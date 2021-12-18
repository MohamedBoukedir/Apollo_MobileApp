package com.example.geto.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.geto.R

class Projet_detaills : Fragment() {

    private val args :Projet_detaillsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_projet_detaills, container, false)
        //variables
        val linearLayout=view.findViewById<LinearLayout>(R.id.linear_layout)
        val b = view.findViewById<ImageButton>(R.id.add_task)
        val task=view.findViewById<EditText>(R.id.task2)
        // populate the fragment
        view.findViewById<TextView>(R.id.project_title).text=args.projet.title
        view.findViewById<TextView>(R.id.description2).text=args.projet.description
        val tasks=args.projet.tasks

        for(task in tasks){
            val checkBox = CheckBox(this.context)
            checkBox.text = task

            linearLayout.addView(checkBox)
        }
        val add_participant= view.findViewById<Button>(R.id.add_participant)
        add_participant.setOnClickListener {
            val bundle = Bundle()
            Navigation.findNavController(view).navigate(R.id.action_projet_detaills_to_addparticipant, bundle)
        }
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
        return view
    }
}