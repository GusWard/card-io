package co.uk.wardone.viewmodel.base

import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewModelAction

abstract class BaseRecyclerItem(
    open val id: String,
    val type: Int)