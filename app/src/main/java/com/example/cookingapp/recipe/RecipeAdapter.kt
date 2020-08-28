package com.example.cookingapp.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingapp.R

class RecipeAdapter internal constructor(
    private var context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var recipes = emptyList<Recipe>()
    var onItemClick: ((Recipe) -> Unit)? = null

    inner class RecipeHeader(itemView: View): RecyclerView.ViewHolder(itemView) {}

    inner class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mRecipeName: TextView = itemView.findViewById(R.id.text_recipe_name)
        val mRecipeDesc: TextView = itemView.findViewById(R.id.text_recipe_desc)
        val mRecipeDifficulty: TextView = itemView.findViewById(R.id.text_recipe_difficulty)
        val mRecipePrepTime: TextView = itemView.findViewById(R.id.text_recipe_prep_time)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(recipes[adapterPosition-1])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == HEADER) {
            val header = inflater.inflate(R.layout.recipe_header, parent, false)
            return RecipeHeader(header)
        }
        val itemView = inflater.inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is RecipeHeader) {
            // fill header
        } else if (holder is RecipeViewHolder) {
            val current = recipes[position-1]

            holder.mRecipeName.text = current.name
            holder.mRecipeDesc.text = "Feeds ${current.feedNumber} for $${current.cost}"
            holder.mRecipeDifficulty.text = "${current.difficulty} Difficulty"
            holder.mRecipePrepTime.text = "${current.prepTimeMin} min"
        }

    }

    internal fun setRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
        notifyDataSetChanged()
    }

    override fun getItemCount() = recipes.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (isHeaderPosition(position)) {
            HEADER
        } else {
            ITEM
        }
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position == 0
    }

    companion object {
        const val HEADER = 0
        const val ITEM = 1
    }

}