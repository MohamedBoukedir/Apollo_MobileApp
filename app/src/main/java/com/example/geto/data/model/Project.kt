package com.example.geto.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project(
    val id:Int,
    val title:String,
    val description :String,
    val tasks:List<String>,
    val users :List<User>,
): Parcelable
