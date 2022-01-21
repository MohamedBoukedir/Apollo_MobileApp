package com.example.geto.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.FileDescriptor
@Parcelize
data class Task(
        val description: String,
        val finished :Boolean,
        val project_id:String,
) : Parcelable
