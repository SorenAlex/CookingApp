package com.example.cookingapp.avatar.headPack

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.cookingapp.R
import com.example.cookingapp.getDrawableIdByName

class HeadPackAdapter internal constructor(
    private var context: Context
): RecyclerView.Adapter<HeadPackAdapter.HeadPackViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var headPacks = emptyList<HeadPack>()
    var onItemClick: ((HeadPack) -> Unit)? = null


    inner class HeadPackViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mPackCost: TextView = itemView.findViewById(R.id.text_pack_cost)
        val mPackImage: ImageView = itemView.findViewById(R.id.pack_image)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(headPacks[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadPackViewHolder {
        val itemView = inflater.inflate(R.layout.pack_item, parent, false)
        return HeadPackViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HeadPackViewHolder, position: Int) {
        val current = headPacks[position]

        holder.mPackCost.text = "${current.cost} coins"

        if (current.isBought) {
            holder.mPackCost.text = "Owned"
        }

        val packImage = ContextCompat.getDrawable(context, getDrawableIdByName(current.packImage))
        holder.mPackImage.setImageDrawable(packImage)

    }

    internal fun setHeadPacks(headPacks: List<HeadPack>) {
        this.headPacks = headPacks
        notifyDataSetChanged()
    }

    override fun getItemCount() = headPacks.size
}