package com.example.geto.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.geto.R


class AddProjet : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_add_projet, container, false)
        val linearLayout=view.findViewById<LinearLayout>(R.id.lnear_layout)

        val b = view.findViewById<ImageView>(R.id.add_task2)
        val task=view.findViewById<MultiAutoCompleteTextView>(R.id.task)

        b.setOnClickListener(View.OnClickListener {
            val name: String = task.getText().toString()
            if (name.isNotEmpty()) {
                val checkBox = CheckBox(this.context)
                checkBox.text = name

                linearLayout.addView(checkBox)
                task.setText("")

            } else Toast.makeText(this.context, "The name cannot be empty!", Toast.LENGTH_LONG)
                .show()
        })
        return(view)

    }
}