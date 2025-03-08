package com.example.real_fight_web.domain

import com.example.real_fight_web.domain.item.*
import jakarta.persistence.*

@Entity
class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "item_id")
    lateinit var item: Item

    @ManyToOne
    @JoinColumn(name = "order_id")
    lateinit var order: Orders

    val orderPrice: Int = 0
    val count: Int = 0
}
