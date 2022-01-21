package com.example.geto.ui.Signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.geto.R
import com.example.geto.data.Rest.ApiInterface
import com.example.geto.data.Rest.RetrofitInstance
import com.example.geto.data.model.SignInBody
import com.example.geto.data.model.User
import com.example.geto.ui.login.LoginFragmentDirections
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class SignIn : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_sign_in, container, false)
        val btn_signin=view.findViewById<Button>(R.id.btn_signIn)
        val name=view.findViewById<EditText>(R.id.user_name)
        val password=view.findViewById<EditText>(R.id.user_password)
        val email=view.findViewById<EditText>(R.id.email)
        btn_signin.setOnClickListener {
            // to do implement authentication
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val registerInInfo = User(name.text.toString(),email.text.toString(),"")
            retIn.registerUser(registerInInfo).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    val appContext = context?.applicationContext ?: return
                    Toast.makeText(appContext,"something wrong", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.code() == 200) {
                        val bundle = Bundle()
                        Navigation.findNavController(view).navigate(R.id.action_signIn_to_loginFragment, bundle)
                        val appContext = context?.applicationContext ?: return
                        Toast.makeText(appContext,"welcome ", Toast.LENGTH_LONG).show()
                    } else if(response.code()==409) {
                        val appContext = context?.applicationContext ?: return
                        Toast.makeText(appContext,"already registered ", Toast.LENGTH_LONG).show()
                    }else{
                        val appContext = context?.applicationContext ?: return
                        Toast.makeText(appContext,"something wrong ", Toast.LENGTH_LONG).show()
                    }
                }
            })
        }
        return view

    }




}