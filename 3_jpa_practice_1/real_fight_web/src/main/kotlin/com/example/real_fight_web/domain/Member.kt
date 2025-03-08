package com.example.real_fight_web.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Member {
    @Id
    @GeneratedValue
    var id: Long = 0
    var username: String = ""
}

