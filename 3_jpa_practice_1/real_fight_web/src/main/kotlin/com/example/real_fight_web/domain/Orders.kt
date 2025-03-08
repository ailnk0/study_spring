package com.example.real_fight_web.domain

import jakarta.persistence.*
import java.time.*

@Entity
class Orders {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    val id: Long = 0

    @ManyToOne
    @JoinColumn(name = "member_id")
    lateinit var member: Member

    @OneToMany(mappedBy = "order")
    val orderItems: MutableList<OrderItem> = mutableListOf()

    @OneToOne
    @JoinColumn(name = "delivery_id")
    lateinit var delivery: Delivery

    val orderDate: LocalDateTime = LocalDateTime.now()

    @Enumerated(EnumType.STRING)
    val status: OrderStatus = OrderStatus.ORDER
}
