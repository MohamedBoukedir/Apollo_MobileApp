package com.example.geto.data.model

import android.provider.ContactsContract

data class User(
    val name:String,
    val password:String,
    val id:Int,
    val projects :List<Project>,
    val email:ContactsContract.CommonDataKinds.Email
)
