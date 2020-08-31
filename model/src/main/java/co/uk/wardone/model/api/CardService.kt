package co.uk.wardone.model.api

import co.uk.wardone.model.database.Card
import co.uk.wardone.model.repositories.CardRepository
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CardService {

    companion object {

        private const val BASE_URL = "http:/127.0.0.1:8080"

        fun create(): CardService {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(CardService::class.java)
        }
    }

    @GET("/")
    fun getCards() : Call<CardResponse>

    @POST("/")
    fun putCard(@Body card: Card) : Call<ResponseBody>

    @DELETE("/")
    fun deleteCard(@Query("id") id: String) : Call<ResponseBody>
}