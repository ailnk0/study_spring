package com.example.real_fight_web.domain

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import java.time.*

@Entity
class Orders private constructor(
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    val id: Long = 0,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orderItems: MutableList<OrderItem> = mutableListOf(),

    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    val delivery: Delivery,

    val orderDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.ORDER
) {
    companion object {
        fun createOrder(member: Member, delivery: Delivery, orderItems: List<OrderItem>): Orders {
            val order = Orders(member = member, delivery = delivery)
            order.assignMember(member)
            order.assignDelivery(delivery)
            orderItems.forEach { order.addOrderItem(it) }
            return order
        }
    }

    private fun assignMember(member: Member) {
        member.orders.add(this)
    }

    private fun assignDelivery(delivery: Delivery) {
        delivery.order = this
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
    }
}
