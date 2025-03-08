package com.example.real_fight_web.domain

import com.example.real_fight_web.domain.item.*
import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class OrderItem private constructor(
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    val id: Long? = null,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    val item: Item,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    var order: Orders? = null,

    val count: Int
) {
    companion object {
        fun createOrderItem(item: Item, count: Int): OrderItem {
            val orderItem = OrderItem(
                item = item,
                count = count
            )
            item.removeStock(count)
            return orderItem
        }
    }

    fun cancel() {
        item.addStock(count)
    }

    fun getTotalPrice(): Int {
        return item.price * count
    }
}
