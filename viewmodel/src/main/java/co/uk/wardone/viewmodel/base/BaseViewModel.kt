package co.uk.wardone.viewmodel.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import co.uk.wardone.model.database.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    application: Application,
    val database: AppDatabase? = AppDatabase.getInstance(application)) : AndroidViewModel(application) {

    private val viewActionLiveData: MutableLiveData<BaseViewAction> = MutableLiveData()

    abstract fun bindData(lifecycleOwner: LifecycleOwner, observer: Observer<BaseFragmentData>)

    open fun bindActions(lifecycleOwner: LifecycleOwner, observer: Observer<BaseViewAction>) {

        viewActionLiveData.observe(lifecycleOwner, observer)
    }

    abstract suspend fun viewModelActionBackground(action: BaseViewModelAction, database: AppDatabase, lifecycleOwner: LifecycleOwner)

    protected fun viewAction(viewAction: BaseViewAction) {

        viewActionLiveData.postValue(viewAction)
    }

    fun viewModelAction(action: BaseViewModelAction, lifecycleOwner: LifecycleOwner) {

        GlobalScope.launch {

            database?.let {

                viewModelActionBackground(action, it, lifecycleOwner)
            }
        }
    }
}