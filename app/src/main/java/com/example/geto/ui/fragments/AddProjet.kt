package com.example.geto.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.geto.R
import com.example.geto.data.Rest.ApiInterface
import com.example.geto.data.Rest.RetrofitInstance
import com.example.geto.data.model.Project
import com.example.geto.data.model.Task
import com.example.geto.data.requestBody.NewProject
import com.example.geto.guser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddProjet : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_add_projet, container, false)
        val linearLayout=view.findViewById<LinearLayout>(R.id.lnear_layout)

        val b = view.findViewById<ImageView>(R.id.add_task2)
        val taskDescription=view.findViewById<MultiAutoCompleteTextView>(R.id.task)
        val tasks= arrayListOf<Task>()

        b.setOnClickListener(View.OnClickListener {
            val description: String = taskDescription.getText().toString()
            if (description.isNotEmpty()) {
                val checkBox = CheckBox(this.context)
                checkBox.text = description
                checkBox.setEnabled(false)
                linearLayout.addView(checkBox)
                taskDescription.setText("")

                val nTask=Task("",description,false,"")
                tasks.add(nTask)
            } else Toast.makeText(this.context, "The description cannot be empty!", Toast.LENGTH_LONG)
                    .show()
        })
        val btn_save = view.findViewById<Button>(R.id.add_project2)
        btn_save.setOnClickListener {
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val title=view.findViewById<TextView>(R.id.title_project)
            val description=view.findViewById<TextView>(R.id.add_project_description)
            val users=ArrayList<String>()
            users.add(guser.email)
            val project =Project(title.text.toString(),description.text.toString(), users,"0", ArrayList())
            val newprojectBody=NewProject(tasks,project,guser.token)
            retIn.add_project(newprojectBody).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val appContext = context?.applicationContext ?: return
                    Toast.makeText(appContext,"wrong email or password ", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.code() == 200) {
                        project.tasks.addAll(tasks)
                        val action =AddProjetDirections.actionAddProjetToNavigationHome()
                        Navigation.findNavController(view).navigate(action)
                    } else {
                        val appContext = context?.applicationContext ?: return
                        Toast.makeText(appContext,response.code().toString(), Toast.LENGTH_LONG).show()
                    }
                }
            })
        }



        return(view)

    }
}