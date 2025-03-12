package com.example.real_fight_web.service

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.dto.*
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

    fun findOrdersJpqlStr(ordersSearch: OrdersSearch): List<Orders> {
        // 주문 조회
        return ordersRepository.findAllJpqlStr(ordersSearch)
    }

    fun findOrdersQueryDsl(ordersSearch: OrdersSearch): List<Orders> {
        // 주문 조회
        return ordersRepository.findAllQueryDsl(ordersSearch)
    }

    fun getAllWithPaging(first: Int = 0, max: Int = 100): List<OrdersDto> {
        // XToOne 관계만 fetch join으로 최적화
        // 페이징 가능
        val orders = ordersRepository.findAllWithMemberDeliveryXToOne(first, max)

        // XToMany는 Batch Lazy Loading으로 조회
        // application.yml에 spring.jpa.properties.hibernate.default_batch_fetch_size: 100 추가
        // n번 쿼리가 아니라, 최대 batch size만큼 쿼리로 조회
        return orders.map {
            OrdersDto.fromEntity(it) // Lazy 강제 초기화
        }
    }

    fun getAllWithDtoXToMany(): List<OrdersFlatDto> {
        // 고수준 최적화 - JPA에서 DTO로 바로 조회
        // 약간 더 쿼리 최적화 가능하지만 아키텍처 포기
        val flats = ordersRepository.findOrdersFlatDto()
        val uniqueFlats = flats.distinctBy { it.id }
        return uniqueFlats
    }
}
