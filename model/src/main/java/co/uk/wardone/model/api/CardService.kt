package co.uk.wardone.model.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface CardService {

    companion object {

        private const val BASE_URL = "http:/127.0.0.1:8080"

        fun create(): CardService {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(CardService::class.java)
        }
    }

    @GET("/")
    fun getCardsSince(since: Long) : Call<CardResponse>

    @PUT("/")
    fun putCard(@Body card: Card) : Call<ResponseBody>

    @DELETE("/")
    fun deleteCard(@Body card: Card) : Call<ResponseBody>
}