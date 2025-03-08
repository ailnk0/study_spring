package com.example.real_fight_web.domain

import jakarta.persistence.*

@Embeddable
class Address(
    val city: String = "",
    val street: String = "",
    val zipcode: String = ""
)
