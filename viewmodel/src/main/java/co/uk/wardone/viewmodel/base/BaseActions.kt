package co.uk.wardone.viewmodel.base

interface BaseViewModelAction

interface BaseViewAction

interface FragmentActionHandler {

    fun fragmentAction(action: BaseViewAction)
}