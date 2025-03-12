package com.example.real_fight_web.api

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.dto.*
import com.example.real_fight_web.repository.*
import com.example.real_fight_web.service.*
import org.springframework.web.bind.annotation.*

@RestController
class OrdersApiController(
    private val ordersRepository: OrdersRepository,
    private val ordersService: OrdersService
) {
    @GetMapping("/api/v1/orders")
    fun ordersV1(): List<Orders> {
        // 문제 1: 엔티티 직렬화 문제
        // LAZY 로딩으로 인해 실제 member, delivery는 proxy 객체이기 때문에 json 직렬화에 실패한다.
        // 해결책1: DTO로 변환하여 반환한다. (권장)
        // 해결책2: Hibernate5Module을 사용하면 해결 가능
        // 해결책3: 무한 참조를 막기 위해 엔티티에 @JsonIgnore로 양방향 연관관계를 끊어준다.

        // 문제 2: 지연 로딩으로 인한 N * N 문제

        val orders = ordersRepository.findAll()
        orders.map {
            it.member.name // Lazy 강제 초기화
            it.delivery.address // Lazy 강제 초기화
            it.orderItems.map { oi ->
                oi.item.name // Lazy 강제 초기화
            }
        }

        return orders
    }

    @GetMapping("/api/v2/orders")
    fun ordersV2(): List<OrdersDto> {
        // 문제: 지연 로딩으로 인한 N + 1 문제 발생
        val orders = ordersRepository.findAll()
        return orders.map {
            OrdersDto.fromEntity(it) // Lazy 강제 초기화
        }
    }

    @GetMapping("/api/v3/orders")
    fun ordersV3(): List<OrdersDto> {
        // distinct fetch join으로 최적화
        // 문제 페이지 안되고 데이터 갯수가 부정확안 문제 발생
        val orders = ordersRepository.findAllWithMemberDeliveryXToMany()
        return orders.map { OrdersDto.fromEntity(it) }
    }

    @GetMapping("/api/v3/orders/paging")
    fun ordersV3Paging(
        @RequestParam(defaultValue = "0") firstResult: Int,
        @RequestParam(defaultValue = "100") maxResult: Int,
    ): List<OrdersDto> {
        // (권장)
        // XToOne 관계는 fetch join으로 최적화
        // XToMany 컬렉션은 hibernate.default_batch_fetch_size, @BatchSize로 최적화
        return ordersService.getAllWithPaging(firstResult, maxResult)
    }

//    @GetMapping("/api/v4/orders")
//    fun ordersV4(): List<OrdersDto> {
//        // 고수준 최적화 - JPA에서 DTO로 바로 조회
//        // 약간 더 쿼리 최적화 가능하지만 아키텍처 포기
//        return ordersRepository.findAllXWithDtoXToOne()
//    }
}
