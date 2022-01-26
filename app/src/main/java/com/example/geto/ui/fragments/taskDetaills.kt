package com.example.geto.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.navArgs
import com.example.geto.R
import com.example.geto.data.Rest.ApiInterface
import com.example.geto.data.Rest.RetrofitInstance
import com.example.geto.data.model.SharedNote
import com.example.geto.guser
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [taskDetaills.newInstance] factory method to
 * create an instance of this fragment.
 */
class taskDetaills : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val args :taskDetaillsArgs by navArgs()
    private  var Notes= listOf<SharedNote>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val task=args.task

        val view=inflater.inflate(R.layout.fragment_task_detaills, container, false)
        val btn_details=view.findViewById<Button>(R.id.btn_detaill)
        val btn_msg=view.findViewById<Button>(R.id.btn_msg)
        val btn_save=view.findViewById<Button>(R.id.btn_save)
        val btn_saveNote=view.findViewById<ImageButton>(R.id.btn_saveNote)

        val msg_section=view.findViewById<ScrollView>(R.id.msg_section)
        val details_section=view.findViewById<LinearLayout>(R.id.details_section)
        val sendmsg_container=view.findViewById<LinearLayout>(R.id.sendmsg_container)

        val note_container=view.findViewById<LinearLayout>(R.id.note_container)





        val  retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val reqbody = SharedNote("", "",guser.name, args.task.id)

        retIn.get_sharedNote(token = guser.token, reqbody).enqueue(object : Callback<List<SharedNote>> {
            override fun onFailure(call: Call<List<SharedNote>>, t: Throwable) {
                val appContext = context?.applicationContext ?: return
                Toast.makeText(appContext, "no conection ", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<List<SharedNote>>, response: Response<List<SharedNote>>) {
                if (response.code() == 200) {

                    Notes= response.body()!!

                    // populate the sharedmesg section
                    val scope= MainScope()
                    scope.launch {
                        for (note in Notes){

                            val textview=TextView(context)
                            val usertextview=TextView(context)
                            if(note.userName!=guser.name){
                                usertextview.text= " "+ note.userName
                                usertextview.setTextColor( getResources().getColor(R.color.teal_200))
                                note_container.addView(usertextview)
                                textview.text="   "+note.note
                                textview.setPadding(10,10,10,10)
                                textview.setMinimumHeight(100)
                                textview.setBackgroundResource(R.drawable.notebackground)
                                note_container.addView(textview)
                            }else{
                                usertextview.text= "   "
                                usertextview.gravity= Gravity.END
                                usertextview.setTextColor( getResources().getColor(R.color.black))
                                note_container.addView(usertextview)
                                textview.text="   "+note.note
                                textview.setPadding(10,10,10,10)
                                textview.setMinimumHeight(100)
                                textview.setBackgroundResource(R.drawable.notesenderbackground)
                                note_container.addView(textview)


                            }

                        }
                    }

                    btn_details.setOnClickListener {
                        btn_details.setBackgroundColor( getResources().getColor(R.color.white))
                        btn_details.setTextColor(getResources().getColor(R.color.black))
                        btn_msg.setBackgroundColor( getResources().getColor(R.color.black))
                        btn_msg.setTextColor(getResources().getColor(R.color.white))

                        val description=view.findViewById<TextView>(R.id.description)
                        val check=view.findViewById<CheckBox>(R.id.checkBox2)

                        description.text="       "+task.description
                        check.isChecked=task.finished
                        details_section.visibility=View.VISIBLE
                        btn_save.visibility=View.VISIBLE
                        msg_section.visibility=View.GONE
                        sendmsg_container.visibility=View.GONE

                        btn_save.setOnClickListener {
                            task.finished=check.isChecked
                            retIn.update_task(token = guser.token,task).enqueue(object : Callback<ResponseBody> {
                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    val appContext = context?.applicationContext ?: return
                                    Toast.makeText(appContext,"No conection ", Toast.LENGTH_LONG).show()

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
                    btn_msg.setOnClickListener {
                        btn_msg.setBackgroundColor( getResources().getColor(R.color.white))
                        btn_msg.setTextColor(getResources().getColor(R.color.black))
                        btn_details.setBackgroundColor( getResources().getColor(R.color.black))
                        btn_details.setTextColor(getResources().getColor(R.color.white))

                        details_section.visibility=View.GONE
                        btn_save.visibility=View.GONE
                        msg_section.visibility=View.VISIBLE
                        sendmsg_container.visibility=View.VISIBLE
                    }

                    btn_saveNote.setOnClickListener {
                        val note=view.findViewById<TextView>(R.id.note)
                        val Nnote= SharedNote(note.text.toString(),"",guser.name,args.task.id)
                        val textview=TextView(context)
                        val usertextview=TextView(context)
                        usertextview.text= "   "
                        usertextview.gravity= Gravity.END
                        usertextview.setTextColor( getResources().getColor(R.color.black))
                        textview.setBackgroundResource(R.drawable.notesenderbackground)
                        note_container.addView(usertextview)

                        textview.text="   "+note.text.toString()
                        note.setText("")
                        textview.setPadding(10,10,10,10)
                        textview.setMinimumHeight(100)
                        note_container.addView(textview)
                        val  retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

                        retIn.add_sharedNote(token = guser.token,Nnote).enqueue(object : Callback<ResponseBody> {
                            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                val appContext = context?.applicationContext ?: return
                                Toast.makeText(appContext,"no conection ", Toast.LENGTH_LONG).show()

                            }
                            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                                if (response.code() == 200) {
                                    val appContext = context?.applicationContext ?: return
                                    Toast.makeText(appContext,"success ", Toast.LENGTH_LONG).show()
                                } else {
                                    val appContext = context?.applicationContext ?: return
                                    Toast.makeText(appContext,"try again ", Toast.LENGTH_LONG).show()
                                }
                            }
                        })
                    }


                } else {
                    val appContext = context?.applicationContext ?: return
                    Toast.makeText(appContext, "try again ", Toast.LENGTH_LONG).show()
                }
            }
        })
        return view
    }


}