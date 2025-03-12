package com.example.real_fight_web.api

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.dto.*
import com.example.real_fight_web.repository.*
import org.springframework.web.bind.annotation.*

@RestController
class OrdersSimpleApiController(
    private val ordersRepository: OrdersRepository
) {
    @GetMapping("/api/v1/simple-orders")
    fun ordersV1(): List<Orders> {
        // 문제 1: 엔티티 직렬화 문제
        // LAZY 로딩으로 인해 실제 member, delivery는 proxy 객체이기 때문에 json 직렬화에 실패한다.
        // 해결책1: DTO로 변환하여 반환한다. (권장)
        // 해결책2: Hibernate5Module을 사용하면 해결 가능
        // 해결책3: 무한 참조를 막기 위해 엔티티에 @JsonIgnore로 양방향 연관관계를 끊어준다.

        // 문제 2: 지연 로딩으로 인한 N + 1 문제

        val orders = ordersRepository.findAll()
        orders.map {
            it.member.name // Lazy 초기화
            it.delivery.address // Lazy 초기화
        }

        return orders
    }

    @GetMapping("/api/v2/simple-orders")
    fun ordersV2(): List<OrdersSimpleDto> {
        // 문제: 지연 로딩으로 인한 N + 1 문제 발생
        val orders = ordersRepository.findAll()
        return orders.map {
            OrdersSimpleDto.fromEntity(it) // Lazy 강제 초기화
        }
    }

    @GetMapping("/api/v3/simple-orders")
    fun ordersV3(): List<OrdersSimpleDto> {
        // fetch join으로 최적화 (권장)
        val orders = ordersRepository.findAllWithMemberDeliveryXToOne()
        return orders.map { OrdersSimpleDto.fromEntity(it) }
    }

    @GetMapping("/api/v4/simple-orders")
    fun ordersV4(): List<OrdersSimpleDto> {
        // 고수준 최적화 - JPA에서 DTO로 바로 조회
        // 약간 더 쿼리 최적화 가능하지만 아키텍처 포기
        return ordersRepository.findAllWithDtoXToOne()
    }
}
