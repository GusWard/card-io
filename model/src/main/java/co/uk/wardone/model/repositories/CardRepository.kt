package co.uk.wardone.model.repositories

import android.util.Log
import co.uk.wardone.model.api.CardResponse
import co.uk.wardone.model.api.CardService
import co.uk.wardone.model.database.AppDatabase
import co.uk.wardone.model.database.Card
import co.uk.wardone.model.database.CardDao
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

                /* We need to make sure we stay in sync with server db so delete any missing ones
                *  then create or update the latest ones */
                val currentCards = cardDao?.getAllSync()
                val latestCards = response.body()
                val cardsToDelete = currentCards?.toHashSet()
                cardsToDelete?.removeAll(latestCards?.data ?: emptyList())
                cardDao?.deleteAll(cardsToDelete?.toList() ?: emptyList())
                cardDao?.insertAll(latestCards?.data ?: emptyList())
            }

            override fun onFailure(call: Call<CardResponse>, t: Throwable) {

                Log.e(TAG, "failed fetching cards", t)
            }
        })
    }

    fun addCard(card: Card, onFailure: (String) -> Unit) {

        val cardService = CardService.create()
        val putCardCall = cardService.putCard(card)

        putCardCall.enqueue(object: Callback<ResponseBody> {

            /* we only want to add the card to our app db on success */
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                refresh()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                Log.e(TAG, "failed adding card", t)
                onFailure("failed fetching cards: " + t.message)
            }
        })
    }

    fun deleteCard(card: Card, onFailure: (String) -> Unit) {

        val dao = getDao()
        val cardService = CardService.create()
        val deleteCardCall = cardService.deleteCard(card)

        dao?.let {

            deleteCardCall.enqueue(object: Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    refresh()
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    Log.e(TAG, "failed adding card", t)
                    onFailure("failed fetching cards: " + t.message)
                }
            })
        }
    }
}