package com.example.real_fight_web.controller

data class BookForm(
    val id: Long,
    val name: String,
    val price: Int,
    val stockQuantity: Int,
    val author: String,
    val isbn: String
)
