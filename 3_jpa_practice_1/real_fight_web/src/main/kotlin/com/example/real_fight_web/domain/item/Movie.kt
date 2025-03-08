package com.example.real_fight_web.domain.item

import jakarta.persistence.*

@Entity
@DiscriminatorValue("M")
class Movie(
    var director: String = "",
    var actor: String = ""
) : Item()
