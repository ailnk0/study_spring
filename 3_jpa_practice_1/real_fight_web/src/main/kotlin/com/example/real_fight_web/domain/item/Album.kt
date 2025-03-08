package com.example.real_fight_web.domain.item

import jakarta.persistence.*

@Entity
@DiscriminatorValue("A")
class Album(
    name: String,
    price: Int,
    stockQuantity: Int,
    var artist: String,
    var etc: String
) : Item(name = name, price = price, stockQuantity = stockQuantity)
