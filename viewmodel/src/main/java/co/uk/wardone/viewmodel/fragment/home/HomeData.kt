package co.uk.wardone.viewmodel.fragment.home

import co.uk.wardone.viewmodel.base.BaseViewData
import co.uk.wardone.viewmodel.base.BaseRecyclerItem

class HomeData {

    data class HomeItems(val items: List<BaseRecyclerItem>) : BaseViewData

    object SearchItem : BaseRecyclerItem("search", HomeItemTypes.SEARCH)

    data class CardItem(
        override val id: String,
        val title: String,
        val description: String,
        val link: String? = null,
        val image: String? = null) : BaseRecyclerItem(id, HomeItemTypes.CARD)
}