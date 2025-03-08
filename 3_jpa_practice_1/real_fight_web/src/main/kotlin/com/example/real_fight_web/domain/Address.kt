package com.example.real_fight_web.domain

import jakarta.persistence.*

@Embeddable
class Address private constructor(
    val city: String,
    val street: String,
    val zipcode: String
) {
    companion object {
        fun CreateAddress(city: String, street: String, zipcode: String): Address {
            return Address(city, street, zipcode)
        }
    }
}
