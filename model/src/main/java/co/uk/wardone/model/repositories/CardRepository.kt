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
        val latestCard = cardDao?.getLatest()
        val cardService = CardService.create()
        val newCardCall = cardService.getCardsSince(latestCard?.timeStamp ?: 0)
        newCardCall.enqueue(object: Callback<CardResponse>{

            override fun onResponse(call: Call<CardResponse>, response: Response<CardResponse>) {

                val newCards = response.body()
                cardDao?.insertAll(newCards?.data?.map {

                    Card(
                        id = it.id,
                        title = it.title,
                        description = it.description,
                        image = it.image,
                        link = it.link,
                        timeStamp = it.timeStamp)
                } ?: emptyList())
            }

            override fun onFailure(call: Call<CardResponse>, t: Throwable) {

                Log.e(TAG, "failed fetching cards", t)
            }
        })
    }
}