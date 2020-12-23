package com.example.tp2.authentification

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class LoginResponse(
    @SerialName("token")
    val token: String
)
