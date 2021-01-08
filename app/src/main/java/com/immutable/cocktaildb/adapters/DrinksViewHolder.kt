package com.immutable.cocktaildb.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.immutable.cocktaildb.R
import com.immutable.cocktaildb.data.Drink
import kotlinx.android.synthetic.main.item_drink.view.*

class DrinksViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(drink: Drink?) {
        drink?.let {
            itemView.drinkTitle.text = it.title

            Glide
                .with(itemView.context)
                .load(it.image)
                .placeholder(getPlaceholder(itemView.context))
                .centerCrop()
                .into(itemView.drinkImage);
        }
    }

    companion object {
        fun create(parent: ViewGroup): DrinksViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_drink, parent, false)
            return DrinksViewHolder(view)
        }

        fun getPlaceholder(context: Context): CircularProgressDrawable {
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()

            return circularProgressDrawable
        }
    }
}