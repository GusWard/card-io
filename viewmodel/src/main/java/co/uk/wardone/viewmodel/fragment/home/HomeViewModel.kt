package co.uk.wardone.viewmodel.fragment.home

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import co.uk.wardone.model.database.AppDatabase
import co.uk.wardone.viewmodel.base.BaseFragmentData
import co.uk.wardone.viewmodel.base.BaseViewModel
import co.uk.wardone.viewmodel.base.BaseViewModelAction

class HomeViewModel(application: Application) : BaseViewModel(application) {

    override fun bindData(lifecycleOwner: LifecycleOwner, observer: Observer<BaseFragmentData>) {


    }

    override suspend fun viewModelActionBackground(
        action: BaseViewModelAction,
        database: AppDatabase,
        lifecycleOwner: LifecycleOwner
    ) {


    }
}