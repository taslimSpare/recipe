package com.caavo.recipeapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caavo.recipeapp.R
import com.caavo.recipeapp.models.RecipeObject
import com.google.android.material.button.MaterialButton

class MarketAdapter(private val context: Context, private val items: List<RecipeObject>, val clickListener: (RecipeObject) -> Unit):
        RecyclerView.Adapter<MarketAdapter.CurrencyViewHolder>() {

    inner class CurrencyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.tv_name)
        private val cost = itemView.findViewById<TextView>(R.id.tv_cost)
        private val image = itemView.findViewById<ImageView>(R.id.img)
        private val addToCart = itemView.findViewById<MaterialButton>(R.id.btn_add_to_cart)

        fun bind(recipe: RecipeObject) {
            name.text = recipe.name
            cost.text = recipe.price

            Glide
                .with(context)
                .load(recipe.image)
                .into(image)

            if(recipe.isInCart) { addToCart.text = context.getString(R.string.remove_from_cart) } else { addToCart.text = context.getString(R.string.add_to_cart) }
            addToCart.setOnClickListener { clickListener(recipe) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_online_recipes, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(items[position])
    }
}