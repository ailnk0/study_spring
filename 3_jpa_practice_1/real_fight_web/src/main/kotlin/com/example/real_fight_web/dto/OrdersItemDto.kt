package com.example.real_fight_web.dto

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.domain.item.*
import com.example.real_fight_web.dto.item.*

data class OrdersItemDto(
    val id: Long? = null,
    val item: ItemDto,
    val count: Int
) {
    companion object {
        fun fromEntity(ordersItem: OrderItem): OrdersItemDto {
            return OrdersItemDto(
                id = ordersItem.id,
                item = ItemDto.fromEntity(ordersItem.item),
                count = ordersItem.count
            )
        }
    }
}
