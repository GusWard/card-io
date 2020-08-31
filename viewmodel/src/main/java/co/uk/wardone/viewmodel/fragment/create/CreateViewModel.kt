package co.uk.wardone.viewmodel.fragment.create

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import co.uk.wardone.model.database.AppDatabase
import co.uk.wardone.viewmodel.base.BaseViewData
import co.uk.wardone.viewmodel.base.BaseViewModel
import co.uk.wardone.viewmodel.base.BaseViewModelAction

class CreateViewModel(application: Application) : BaseViewModel(application)  {

    

    override fun bindData(lifecycleOwner: LifecycleOwner, observer: Observer<BaseViewData>) {


    }

    override suspend fun viewModelActionBackground(
        action: BaseViewModelAction,
        database: AppDatabase,
        lifecycleOwner: LifecycleOwner
    ) {

    }
}