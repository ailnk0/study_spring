package com.example.real_fight_web.service

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.repository.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
@Transactional(readOnly = true)
class OrdersService(
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository,
    private val ordersRepository: OrdersRepository
) {
    @Transactional
    fun orders(memberId: Long, itemId: Long, count: Int): Long {
        // 회원 조회
        val member = memberRepository.findOne(memberId) ?: throw IllegalStateException("회원 정보가 없습니다.")

        // 상품 조회
        val item = itemRepository.findOne(itemId) ?: throw IllegalStateException("상품 정보가 없습니다.")

        // 배송정보 생성
        val delivery = Delivery(address = member.address)

        // 상품 생성
        val orderItem = OrderItem.createOrderItem(
            item = item,
            count = count
        )

        // 주문 생성
        val orders = Orders.createOrders(
            member = member,
            delivery = delivery,
            orderItems = listOf(orderItem)
        )

        // 주문 저장
        ordersRepository.save(orders)

        return orders.id ?: throw IllegalStateException("주문 생성에 실패했습니다.")
    }

    @Transactional
    fun cancelOrders(ordersId: Long) {
        // 주문 조회
        val orders = ordersRepository.findOne(ordersId) ?: throw IllegalStateException("주문 정보가 없습니다.")

        // 주문 취소
        orders.cancel()
    }

    fun findOrders(): List<Orders> {
        // 주문 조회
        return ordersRepository.findAll()
    }
}
