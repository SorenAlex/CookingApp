package com.example.cookingapp.recipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.example.cookingapp.R
import org.w3c.dom.Text

class StepsAdapter(
    context: Context,
    private val resourceId: Int, private val values: List<String>
): ArrayAdapter<String>(context, resourceId, values) {

    override fun getItem(position: Int) = values[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater
            .from(context).inflate(resourceId, parent, false)

        val currentStep = getItem(position)

        val mStepNumText: TextView = view.findViewById(R.id.text_step_number)
        mStepNumText.text = "Step ${position+1}"

        val mStepText: TextView = view.findViewById(R.id.text_step_desc)
        mStepText.text = currentStep

        return view
    }

    override fun getCount(): Int {
        return values.size
    }

}