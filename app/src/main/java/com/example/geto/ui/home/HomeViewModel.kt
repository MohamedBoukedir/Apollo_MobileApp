package com.example.geto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geto.data.model.Project
import com.example.geto.data.model.User

class HomeViewModel : ViewModel() {
    lateinit var projects :LiveData<List<Project>>
    lateinit var user: User
}