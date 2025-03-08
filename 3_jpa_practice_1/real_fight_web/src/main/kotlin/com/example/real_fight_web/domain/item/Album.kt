package com.example.real_fight_web.domain.item

import jakarta.persistence.*

@Entity
@DiscriminatorValue("A")
class Album(
    var artist: String = "",
    var etc: String = ""
) : Item()

