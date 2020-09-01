package co.uk.wardone.viewmodel.fragment.home

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import co.uk.wardone.model.database.Card
import co.uk.wardone.model.repositories.CardRepository
import co.uk.wardone.viewmodel.base.BaseViewData
import co.uk.wardone.viewmodel.base.BaseViewModel
import co.uk.wardone.viewmodel.base.BaseViewModelAction

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private var cardRepository: CardRepository? = null

    private val homeItemsLiveData = MutableLiveData<HomeData.HomeItems>()
    private val queryLiveData = MutableLiveData<String>()

    override fun bindData(lifecycleOwner: LifecycleOwner, observer: Observer<BaseViewData>) {

        homeItemsLiveData.observe(lifecycleOwner, observer)

        database?.let {  db ->

            cardRepository = CardRepository(db)
            initCardObserver(lifecycleOwner)
            initCardSearch(lifecycleOwner)
        }
    }

    override suspend fun viewModelActionBackground(
        action: BaseViewModelAction
    ) {

        when (action) {

            is HomeViewModelActions.Search -> {

                /* this change will trigger the observer below */
                queryLiveData.postValue(action.query)
            }

            is HomeViewModelActions.DeleteCard -> {

                cardRepository?.deleteCard(action.id) { error ->

                    viewAction(HomeViewActions.DeleteCardFailed(error))
                }
            }
        }
    }

    private fun initCardObserver(lifecycleOwner: LifecycleOwner): Unit? {

        /* on initial bind, refresh from the remote data source, view will be populated by local
         * data to begin with then once refresh completes UI will react to the changes in the Room
         * database via the LiveData instance returned by getAll */
        return cardRepository
            ?.getDaoAndRefresh()
            ?.getAll()
            ?.observe(lifecycleOwner, Observer { cards ->

                updateCards(cards)
            })
    }

    private fun initCardSearch(lifecycleOwner: LifecycleOwner): Unit? {

        /* queryLiveData will be updated via view model action from the view, switch map enables us to
         * react to those changes without adding a new observer to the search live data every time a new
         * character is typed */
        val searchLiveData = Transformations.switchMap(queryLiveData) { query ->

            if (query.isEmpty()) {

                cardRepository?.getDao()?.getAll()
            } else {

                cardRepository?.getDao()?.search(query)
            }
        }
        return searchLiveData?.observe(lifecycleOwner, Observer { cards ->

            /* here we are observing changes to the search live data,
             * and posting them to the view */
            updateCards(cards)
        })
    }

    private fun updateCards(cards: List<Card>) {

        val cardItems = cards.map { card ->

            HomeData.CardItem(
                id = card.id.toString(),
                title = card.title,
                description = card.description,
                link = card.link,
                image = card.image
            )
        }

        val homeItems = HomeData.HomeItems(listOf(HomeData.SearchItem) + cardItems)
        homeItemsLiveData.postValue(homeItems)
    }
}