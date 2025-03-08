package com.example.real_fight_web.domain.item

import jakarta.persistence.*

@Entity
@DiscriminatorValue("B")
class Book(
    var author: String = "",
    var isbn: String = ""
) : Item()
