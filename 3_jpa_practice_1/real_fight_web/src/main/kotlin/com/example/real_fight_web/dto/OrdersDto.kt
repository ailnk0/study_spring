package com.example.real_fight_web.dto

import com.example.real_fight_web.domain.*
import java.time.*

data class OrdersDto(
    var orderId: Long,
    var name: String,
    var orderDate: LocalDateTime,
    var orderStatus: OrderStatus,
    var address: Address,
    var orderItems: List<OrdersItemDto>
) {
    companion object {
        fun fromEntity(orders: Orders): OrdersDto {
            return OrdersDto(
                orderId = orders.id!!,
                name = orders.member.name,
                orderDate = orders.orderDate,
                orderStatus = orders.status,
                address = orders.member.address,
                orderItems = orders.orderItems.map { OrdersItemDto.fromEntity(it) }
            )
        }
    }
}
