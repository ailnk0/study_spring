package com.example.real_fight_web.repository

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.dto.*
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

    fun findAll(ordersSearch: OrdersSearch): List<Orders> {
        val jpql = StringBuilder("SELECT o FROM Orders o JOIN o.member m WHERE 1=1")
        if (ordersSearch.orderStatus != null) {
            jpql.append(" AND o.status = :status")
        }
        if (!ordersSearch.memberName.isNullOrBlank()) {
            jpql.append(" AND m.name LIKE :name")
        }
        val query = em.createQuery(jpql.toString(), Orders::class.java)

        ordersSearch.orderStatus?.let { query.setParameter("status", it) }
        ordersSearch.memberName?.let { query.setParameter("name", "%$it%") }

        return query.setMaxResults(1000).resultList
    }
}
