package co.uk.wardone.cardio.fragments.home

import android.view.View
import android.view.ViewGroup
import co.uk.wardone.cardio.core.BaseViewHolder
import co.uk.wardone.cardio.core.BaseViewHolderFactory
import co.uk.wardone.viewmodel.base.BaseRecyclerItem


class HomeViewHolderFactory : BaseViewHolderFactory() {

    override fun createViewHolder(parent: ViewGroup, type: Int): BaseViewHolder {

        return when (type) {

            else -> throw IllegalArgumentException("unknown view type $type")
        }
    }

    inner class TranscriptViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(item: BaseRecyclerItem, position: Int) {

            when (item) {


            }
        }
    }
}