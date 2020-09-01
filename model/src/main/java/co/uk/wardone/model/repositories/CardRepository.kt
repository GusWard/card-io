package co.uk.wardone.model.repositories

import android.util.Log
import co.uk.wardone.model.api.CardResponse
import co.uk.wardone.model.api.CardService
import co.uk.wardone.model.database.AppDatabase
import co.uk.wardone.model.database.Card
import co.uk.wardone.model.database.CardDao
import co.uk.wardone.model.utils.calculateListDiff
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardRepository(val database: AppDatabase) : BaseRepository<CardDao>() {

    companion object {

        private const val TAG = "CardRepo"
    }

    override fun getDao(): CardDao? = database.getCardDao()

    override fun refresh() {

        val cardDao = getDao()
        val cardService = CardService.create()
        val newCardCall = cardService.getCards()

        newCardCall.enqueue(object: Callback<CardResponse>{

            override fun onResponse(call: Call<CardResponse>, response: Response<CardResponse>) {

                /* We need to make sure we stay in sync with server db so delete any missing ones,
                *  then create or update the latest ones */
                val currentCards = cardDao?.getAllSync() ?: emptyList()
                val latestCards = response.body()?.data ?: emptyList()
                val cardsToDelete = calculateListDiff(currentCards, latestCards)
                cardDao?.deleteAll(cardsToDelete)
                cardDao?.insertAll(latestCards)
            }

            override fun onFailure(call: Call<CardResponse>, t: Throwable) {

                Log.e(TAG, "failed fetching cards", t)
            }
        })
    }

    fun addCard(card: Card, onSuccess: () -> Unit, onFailure: (String) -> Unit) {

        val cardService = CardService.create()
        val putCardCall = cardService.putCard(card)

        putCardCall.enqueue(object: Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                /* the app's card table does not have auto increment set as this should be done in backend.
                 * we want our data to mirror the backend so push the card with default 0, allow server to
                 * generate id, then in refresh the card will get added to app's db with shiny new id */
                refresh()
                onSuccess()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                Log.e(TAG, "failed adding card", t)
                onFailure("failed adding card: " + t.message)
            }
        })
    }

    fun deleteCard(id: String, onFailure: (String) -> Unit) {

        val dao = getDao()
        val cardService = CardService.create()
        val deleteCardCall = cardService.deleteCard(id)

        dao?.let {

            deleteCardCall.enqueue(object: Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    refresh()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    Log.e(TAG, "failed deleting card", t)
                    onFailure("failed deleting card: " + t.message)
                }
            })
        }
    }
}