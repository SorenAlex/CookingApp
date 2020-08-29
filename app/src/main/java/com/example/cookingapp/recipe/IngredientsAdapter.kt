package com.example.cookingapp.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import com.example.cookingapp.R
import kotlinx.android.synthetic.main.ingredient_item.view.*

class IngredientsAdapter(
    context: Context,
    private val resourceId: Int, private val values: List<String>
): ArrayAdapter<String>(context, resourceId, values) {

    override fun getItem(position: Int) = values[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater
            .from(context).inflate(resourceId, parent, false)

        val currentIngredient = getItem(position)

        val mCheckboxIngredient: CheckBox = view.findViewById(R.id.checkbox_ingredient)
        mCheckboxIngredient.text = currentIngredient

        return view
    }

    override fun getCount(): Int {
        return values.size
    }

}