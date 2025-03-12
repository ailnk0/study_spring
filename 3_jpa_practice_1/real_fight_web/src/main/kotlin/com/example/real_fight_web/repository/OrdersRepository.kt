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

    fun saveAll(orders: List<Orders>) {
        orders.forEach { save(it) }
    }

    fun findOne(id: Long): Orders? {
        return em.find(Orders::class.java, id)
    }

    fun count(): Long {
        return em.createQuery("select count(o) from Orders o", Long::class.javaObjectType)
            .singleResult
    }

    fun findAll(): List<Orders> {
        return em.createQuery("select o from Orders o", Orders::class.java)
            .resultList
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

    fun findAllWithMemberDeliveryXToOne(first: Int = 0, max: Int = 100): List<Orders> {
        return em.createQuery(
            "SELECT o FROM Orders o" +
                    " JOIN FETCH o.member m" +
                    " JOIN FETCH o.delivery d", Orders::class.java
        )
            .setFirstResult(first)
            .setMaxResults(max)
            .resultList
    }

    fun findAllWithDtoXToOne(): List<OrdersSimpleDto> {
        return em.createQuery(
            "SELECT new com.example.real_fight_web.dto.OrdersSimpleDto(o.id, m.name, o.orderDate, o.status, m.address) FROM Orders o" +
                    " JOIN o.member m", OrdersSimpleDto::class.java
        ).resultList
    }

    fun findAllWithMemberDeliveryXToMany(): List<Orders> {
        // JPQL distinct 키워드를 사용하면 DB에서 중복을 제거해준다.
        // 단점1: 페이징 불가능
        // 단점2: 컬렉션 fetch join은 1개만 사용 가능
        return em.createQuery(
            "SELECT dictinct o FROM Orders o" +
                    " JOIN FETCH o.member m" +
                    " JOIN FETCH o.delivery d" +
                    " JOIN FETCH o.orderItems oi" + // 컬렉션 fetch join
                    " JOIN FETCH oi.item i", Orders::class.java
        )
            .setFirstResult(2) // 페이징 불가능 - 동작하지 않음
            .setMaxResults(100) // 페이징 불가능 - 동작하지 않음
            .resultList
    }

    fun findAllWithDtoXToMany(): List<OrdersDto> {
        val result = findOrdersDto()
        val orderIds = result.map { it.orderId }

        val orderItems = em.createQuery(
            "SELECT new com.example.real_fight_web.dto.OrdersItemDto(oi.id, oi.item, oi.count)" +
                    " FROM OrdersItem oi" +
                    " JOIN oi.item i" +
                    " WHERE oi.order.id int :orderIds", OrdersItemDto::class.java
        )
            .setParameter("orderIds", orderIds)
            .resultList

        result.forEach {
            it.orderItems = orderItems.filter { oi -> oi.id == it.orderId }
        }
        return result
    }

    fun findOrdersItemDto( orderId: Long): List<OrdersItemDto> {
        return em.createQuery(
            "SELECT new com.example.real_fight_web.dto.OrdersItemDto(oi.id, oi.item, oi.count)" +
                    " FROM OrdersItem oi" +
                    " JOIN oi.item i" +
                    " WHERE oi.order.id = :orderId", OrdersItemDto::class.java
        ).resultList
    }

    fun findOrdersDto(): List<OrdersDto> {
        return em.createQuery(
            "SELECT new com.example.real_fight_web.dto.OrdersDto(o.id, m.name, o.orderDate, o.status, m.address)" +
                    " FROM Orders o" +
                    " JOIN o.member m", OrdersDto::class.java
        ).resultList
    }

    fun findOrdersFlatDto(): List<OrdersFlatDto> {
        return em.createQuery(
            "SELECT new com.example.real_fight_web.dto.OrdersFlatDto(o.id, m.name, o.orderDate, o.status, m.address, i.name, oi.count)" +
                    " FROM Orders o" +
                    " JOIN o.member m" +
                    " JOIN o.orderItems oi" +
                    " JOIN oi.item i", OrdersFlatDto::class.java
        ).resultList
    }
}
