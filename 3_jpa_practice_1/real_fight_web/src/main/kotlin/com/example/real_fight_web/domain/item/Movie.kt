package com.example.real_fight_web.domain.item

import jakarta.persistence.*

@Entity
@DiscriminatorValue("M")
class Movie : Item() {
    var director: String = ""
    var actor: String = ""
}
