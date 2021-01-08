package com.immutable.cocktaildb.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.immutable.cocktaildb.data.Drink
import com.immutable.cocktaildb.utils.ViewType

class DrinksListAdapter(
    private val retry: () -> Unit
) : PagedListAdapter<Drink, RecyclerView.ViewHolder>(DrinkDiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ViewType.DATA_VIEW_TYPE.type) DrinksViewHolder.create(parent) else ListTitleViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == ViewType.DATA_VIEW_TYPE.type)
            (holder as DrinksViewHolder).bind(getItem(position))
        else
            (holder as ListTitleViewHolder).bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return with(getItem(position)) { this?.viewType?.type ?: ViewType.DATA_VIEW_TYPE.type }
    }

    companion object {
        val DrinkDiffCallback = object : DiffUtil.ItemCallback<Drink>() {
            override fun areItemsTheSame(oldItem: Drink, newItem: Drink): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Drink, newItem: Drink): Boolean {
                return oldItem.title == newItem.title
                        && oldItem.image == oldItem.image
            }
        }
    }
}