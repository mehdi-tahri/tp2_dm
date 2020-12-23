package com.example.tp2.authentification

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class LoginForm(
    @SerialName("email_login")
    val email_login: String,
    @SerialName("password_login")
    val password_login: String
)
