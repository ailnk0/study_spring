package com.example.real_fight_web.domain.item

import jakarta.persistence.*

@Entity
@DiscriminatorValue("B")
class Book : Item() {
    var author: String = ""
    var isbn: String = ""
}
