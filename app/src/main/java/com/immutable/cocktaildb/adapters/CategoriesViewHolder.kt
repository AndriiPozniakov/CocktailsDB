package com.immutable.cocktaildb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.immutable.cocktaildb.R
import com.immutable.cocktaildb.data.Category
import com.immutable.cocktaildb.utils.ToggleListener
import kotlinx.android.synthetic.main.item_category.view.*


class CategoriesViewHolder(view: View, private val listener: ToggleListener) : RecyclerView.ViewHolder(view) {

    fun bind(category: Category, position: Int) {
        itemView.apply {
            textFilter.text = category.strCategory
            checkBoxFilter.isChecked = category.isChecked
            checkBoxFilter.setOnClickListener {
                listener.onToggle(position, checkBoxFilter.isChecked)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, listener: ToggleListener): CategoriesViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
            return CategoriesViewHolder(view, listener)
        }
    }
}