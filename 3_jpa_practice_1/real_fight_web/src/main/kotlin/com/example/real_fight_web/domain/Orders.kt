package com.example.real_fight_web.domain

import jakarta.persistence.*
import jakarta.persistence.FetchType.*
import java.time.*

@Entity
class Orders private constructor(
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    val id: Long? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orderItems: MutableList<OrderItem> = mutableListOf(),

    @OneToOne(fetch = LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "delivery_id")
    var delivery: Delivery,

    val orderDate: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.ORDER,
) {
    companion object {
        fun createOrder(member: Member, delivery: Delivery, orderItems: List<OrderItem>): Orders {
            val order = Orders(member = member, delivery = delivery)
            order.changeMember(member)
            order.changeDelivery(delivery)
            orderItems.forEach { order.addOrderItem(it) }
            return order
        }
    }

    fun changeMember(member: Member) {
        this.member = member
        member.orders.add(this)
    }

    fun changeDelivery(delivery: Delivery) {
        this.delivery = delivery
        delivery.order = this
    }

    fun addOrderItem(orderItem: OrderItem) {
        orderItems.add(orderItem)
        orderItem.order = this
    }

    fun cancel() {
        if (delivery.status == DeliveryStatus.COMP) {
            throw IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.")
        }
        this.status = OrderStatus.CANCEL
        orderItems.forEach { it.cancel() }
    }

    fun getTotalPrice(): Int {
        return orderItems.sumOf { it.getTotalPrice() }
    }
}
