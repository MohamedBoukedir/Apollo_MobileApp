package com.example.geto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geto.data.model.Project
import com.example.geto.data.model.User

class HomeViewModel : ViewModel() {



    private val pr1= Project(1,"h1","desc1",listOf<String>("hhhh","hhhhhh"),listOf<User>())
    private fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
    val projects: LiveData<List<Project>> = MutableLiveData<List<Project>>().default(listOf(pr1,pr1,pr1,pr1,pr1,pr1,pr1,pr1,pr1))





}