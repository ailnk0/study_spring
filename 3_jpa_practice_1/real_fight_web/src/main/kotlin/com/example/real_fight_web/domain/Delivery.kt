package com.example.real_fight_web.domain

import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class Delivery(
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    val id: Long? = null,

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    var order: Orders? = null,

    @Embedded
    val address: Address,

    @Enumerated(EnumType.STRING)
    var status: DeliveryStatus = DeliveryStatus.READY
)
