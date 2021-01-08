package com.immutable.cocktaildb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.immutable.cocktaildb.R
import com.immutable.cocktaildb.data.Drink
import kotlinx.android.synthetic.main.item_title.view.*

class ListTitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(drink: Drink?) {
        drink?.let {
            itemView.group_title.text = it.title
        }
    }

    companion object {
        fun create(parent: ViewGroup): ListTitleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_title, parent, false)
            return ListTitleViewHolder(view)
        }
    }
}