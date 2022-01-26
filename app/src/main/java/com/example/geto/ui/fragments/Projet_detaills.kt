package com.example.geto.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.View.TEXT_ALIGNMENT_TEXT_START
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.geto.R
import com.example.geto.data.Rest.ApiInterface
import com.example.geto.data.Rest.RetrofitInstance
import com.example.geto.data.model.SharedNote
import com.example.geto.data.model.SignInBody
import com.example.geto.data.model.Task
import com.example.geto.data.model.User
import com.example.geto.guser
import com.example.geto.ui.login.LoginFragmentDirections
import com.google.gson.Gson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Projet_detaills : Fragment() {

    private val args :Projet_detaillsArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_projet_detaills, container, false)
        //variables
        val btn_tasks=view.findViewById<Button>(R.id.btn_tasks)
        val btn_sharedNotes=view.findViewById<Button>(R.id.btn_sharedNotes)
        val add_participant= view.findViewById<Button>(R.id.add_participant)

        val add_taskContainer=view.findViewById<ConstraintLayout>(R.id.add_task_container)
        val add_noteContainer=view.findViewById<ConstraintLayout>(R.id.msg_container)
        val Notes_layout=view.findViewById<LinearLayout>(R.id.linearLayout_Notes)

        val tasks_section=view.findViewById<ScrollView>(R.id.tasks_section)
        val notes_section=view.findViewById<ScrollView>(R.id.Notes_section)

        btn_sharedNotes.setBackgroundColor( getResources().getColor(R.color.black))
        btn_sharedNotes.setTextColor(getResources().getColor(R.color.white))

        btn_tasks.setBackgroundColor( getResources().getColor(R.color.white))
        btn_tasks.setTextColor(getResources().getColor(R.color.black))

        val tasks=args.projet.tasks
        var position=0;
        //populate the tasks section:
        val linearLayout=view.findViewById<LinearLayout>(R.id.linear_layout)
        for(task in tasks){
            val textview=TextView(this.context)
            textview.text="   " +task.description
            textview.minHeight=100
            textview.textAlignment= TEXT_ALIGNMENT_TEXT_START
            textview.setBackgroundResource(R.drawable.background)
            textview.id=position
            textview.setOnClickListener {
                val action = Projet_detaillsDirections.actionProjetDetaillsToTaskDetaills(tasks[textview.id])
                Navigation.findNavController(view).navigate(action)
            }
            position+=1
            textview.setPadding(10,10,10,10)
            linearLayout.addView(textview)
        }
        handel_taks(view,position,tasks)
        //populate note section
        val  retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val reqbody=SharedNote("", args.projet.id,guser.name,"")
        retIn.get_sharedNote(token = guser.token, reqbody).enqueue(object : Callback<List<SharedNote>> {
            override fun onFailure(call: Call<List<SharedNote>>, t: Throwable) {
                val appContext = context?.applicationContext ?: return
                Toast.makeText(appContext, "no conection ", Toast.LENGTH_LONG).show()
            }
            override fun onResponse(call: Call<List<SharedNote>>, response: Response<List<SharedNote>>) {
                if (response.code() == 200) {
                    val Notes= response.body()!!
                    // populate the sharedmesg section
                    val scope= MainScope()
                    scope.launch {
                        for (note in Notes){
                            val textview=TextView(context)
                            val usertextview=TextView(context)
                            if(note.userName!=guser.name){
                                usertextview.text= "  "+note.userName +" :"
                                usertextview.setTextColor( getResources().getColor(R.color.teal_200))
                                Notes_layout.addView(usertextview)
                                textview.text= "  "+note.note
                                textview.setPadding(10,10,10,10)
                                textview.setMinimumHeight(100)
                                textview.setBackgroundResource(R.drawable.notebackground)
                                Notes_layout.addView(textview)
                            }else{
                                usertextview.text= "   "
                                usertextview.gravity=Gravity.END
                                usertextview.setTextColor( getResources().getColor(R.color.black))
                                Notes_layout.addView(usertextview)
                                textview.text= "  "+note.note
                                textview.setPadding(10,10,10,10)
                                textview.setMinimumHeight(100)
                                textview.setBackgroundResource(R.drawable.notesenderbackground)
                                Notes_layout.addView(textview)
                            }

                        }
                    }
                } else {
                    val appContext = context?.applicationContext ?: return
                    Toast.makeText(appContext, "try again ", Toast.LENGTH_LONG).show()
                }
            }
        })
        btn_sharedNotes.setOnClickListener {
            btn_sharedNotes.setBackgroundColor( getResources().getColor(R.color.white))
            btn_sharedNotes.setTextColor(getResources().getColor(R.color.black))

            btn_tasks.setBackgroundColor( getResources().getColor(R.color.black))
            btn_tasks.setTextColor(getResources().getColor(R.color.white))

            tasks_section.visibility=View.GONE
            add_taskContainer.visibility=View.GONE
            add_participant.visibility=View.GONE
            add_noteContainer.visibility=View.VISIBLE
            notes_section.visibility=View.VISIBLE

            val btn_addNote=view.findViewById<ImageButton>(R.id.add_Note)





            btn_addNote.setOnClickListener {

                val note=view.findViewById<EditText>(R.id.Note)
                val Nnote= SharedNote(note.text.toString(),args.projet.id,guser.name,"")
                val textview=TextView(context)
                val usertextview=TextView(context)
                usertextview.text= "   "
                usertextview.gravity=Gravity.END
                usertextview.setTextColor( getResources().getColor(R.color.black))
                Notes_layout.addView(usertextview)
                textview.text= "  "+note.text.toString()
                note.setText("")
                textview.setPadding(10,10,10,10)
                textview.setMinimumHeight(100)
                textview.setBackgroundResource(R.drawable.notesenderbackground)
                Notes_layout.addView(textview)
                retIn.add_sharedNote(token = guser.token,Nnote).enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        val appContext = context?.applicationContext ?: return
                        Toast.makeText(appContext,"no conection ", Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.code() == 200) {
                            val appContext = context?.applicationContext ?: return
                            Toast.makeText(appContext,"saved successfuly ", Toast.LENGTH_LONG).show()
                        } else {
                            val appContext = context?.applicationContext ?: return
                            Toast.makeText(appContext,"try again ", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }

        }
        btn_tasks.setOnClickListener {
            btn_tasks.setBackgroundColor( getResources().getColor(R.color.white))
            btn_tasks.setTextColor(getResources().getColor(R.color.black))
            btn_sharedNotes.setBackgroundColor( getResources().getColor(R.color.black))
            btn_sharedNotes.setTextColor(getResources().getColor(R.color.white))

            add_taskContainer.visibility=View.VISIBLE
            add_participant.visibility=View.VISIBLE
            add_noteContainer.visibility=View.GONE
            notes_section.visibility=View.GONE
            tasks_section.visibility=View.VISIBLE
            position=tasks.size
            handel_taks(view,position,tasks)

        }





        return view
    }

    fun handel_taks(view: View,position:Int,tasks:ArrayList<Task>){
        var position=position
        val linearLayout=view.findViewById<LinearLayout>(R.id.linear_layout)
        val b = view.findViewById<ImageButton>(R.id.add_task)
        val task=view.findViewById<EditText>(R.id.task2)


        // populate the fragment


        val add_participant= view.findViewById<Button>(R.id.add_participant)
        add_participant.setOnClickListener {
            val action =Projet_detaillsDirections.actionProjetDetaillsToAddparticipant(args.projet)
            Navigation.findNavController(view).navigate(action)
        }
        b.setOnClickListener(View.OnClickListener {
            val name: String = task.getText().toString()
            if (name.isNotEmpty()) {
                val newTask= Task("",name,false,args.projet.id)
                //tasks.add(newTask)
                val textview=TextView(this.context)
                textview.text= "   " +name
                textview.minHeight=100
                textview.textAlignment= TEXT_ALIGNMENT_TEXT_START
                textview.setBackgroundResource(R.drawable.background)
                textview.setPadding(10,10,10,10)
                textview.id=position

                val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

                retIn.add_task(token = guser.token,newTask).enqueue(object : Callback<Task> {
                    override fun onFailure(call: Call<Task>, t: Throwable) {
                        val appContext = context?.applicationContext ?: return
                        Toast.makeText(appContext,"No conection ", Toast.LENGTH_LONG).show()

                    }
                    override fun onResponse(call: Call<Task>, response: Response<Task>) {
                        if (response.code() == 200) {
                            newTask.id= response.body()?.id ?: "";
                            tasks.add(newTask)
                            linearLayout.addView(textview)
                            task.setText("")
                            textview.setOnClickListener {
                                val action = Projet_detaillsDirections.actionProjetDetaillsToTaskDetaills(newTask)
                                Navigation.findNavController(view).navigate(action)
                            }
                            position+=1
                        } else {
                            val appContext = context?.applicationContext ?: return
                            Toast.makeText(appContext,"try again ", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            } else Toast.makeText(this.context, "The name cannot be empty!", Toast.LENGTH_LONG)
                    .show()
        })

    }
    fun style_senderNote(){

    }

}