package com.example.real_fight_web.service

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.domain.item.*
import com.example.real_fight_web.repository.*
import jakarta.persistence.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.test.context.*
import org.springframework.test.context.junit.jupiter.*
import org.springframework.transaction.annotation.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@Transactional
class OrdersServiceTest {

    @Autowired
    lateinit var ordersService: OrdersService

    @Autowired
    lateinit var ordersRepository: OrdersRepository

    @Autowired
    lateinit var em: EntityManager

    @Test
    fun order() {
        // given
        val member = createTestMember()
        val book = createTestBook()
        val orderCount = 2
        val memberId = member.id ?: throw IllegalStateException("회원 정보가 없습니다.")
        val bookId = book.id ?: throw IllegalStateException("상품 정보가 없습니다.")

        // when
        val ordersId = ordersService.orders(memberId, bookId, orderCount)

        // then
        val foundOrders = ordersRepository.findOne(ordersId) ?: throw IllegalStateException("주문 정보가 없습니다.")
        assertEquals(OrderStatus.ORDER, foundOrders.status, "상품 주문시 상태는 ORDER")
        assertEquals(1, foundOrders.orderItems.size, "주문한 상품 종류 수가 정확해야 한다.")
        assertEquals(10000 * orderCount, foundOrders.getTotalPrice(), "주문 가격은 가격 * 수량이다.")
        assertEquals(8, book.stockQuantity, "주문 수량만큼 재고가 줄어야 한다.")
    }

    @Test
    fun exceedStock() {
        // given
        val member = createTestMember()
        val book = createTestBook()
        val orderCount = 11
        val memberId = member.id ?: throw IllegalStateException("회원 정보가 없습니다.")
        val bookId = book.id ?: throw IllegalStateException("상품 정보가 없습니다.")

        // when
        val exception = assertThrows<IllegalStateException> {
            ordersService.orders(memberId, bookId, orderCount)
        }

        // then
        assertEquals("재고 수량 부족", exception.message)
    }

    @Test
    fun cancelOrder() {
        // given
        val member = createTestMember()
        val book = createTestBook()
        val orderCount = 2
        val memberId = member.id ?: throw IllegalStateException("회원 정보가 없습니다.")
        val bookId = book.id ?: throw IllegalStateException("상품 정보가 없습니다.")
        val ordersId = ordersService.orders(memberId, bookId, orderCount)

        // when
        ordersService.cancelOrders(ordersId)

        // then
        val foundOrders = ordersRepository.findOne(ordersId) ?: throw IllegalStateException("주문 정보가 없습니다.")
        assertEquals(OrderStatus.CANCEL, foundOrders.status, "주문 취소시 상태는 CANCEL")
        assertEquals(10, book.stockQuantity, "주문 취소된 상품은 그만큼 재고가 증가해야 한다.")
    }

    private fun createTestMember(): Member {
        val member = Member(
            name = "memberA",
            address = Address(city = "서울", street = "1", zipcode = "1111")
        )
        em.persist(member)
        return member
    }

    private fun createTestBook(): Book {
        val book = Book(
            name = "bookA",
            price = 10000,
            stockQuantity = 10,
            author = "authorA",
            isbn = "123123"
        )
        em.persist(book)
        return book
    }
}
