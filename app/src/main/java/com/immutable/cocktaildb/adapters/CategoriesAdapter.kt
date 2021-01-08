package com.immutable.cocktaildb.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.immutable.cocktaildb.data.Category
import com.immutable.cocktaildb.utils.ViewType
import com.immutable.cocktaildb.utils.OnFilterAccept
import com.immutable.cocktaildb.utils.ToggleListener
import kotlin.collections.ArrayList

class CategoriesAdapter(
    private val toggleListener: ToggleListener,
    private val acceptListener: OnFilterAccept
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val categories: MutableList<Category> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ViewType.DATA_VIEW_TYPE.type -> CategoriesViewHolder.create(
                parent = parent,
                listener = toggleListener
            )
            else -> CategoriesFooterViewHolder.create(parent, listener = acceptListener,)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            ViewType.DATA_VIEW_TYPE.type -> (holder as CategoriesViewHolder).bind(category = categories[position], position = position)
            else -> (holder as CategoriesFooterViewHolder).bind()
        }
    }

    override fun getItemCount(): Int {
       return categories.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(categories.size != position) ViewType.DATA_VIEW_TYPE.type else ViewType.ANOTHER_VIEW_TYPE.type
    }

    fun setUpdateInfo(categories: List<Category>) {
        this.categories.clear()
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }
}