package com.example.real_fight_web.domain

import jakarta.persistence.*

@Entity
class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long = 0

    @OneToOne(mappedBy = "delivery")
    lateinit var order: Orders

    @Embedded
    val address: Address = Address()

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus = DeliveryStatus.READY
}
