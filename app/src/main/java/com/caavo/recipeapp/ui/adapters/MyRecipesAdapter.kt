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

class MyRecipesAdapter(private val context: Context, private val items: List<RecipeObject>):
        RecyclerView.Adapter<MyRecipesAdapter.CurrencyViewHolder>() {

    inner class CurrencyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val name = itemView.findViewById<TextView>(R.id.tv_name)
        private val cost = itemView.findViewById<TextView>(R.id.tv_cost)
        private val category = itemView.findViewById<TextView>(R.id.tv_category)
        private val image = itemView.findViewById<ImageView>(R.id.img)

        fun bind(recipe: RecipeObject) {
            name.text = recipe.name
            cost.text = recipe.price
            category.text = recipe.category

            Glide
                .with(context)
                .load(recipe.image)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_my_recipes, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(items[position])
    }
}