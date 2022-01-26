package com.example.geto.data.Rest

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import com.example.geto.WEB_SERVICE_URL
import com.example.geto.data.model.*
import com.example.geto.data.requestBody.NewProject
import com.example.geto.data.requestBody.UserId
import com.example.geto.data.requestBody.addPart_Body
import com.example.geto.guser
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {
    @Headers("Content-Type:application/json")
    @POST("/login")
    fun login(@Body user:SignInBody): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("/register")
    fun registerUser(
        @Body user: User
    ): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("/projects/add")
    fun add_project(@Body nproject: NewProject): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("/projects/getProjectes") //projects/getProjectes
    fun get_projects(@Body userid: UserId): retrofit2.Call<List<Project>>

    @Headers("Content-Type:application/json")
    @POST("/tasks/add") //projects/getProjectes
    fun add_task(@Header("Authorization") token: String,@Body task: Task): retrofit2.Call<Task>

    @Headers("Content-Type:application/json")
    @PUT("/tasks/update") //projects/getProjectes
    fun update_task(@Header("Authorization") token: String,@Body task: Task): retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("/users/addParticipant")
    fun add_participant(@Header("Authorization") token:String,@Body addpartBody: addPart_Body) : retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("/Notes/add")
    fun add_sharedNote(@Header("Authorization") token:String,@Body note: SharedNote) : retrofit2.Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("/Notes/getNotes")
    fun get_sharedNote(@Header("Authorization") token:String,@Body note: SharedNote) : retrofit2.Call<List<SharedNote>>



}
class RetrofitInstance {
    companion object {
        val BASE_URL=WEB_SERVICE_URL

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()
        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}