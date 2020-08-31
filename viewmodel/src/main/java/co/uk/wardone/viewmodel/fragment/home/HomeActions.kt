package co.uk.wardone.viewmodel.fragment.home

import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewModelAction

class HomeViewModelActions {

    data class Search(val query: String) : BaseViewModelAction
}