package com.example.real_fight_web.domain

import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class Delivery private constructor(
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long = 0,

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    var order: Orders,

    @Embedded
    val address: Address,

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus = DeliveryStatus.READY
)
