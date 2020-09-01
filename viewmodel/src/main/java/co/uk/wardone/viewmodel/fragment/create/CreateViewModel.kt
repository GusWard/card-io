package co.uk.wardone.viewmodel.fragment.create

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import co.uk.wardone.model.database.Card
import co.uk.wardone.model.repositories.CardRepository
import co.uk.wardone.viewmodel.base.BaseViewData
import co.uk.wardone.viewmodel.base.BaseViewModel
import co.uk.wardone.viewmodel.base.BaseViewModelAction

class CreateViewModel(application: Application) : BaseViewModel(application)  {

    private var cardRepository: CardRepository? = null

    override fun bindData(lifecycleOwner: LifecycleOwner, observer: Observer<BaseViewData>) {

        database?.let {  db ->

            cardRepository = CardRepository(db)
        }
    }

    override suspend fun viewModelActionBackground(
        action: BaseViewModelAction
    ) {

        when (action) {

            is CreateViewModelActions.CreateCard -> {

                val valid = validateCard(action)

                if (valid) {

                    val card = Card(
                        title = action.title,
                        description = action.description,
                        link = action.link ?: "",
                        image = action.image ?: "")

                    cardRepository?.addCard(card, {

                        viewAction(CreateViewActions.CreateSuccess)
                    }, { error ->

                        viewAction(CreateViewActions.CreateFailed(error))
                    })
                } else {

                    viewAction(CreateViewActions.CreateFailed("Please enter at least title & description"))
                }
            }
        }
    }
}