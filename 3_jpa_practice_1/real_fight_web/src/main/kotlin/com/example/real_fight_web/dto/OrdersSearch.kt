package com.example.real_fight_web.dto

import com.example.real_fight_web.domain.*

data class OrdersSearch(
    val memberName: String? = null,
    val orderStatus: OrderStatus? = null
)
