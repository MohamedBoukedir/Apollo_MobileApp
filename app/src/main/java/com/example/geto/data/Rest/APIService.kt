package com.example.geto.data.Rest

import com.example.geto.data.model.SignInBody
import com.example.geto.data.model.User
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APIService {

    lateinit var user : User
    var login :Boolean = false
    fun login(username :String,password :String){
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val signInInfo = SignInBody(username, password)

        retIn.login(signInInfo).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                login=false
            }
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    var gson = Gson()
                    user = gson?.fromJson(response.body()?.string(), User::class.java)
                    login=true
                } else {
                    login =false
                }
            }
        })
    }
}