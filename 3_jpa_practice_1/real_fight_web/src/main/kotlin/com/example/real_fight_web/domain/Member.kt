package com.example.real_fight_web.domain

import jakarta.persistence.*

@Entity
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null,

    var name: String = "",

    @Embedded
    val address: Address = Address(),

    @OneToMany(mappedBy = "member")
    val orders: MutableList<Orders> = mutableListOf(),
) {
    fun getMemberId(): Long {
        return id ?: throw IllegalStateException("아이디가 없습니다.")
    }
}
