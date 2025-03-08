package com.example.real_fight_web.domain.item

import jakarta.persistence.*

@Entity
@DiscriminatorValue("M")
class Movie(
    name: String,
    price: Int,
    stockQuantity: Int,
    var director: String,
    var actor: String
) : Item(name = name, price = price, stockQuantity = stockQuantity)
