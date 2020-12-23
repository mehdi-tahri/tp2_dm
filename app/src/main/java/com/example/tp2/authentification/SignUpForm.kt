package com.example.tp2.authentification

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SignUpForm(
    @SerialName("firstname")
    val firstname: String,
    @SerialName("lastname")
    val lastname: String,
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String,
    @SerialName("password_confirmation")
    val password_confirmation: String
)
