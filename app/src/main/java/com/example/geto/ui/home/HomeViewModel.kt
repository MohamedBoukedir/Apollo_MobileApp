package com.example.geto.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geto.data.model.Project
import com.example.geto.data.model.User

class HomeViewModel : ViewModel() {



    private val pr1= Project(1,"project 1","desc1",listOf<String>("task1","task2"),listOf<User>())
    private val pr2= Project(1,"project 2","description",listOf<String>("task1","task2"),listOf<User>())
    private val pr3= Project(1,"project 3","description 3",listOf<String>("task1","task2"),listOf<User>())
    private val pr4= Project(1,"project 4","description 4",listOf<String>("task1","task2"),listOf<User>())
    private val pr5= Project(1,"project 5","description 5",listOf<String>("task1","task2"),listOf<User>())
    private val pr6= Project(1,"project 7","description 7",listOf<String>("task1","task2"),listOf<User>())
    private val pr7 =Project(1,"project 6","description 6",listOf<String>("task1","task2"),listOf<User>())


    private fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
    val projects: LiveData<List<Project>> = MutableLiveData<List<Project>>().default(listOf(pr1,pr2,pr3,pr4,pr5,pr6,pr7,pr1,pr2,pr3,pr4,pr5))





}