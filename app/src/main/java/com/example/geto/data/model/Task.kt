package com.example.geto.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.FileDescriptor
@Parcelize
data class Task(
        var id:String,
        val description: String,
        var finished :Boolean,
        val project_id:String,
) : Parcelable
