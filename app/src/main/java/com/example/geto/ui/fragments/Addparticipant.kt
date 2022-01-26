package com.example.geto.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.geto.R
import com.example.geto.data.Rest.ApiInterface
import com.example.geto.data.Rest.RetrofitInstance
import com.example.geto.data.model.SignInBody
import com.example.geto.data.model.User
import com.example.geto.data.requestBody.addPart_Body
import com.example.geto.guser
import com.example.geto.ui.login.LoginFragmentDirections
import com.google.gson.Gson
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
 * Use the [Addparticipant.newInstance] factory method to
 * create an instance of this fragment.
 */
class Addparticipant : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val args :AddparticipantArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_addparticipant, container, false)
        val btn_addParticipant=view.findViewById<Button>(R.id.add_participant2)
        val email=view.findViewById<EditText>(R.id.email_participant)


        btn_addParticipant.setOnClickListener {
            if(email.text.toString().isNotEmpty()){
                val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
                val reqBody=addPart_Body(email.text.toString(),args.project.id)
                retIn.add_participant(token = guser.token,reqBody).enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        //loadingProgressBar.visibility = View.INVISIBLE
                        val appContext = context?.applicationContext ?: return
                        Toast.makeText(appContext,"No connection ", Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.code() == 200) {
                            //loadingProgressBar.visibility = View.INVISIBLE
                            val action =AddparticipantDirections.actionAddparticipantToProjetDetaills(args.project)
                            Navigation.findNavController(view).navigate(action)
                        } else {
                            //loadingProgressBar.visibility = View.INVISIBLE
                            val appContext = context?.applicationContext ?: return
                            Toast.makeText(appContext,"wrong email or password ", Toast.LENGTH_LONG).show()
                        }
                    }
                })


            }

        }
        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Addparticipant.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Addparticipant().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}