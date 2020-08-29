package co.uk.wardone.viewmodel.fragment.home

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.uk.wardone.model.database.AppDatabase
import co.uk.wardone.model.repositories.CardRepository
import co.uk.wardone.viewmodel.base.BaseViewData
import co.uk.wardone.viewmodel.base.BaseViewModel
import co.uk.wardone.viewmodel.base.BaseViewModelAction

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private val homeItemsLiveData = MutableLiveData<HomeData.HomeItems>()

    override fun bindData(lifecycleOwner: LifecycleOwner, observer: Observer<BaseViewData>) {

        homeItemsLiveData.observe(lifecycleOwner, observer)

        database?.let {  db ->

            CardRepository(db)
                .getDaoAndRefresh()
                ?.getAll()
                ?.observe(lifecycleOwner, Observer { cards ->

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
                })
        }
    }

    override suspend fun viewModelActionBackground(
        action: BaseViewModelAction,
        database: AppDatabase,
        lifecycleOwner: LifecycleOwner
    ) {


    }
}