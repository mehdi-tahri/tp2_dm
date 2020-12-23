package com.example.tp2.network

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserInfo(
        @SerialName("email")
        val email: String,
        @SerialName("firstname")
        val firstName: String,
        @SerialName("lastname")
        val lastName: String,
        @SerialName("avatar")
        val avatar: String = "https://image.freepik.com/vecteurs-libre/urss-marteau-faucille_125371-91.jpg"
)
