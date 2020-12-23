package com.example.tp2.authentification

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class SignupResponse(
    @SerialName("token")
    val token: String
)
