package co.uk.wardone.server

import android.content.Context
import android.util.Log
import co.uk.wardone.server.database.CardServerDatabase
import com.google.gson.Gson
import fi.iki.elonen.NanoHTTPD

class CardServer(context: Context) : NanoHTTPD(8080) {

    companion object {

        private const val TAG = "CardServer"
    }

    private val db = CardServerDatabase.getInstance(context)

    override fun serve(session: IHTTPSession?): Response {

        return when (session?.method) {

            Method.GET -> getAllCards(session)
            Method.PUT -> putCard(session)
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

    private fun getAllCards(session: IHTTPSession): Response {


        val since = session.parms["since"]?.toLongOrNull() ?: 0L
        val dao = db?.getCardDao()
        val cards = dao?.getAll(since)

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
                newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, json)
            }
        }
    }

    private fun putCard(session: IHTTPSession): Response {

        Log.i(TAG, session.parms.toString())
        Log.i(TAG, session.parms.toString())
        Log.i(TAG, session.parms.toString())
        Log.i(TAG, session.parms.toString())
        return newFixedLengthResponse("not implemented")
    }

    private fun deleteCard(session: IHTTPSession): Response {

        return newFixedLengthResponse("not implemented")
    }
}