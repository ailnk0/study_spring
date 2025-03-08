package com.example.real_fight_web.domain.item

import jakarta.persistence.*

@Entity
@DiscriminatorValue("BOOK")
class Book(
    name: String,
    price: Int,
    stockQuantity: Int,
    var author: String,
    var isbn: String
) : Item(name = name, price = price, stockQuantity = stockQuantity)
