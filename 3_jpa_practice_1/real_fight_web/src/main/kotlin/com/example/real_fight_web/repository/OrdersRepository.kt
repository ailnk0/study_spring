package com.example.real_fight_web.repository

import com.example.real_fight_web.domain.*
import jakarta.persistence.*
import org.springframework.stereotype.*

@Repository
class OrdersRepository(private val em: EntityManager) {

    fun save(order: Orders) {
        em.persist(order)
    }

    fun findOne(id: Long): Orders? {
        return em.find(Orders::class.java, id)
    }

    fun findAll(): List<Orders> {
        return em.createQuery("select o from Order o", Orders::class.java)
            .resultList
    }
}
