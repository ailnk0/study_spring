package com.example.real_fight_web.dto

import com.example.real_fight_web.domain.*
import java.time.*

data class OrdersSimpleDto(
    var orderId: Long,
    var name: String,
    var orderDate: LocalDateTime,
    var orderStatus: OrderStatus,
    var address: Address
) {
    companion object {
        fun fromEntity(orders: Orders): OrdersSimpleDto {
            return OrdersSimpleDto(
                orderId = orders.id!!,
                name = orders.member.name,
                orderDate = orders.orderDate,
                orderStatus = orders.status,
                address = orders.member.address
            )
        }

        fun of(
            orderId: Long,
            name: String,
            orderDate: LocalDateTime,
            orderStatus: OrderStatus,
            address: Address
        ): OrdersSimpleDto {
            return OrdersSimpleDto(
                orderId = orderId,
                name = name,
                orderDate = orderDate,
                orderStatus = orderStatus,
                address = address
            )
        }
    }
}
