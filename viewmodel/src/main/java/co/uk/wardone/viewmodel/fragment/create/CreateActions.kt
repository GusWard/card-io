package co.uk.wardone.viewmodel.fragment.create

import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewModelAction

class CreateViewModelActions {

    data class CreateCard(
        val title: String,
        val description: String,
        val link: String? = null,
        val image: String? = null) : BaseViewModelAction
}

class CreateViewActions {

    data class CreateFailed(val message: String) : BaseViewAction

    object CreateSuccess : BaseViewAction
}