package co.uk.wardone.viewmodel.activity

import android.content.Context
import co.uk.wardone.server.CardServer

class MainActivityViewModel {

    private var cardServer: CardServer? = null

    fun onActivityCreate(context: Context) {

        cardServer = CardServer(context)
        cardServer?.start()
    }

    fun onActivityDestroy() {

        cardServer?.stop()
    }
}