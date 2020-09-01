package co.uk.wardone.viewmodel.util

import androidx.recyclerview.widget.DiffUtil
import co.uk.wardone.viewmodel.base.BaseRecyclerItem

class DiffUtilCallback<T : BaseRecyclerItem>(private val oldList: List<T>, private val newList: List<T>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            = oldList[oldItemPosition] == newList[newItemPosition]
}