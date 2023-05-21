package br.eng.joaovictor.ghproject.model

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int,
    val login: String,
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
