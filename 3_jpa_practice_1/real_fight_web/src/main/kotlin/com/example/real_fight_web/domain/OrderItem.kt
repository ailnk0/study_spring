package com.example.real_fight_web.domain

import com.example.real_fight_web.domain.item.*
import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class OrderItem(
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long = 0,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    var order: Orders,

    val orderPrice: Int,

    val count: Int
)
