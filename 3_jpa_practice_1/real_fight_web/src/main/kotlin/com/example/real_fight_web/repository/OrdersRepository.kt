package com.example.real_fight_web.repository

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.dto.*
import com.querydsl.core.types.dsl.*
import com.querydsl.jpa.impl.*
import jakarta.persistence.*
import org.springframework.stereotype.*

@Repository
class OrdersRepository(
    private val em: EntityManager,
    private val query: JPAQueryFactory
) {

    fun save(order: Orders) {
        em.persist(order)
    }

    fun findOne(id: Long): Orders? {
        return em.find(Orders::class.java, id)
    }

    // JPQL 문자열 동적 쿼리
    fun findAllJpqlStr(ordersSearch: OrdersSearch): List<Orders> {
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

    // Querydsl 동적 쿼리
    fun findAllQueryDsl(ordersSearch: OrdersSearch): List<Orders> {
        val order: QOrders = QOrders.orders
        val member: QMember = QMember.member

        return query
            .select(order)
            .from(order)
            .join(order.member, member)
            .where(
                statusEq(ordersSearch.orderStatus),
                memberNameLike(ordersSearch.memberName)
            )
            .fetch()
    }

    fun statusEq(orderStatus: OrderStatus?): BooleanExpression? {
        return orderStatus?.let { QOrders.orders.status.eq(it) }
    }

    fun memberNameLike(memberName: String?): BooleanExpression? {
        return memberName?.let { QMember.member.name.like("%$it%") }
    }
}
