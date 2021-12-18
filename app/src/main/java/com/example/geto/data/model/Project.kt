package com.example.geto.data.model

data class Project(
    val id:Int,
    val title:String,
    val description :String,
    val tasks:List<String>,
    val users :List<User>,

)
