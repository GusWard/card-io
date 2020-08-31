package co.uk.wardone.viewmodel.fragment.home

import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewModelAction

class HomeViewModelActions {

    data class Search(val query: String) : BaseViewModelAction

    data class DeleteCard(val id: String) : BaseViewModelAction
}

class HomeViewActions {

    data class VerifyDeleteCard(val id: String) : BaseViewAction

    data class DeleteCardFailed(val message: String) : BaseViewAction
}