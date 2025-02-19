package com.example.real_fight_web

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import lombok.Getter
import lombok.Setter


@Entity
@Getter
@Setter
class Member {
    @Id
    @GeneratedValue
    var id: Long = 0
    var username: String = ""
}

