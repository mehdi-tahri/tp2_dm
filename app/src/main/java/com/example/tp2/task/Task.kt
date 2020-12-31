package com.example.tp2.task

import java.io.Serializable

@kotlinx.serialization.Serializable
data class Task (val id : String, val title : String, val description : String = "default desc") :  Serializable {
}