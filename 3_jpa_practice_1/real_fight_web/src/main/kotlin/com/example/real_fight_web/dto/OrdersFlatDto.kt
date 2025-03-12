package com.example.real_fight_web.dto

import com.example.real_fight_web.domain.*
import java.time.*

data class OrdersFlatDto(
    val id: Long?,
    val name: String,
    val orderDate: LocalDateTime,
    val status: OrderStatus,
    val address: Address,
    val itemName: String,
    val count: Int,
)
