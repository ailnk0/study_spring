package com.example.real_fight_web.domain

import jakarta.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long = 0,

    var name: String = "",

    @Embedded
    val address: Address = Address.createAddress("", "", ""),

    @OneToMany(mappedBy = "member")
    val orders: MutableList<Orders> = mutableListOf(),
)
