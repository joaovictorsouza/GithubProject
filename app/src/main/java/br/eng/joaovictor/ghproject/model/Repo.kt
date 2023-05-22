package br.eng.joaovictor.ghproject.model

import com.google.gson.annotations.SerializedName

data class Repo(
    val id: Int,
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    val description: String,
    val language: String,
    val url: String
)
