package com.example.geto.ui.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController

import com.example.geto.R
import com.example.geto.data.Rest.ApiInterface
import com.example.geto.data.Rest.RetrofitInstance
import com.example.geto.data.model.SignInBody
import com.example.geto.data.model.User
import com.example.geto.ui.home.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_login, container, false)
        val signButton=view.findViewById<Button>(R.id.btn_signin2)
        signButton.setOnClickListener {
            val bundle = Bundle()
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signIn, bundle)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)
        val usernameEditText = view.findViewById<EditText>(R.id.username)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val loginButton = view.findViewById<Button>(R.id.login)
        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.loading)

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            var username= usernameEditText.text.toString()
            var password=passwordEditText.text.toString()
            var user:User
            val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
            val signInInfo = SignInBody(username, password)

            retIn.login(signInInfo).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.code() == 200) {
                        var gson = Gson()
                        user = gson?.fromJson(response.body()?.string(), User::class.java)
                        loadingProgressBar.visibility = View.INVISIBLE
                        val bundle = Bundle()
                        findNavController(view).navigate(R.id.action_loginFragment_to_navigation_home, bundle)
                    } else {
                    }
                }
            })

        }
    }


    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }
}