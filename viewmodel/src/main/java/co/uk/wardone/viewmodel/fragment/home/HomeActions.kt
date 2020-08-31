package co.uk.wardone.viewmodel.fragment.home

import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewModelAction

class HomeViewModelActions {

    data class Search(val query: String) : BaseViewModelAction

    data class CreateCard(
        val title: String,
        val description: String,
        val link: String? = null,
        val image: String? = null) : BaseViewModelAction
}

class HomeViewActions {

    data class MakeToast(val message: String) : BaseViewAction
}