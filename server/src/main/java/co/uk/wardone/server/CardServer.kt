package co.uk.wardone.server

import android.content.Context
import android.util.Log
import co.uk.wardone.server.database.CardServerDatabase
import co.uk.wardone.server.database.ServerCard
import com.google.gson.Gson
import fi.iki.elonen.NanoHTTPD
import java.lang.IllegalArgumentException

data class ArrayResponse(val length: Int, val data: List<Any>)

class CardServer(context: Context) : NanoHTTPD(8080) {

    companion object {

        private const val TAG = "CardServer"
        private const val MIME_APP_JSON = "application/json"
    }

    private val db = CardServerDatabase.getInstance(context)

    override fun serve(session: IHTTPSession?): Response {

        return when (session?.method) {

            Method.GET -> getAllCards()
            Method.POST -> putCard(session)
            Method.DELETE -> deleteCard(session)
            else -> {

                Log.e(TAG, "${session?.method} method not implemented")
                newFixedLengthResponse(
                    Response.Status.METHOD_NOT_ALLOWED,
                    MIME_PLAINTEXT,
                    "Go Away! :)"
                )
            }
        }
    }

    private fun getAllCards(): Response {

        try {

            val dao = db?.getCardDao()
            val cards = dao?.getAll()

            return when {

                dao == null -> {

                    newFixedLengthResponse(
                        Response.Status.INTERNAL_ERROR,
                        MIME_PLAINTEXT,
                        "Internal server error"
                    )
                }
                cards == null -> {

                    newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not found")
                }
                else -> {

                    val length = cards.size
                    val arrayResponse = ArrayResponse(length, cards)
                    val json = Gson().toJson(arrayResponse)
                    newFixedLengthResponse(Response.Status.OK, MIME_APP_JSON, json)
                }
            }

        } catch (e: Exception) {

            Log.e(TAG, "failed getting cards", e)
            return newFixedLengthResponse(
                Response.Status.INTERNAL_ERROR,
                MIME_PLAINTEXT,
                e.message
            )
        }
    }

    private fun putCard(session: IHTTPSession): Response {

        return try {

            val data = mutableMapOf<String, String>()
            session.parseBody(data)
            val dao = db?.getCardDao()
            val card = Gson().fromJson<ServerCard>(data["postData"], ServerCard::class.java)
            val id = dao?.insert(card)
            newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, "$id")
        } catch (e: Exception) {

            Log.e(TAG, "failed adding card", e)
            newFixedLengthResponse(
                Response.Status.INTERNAL_ERROR,
                MIME_PLAINTEXT,
                e.message
            )
        }
    }

    private fun deleteCard(session: IHTTPSession): Response {

        return try {

            val data = mutableMapOf<String, String>()
            session.parseBody(data)
            val dao = db?.getCardDao()
            val id = session.parms["id"]
            if (id == null) {

                throw IllegalArgumentException("Must provide id")
            } else {

                dao?.delete(id.toLong())
                newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, id)
            }
        } catch (e: Exception) {

            Log.e(TAG, "failed adding card", e)
            newFixedLengthResponse(
                Response.Status.INTERNAL_ERROR,
                MIME_PLAINTEXT,
                e.message
            )
        }
    }
}