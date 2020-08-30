package com.example.cookingapp.recipe

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingapp.R
import com.example.cookingapp.decodeSampledBitmapFromResource
import com.example.cookingapp.getDrawableIdByName

class RecipeAdapter internal constructor(
    private var context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var recipes = emptyList<Recipe>()
    var onItemClick: ((Recipe) -> Unit)? = null

    inner class RecipeHeader(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mFilterKeyButton: ImageView = itemView.findViewById(R.id.button_filter_expander)
        val mFilterContainer: LinearLayout = itemView.findViewById(R.id.container_search_filter)

        init {
            mFilterKeyButton.setOnClickListener {
                if (mFilterContainer.visibility == View.GONE) {
                    mFilterContainer.visibility = View.VISIBLE
                } else {
                    mFilterContainer.visibility = View.GONE
                }
            }
        }
    }

    inner class RecipeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mRecipeName: TextView = itemView.findViewById(R.id.text_recipe_name)
        val mRecipeDesc: TextView = itemView.findViewById(R.id.text_recipe_desc)
        val mRecipeDifficulty: TextView = itemView.findViewById(R.id.text_recipe_difficulty)
        val mRecipeTime: TextView = itemView.findViewById(R.id.text_recipe_prep_time)
        val mRecipeImage: ImageView = itemView.findViewById(R.id.recipe_item_image)
        val mFavourite: ImageView = itemView.findViewById(R.id.favourites_button)
        val mCompleted: ImageView = itemView.findViewById(R.id.completed_button)

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
            holder.mRecipeDesc.text = current.description
            holder.mRecipeDifficulty.text = "Difficulty: ${current.difficulty}"
            holder.mRecipeTime.text = "Est. Time: ${current.prepTimeMin} min"

            holder.mRecipeImage.setImageBitmap(
                decodeSampledBitmapFromResource(context.resources,
                    getDrawableIdByName(current.imageTag) ,256,256)
            )

            holder.mFavourite.setOnClickListener {
                val packImage = ContextCompat.getDrawable(context, getDrawableIdByName("ic_baseline_star_highlight"))
                holder.mFavourite.setImageDrawable(packImage)
            }

            if (current.isComplete) {
                val packImage = ContextCompat.getDrawable(context, getDrawableIdByName("ic_baseline_doned"))
                holder.mCompleted.setImageDrawable(packImage)
            }
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