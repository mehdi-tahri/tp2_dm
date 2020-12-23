package com.example.tp2.authentification

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class LoginForm(
    @SerialName("email")
    val email_login: String,
    @SerialName("password")
    val password_login: String
)
