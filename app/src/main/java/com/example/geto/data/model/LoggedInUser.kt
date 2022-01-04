package com.example.geto.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
        val name:String,
        val password:String,
        val projects :List<Project>,
        val email:String,
        val token :String,
        val userId: String,
        val displayName: String
)