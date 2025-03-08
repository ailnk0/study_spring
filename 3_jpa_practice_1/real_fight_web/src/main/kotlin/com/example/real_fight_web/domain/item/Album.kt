package com.example.real_fight_web.domain.item

import jakarta.persistence.*

@Entity
@DiscriminatorValue("A")
class Album : Item() {
    var artist: String = ""
    var etc: String = ""
}
