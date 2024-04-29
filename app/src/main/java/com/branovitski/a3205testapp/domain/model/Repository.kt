package com.branovitski.a3205testapp.domain.model

data class Repository(
    val name: String,
    val owner: Owner,
    val html_url: String,
    val description: String?,
    val language: String?,
)