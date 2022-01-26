package com.example.geto.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project(
        val title:String,
        val description :String,
        val users_id : ArrayList<String>,
        val id:String,
        val tasks:ArrayList<Task>
): Parcelable
