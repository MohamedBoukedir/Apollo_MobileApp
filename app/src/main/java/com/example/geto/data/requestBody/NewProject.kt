package com.example.geto.data.requestBody

import com.example.geto.data.model.Project
import com.example.geto.data.model.Task

data class NewProject(
        val tasks: List<Task>,
        val project:Project,
        val token:String
)
