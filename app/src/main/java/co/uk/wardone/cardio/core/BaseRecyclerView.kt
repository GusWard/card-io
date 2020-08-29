package co.uk.wardone.cardio.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.uk.wardone.viewmodel.base.BaseRecyclerItem
import co.uk.wardone.viewmodel.base.BaseViewAction
import co.uk.wardone.viewmodel.base.BaseViewModelAction
import co.uk.wardone.viewmodel.util.DiffUtilCallback

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: BaseRecyclerItem, position: Int)
}

open class CoreRecyclerViewAdapter(private val viewHolderFactory: BaseViewHolderFactory) : RecyclerView.Adapter<BaseViewHolder>() {

    var items = mutableListOf<BaseRecyclerItem>()

    fun getItem(pos: Int): BaseRecyclerItem = items[pos]

    fun indexOf(item: BaseRecyclerItem) = items.indexOf(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        return viewHolderFactory.createViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {

        return items.size
    }

    override fun getItemViewType(position: Int): Int = items[position].type

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) = holder.bind(items[position], position)

    fun <T : BaseRecyclerItem> updateItems(newItems: List<T>) {

        val diffResult = DiffUtil.calculateDiff(DiffUtilCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}

abstract class BaseViewHolderFactory(
    open var viewModelAction : (action: BaseViewModelAction) -> Unit = { _ -> },
    open var viewAction : (action: BaseViewAction, item: BaseRecyclerItem, view: View) -> Unit = { _, _, _ -> }) {

    abstract fun createViewHolder(parent: ViewGroup, type: Int): BaseViewHolder
}

class ScrollingAlphaScrollListener(val setAlpha: (Float) -> Unit) : RecyclerView.OnScrollListener() {

    var position = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

        super.onScrolled(recyclerView, dx, dy)
        position += dy

        if (position > 0) {

            setAlpha(1 - (position / 100f))
        }else {

            setAlpha(1f)
        }
    }
}