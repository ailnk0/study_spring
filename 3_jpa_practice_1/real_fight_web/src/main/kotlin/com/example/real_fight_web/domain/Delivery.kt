package com.example.real_fight_web.domain

import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long = 0

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    lateinit var order: Orders

    @Embedded
    val address: Address = Address.createAddress("", "", "")

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus = DeliveryStatus.READY
}
