package com.example.real_fight_web.dto.item

import com.example.real_fight_web.domain.item.*

data class ItemDto(
    val id: Long?,
    val name: String,
    val price: Int,
    val stockQuantity: Int
) {
    companion object {
        fun fromEntity(item: Item): ItemDto {
            return ItemDto(
                id = item.id,
                name = item.name,
                price = item.price,
                stockQuantity = item.stockQuantity
            )
        }
    }
}
