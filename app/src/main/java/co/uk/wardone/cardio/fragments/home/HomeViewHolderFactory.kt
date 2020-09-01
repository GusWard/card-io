package co.uk.wardone.cardio.fragments.home

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.uk.wardone.cardio.R
import co.uk.wardone.cardio.core.BaseViewHolder
import co.uk.wardone.cardio.core.BaseViewHolderFactory
import co.uk.wardone.viewmodel.base.BaseRecyclerItem
import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewModelAction
import co.uk.wardone.viewmodel.fragment.home.HomeData
import co.uk.wardone.viewmodel.fragment.home.HomeItemTypes
import co.uk.wardone.viewmodel.fragment.home.HomeViewActions
import co.uk.wardone.viewmodel.fragment.home.HomeViewModelActions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.android.synthetic.main.item_home_card.view.*
import kotlinx.android.synthetic.main.item_home_search.view.*


class HomeViewHolderFactory(
    override var viewModelAction: (action: BaseViewModelAction) -> Unit,
    override var viewAction: (action: BaseViewAction, item: BaseRecyclerItem, view: View) -> Unit
) : BaseViewHolderFactory(viewModelAction) {

    override fun createViewHolder(parent: ViewGroup, type: Int): BaseViewHolder {

        return when (type) {

            HomeItemTypes.SEARCH -> {

                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_home_search,
                    parent,
                    false
                )
                SearchViewHolder(view)
            }

            HomeItemTypes.CARD -> {

                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_home_card,
                    parent,
                    false
                )
                CardViewHolder(view)
            }
            else -> throw IllegalArgumentException("unknown view type $type")
        }
    }

    inner class SearchViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(item: BaseRecyclerItem, position: Int) {

            if (item is HomeData.SearchItem) {

                itemView.itemHomeSearchInput.addTextChangedListener(object : TextWatcher {

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                        viewModelAction(HomeViewModelActions.Search(s.toString()))
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
            }
        }
    }

    inner class CardViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(item: BaseRecyclerItem, position: Int) {

            if (item is HomeData.CardItem) {

                itemView.itemHomeCardTitle?.text = item.title
                itemView.itemHomeCardDescription?.text = item.description

                itemView.itemHomeCardLink?.text = item.link ?: ""
                if (item.link.isNullOrEmpty()) {

                    itemView.itemHomeCardLink?.visibility = View.GONE
                    itemView.setOnLongClickListener(null)
                } else {

                    itemView.itemHomeCardLink.visibility = View.VISIBLE
                    itemView.setOnClickListener {

                        viewAction(HomeViewActions.OpenLink(item.link ?: ""), item, itemView)
                    }
                }

                itemView.itemHomeCardImage?.visibility = if (item.image.isNullOrEmpty()) View.GONE else View.VISIBLE
                if (itemView.itemHomeCardImage != null && item.image != null) {

                    Glide.with(itemView)
                        .load(item.image)
                        .transform(CenterCrop(), RoundedCorners(20))
                        .placeholder(R.drawable.ic_baseline_image_48)
                        .into(itemView.itemHomeCardImage)
                }

                itemView.setOnLongClickListener {

                    viewAction(HomeViewActions.VerifyDeleteCard(item.id), item, itemView)
                    true
                }
            }
        }
    }
}