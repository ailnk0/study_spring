package com.example.real_fight_web.service

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.domain.item.*
import com.example.real_fight_web.dto.*
import com.example.real_fight_web.repository.*
import jakarta.persistence.*
import org.assertj.core.api.Assertions
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

    private lateinit var memberA: Member
    private lateinit var memberB: Member
    private lateinit var bookA: Book
    private lateinit var bookB: Book

    @BeforeEach
    fun setUp() {
        memberA = createTestMemberA()
        memberB = createTestMemberB()
        bookA = createTestBookA()
        bookB = createTestBookB()
    }

    @Test
    fun order() {
        // given
        val orderCount = 2

        // when
        val ordersId = ordersService.orders(memberA.getMemberId(), bookA.getItemId(), orderCount)

        // then
        val foundOrders = ordersRepository.findOne(ordersId) ?: throw IllegalStateException("주문 정보가 없습니다.")
        assertEquals(OrderStatus.ORDER, foundOrders.status, "상품 주문시 상태는 ORDER")
        assertEquals(1, foundOrders.orderItems.size, "주문한 상품 종류 수가 정확해야 한다.")
        assertEquals(10000 * orderCount, foundOrders.getTotalPrice(), "주문 가격은 가격 * 수량이다.")
        assertEquals(8, bookA.stockQuantity, "주문 수량만큼 재고가 줄어야 한다.")
    }

    @Test
    fun exceedStock() {
        // given
        val orderCount = 11

        // when
        val exception = assertThrows<IllegalStateException> {
            ordersService.orders(memberA.getMemberId(), bookA.getItemId(), orderCount)
        }

        // then
        assertEquals("재고 수량 부족", exception.message)
    }

    @Test
    fun cancelOrder() {
        // given
        val orderCount = 2
        val ordersId = ordersService.orders(memberA.getMemberId(), bookA.getItemId(), orderCount)

        // when
        ordersService.cancelOrders(ordersId)

        // then
        val foundOrders = ordersRepository.findOne(ordersId) ?: throw IllegalStateException("주문 정보가 없습니다.")
        assertEquals(OrderStatus.CANCEL, foundOrders.status, "주문 취소시 상태는 CANCEL")
        assertEquals(10, bookA.stockQuantity, "주문 취소된 상품은 그만큼 재고가 증가해야 한다.")
    }

    @Nested
    inner class SearchOrdersTest {

        @Test
        fun searchOrdersByMemberJpqlStr() {
            // given
            val orderCount = 2
            ordersService.orders(memberA.getMemberId(), bookA.getItemId(), orderCount)
            ordersService.orders(memberA.getMemberId(), bookB.getItemId(), orderCount)
            ordersService.orders(memberB.getMemberId(), bookB.getItemId(), orderCount)

            // when
            val ordersSearch = OrdersSearch(memberName = memberA.name)
            val orders = ordersService.findOrdersJpqlStr(ordersSearch)

            // then
            Assertions.assertThat(orders).hasSize(2)
                .withFailMessage("회원의 주문 내역은 2개여야 합니다.")
        }

        @Test
        fun searchCancelledOrdersJpqlStr() {
            // given
            val orderCount = 2
            val ordersId = ordersService.orders(memberA.getMemberId(), bookA.getItemId(), orderCount)
            ordersService.orders(memberA.getMemberId(), bookB.getItemId(), orderCount)
            ordersService.orders(memberB.getMemberId(), bookB.getItemId(), orderCount)
            ordersService.cancelOrders(ordersId)

            // when
            val ordersSearch = OrdersSearch(orderStatus = OrderStatus.CANCEL)
            val orders = ordersService.findOrdersJpqlStr(ordersSearch)

            // then
            Assertions.assertThat(orders).hasSize(1)
                .withFailMessage("취소된 주문은 1개여야 합니다.")
        }

        @Test
        fun searchOrdersByMemberQueryDsl() {
            // given
            val orderCount = 2
            ordersService.orders(memberA.getMemberId(), bookA.getItemId(), orderCount)
            ordersService.orders(memberA.getMemberId(), bookB.getItemId(), orderCount)
            ordersService.orders(memberB.getMemberId(), bookB.getItemId(), orderCount)

            // when
            val ordersSearch = OrdersSearch(memberName = memberA.name)
            val orders = ordersService.findOrdersQueryDsl(ordersSearch)

            // then
            Assertions.assertThat(orders).hasSize(2)
                .withFailMessage("회원의 주문 내역은 2개여야 합니다.")
        }

        @Test
        fun searchCancelledOrdersQueryDsl() {
            // given
            val orderCount = 2
            val ordersId = ordersService.orders(memberA.getMemberId(), bookA.getItemId(), orderCount)
            ordersService.orders(memberA.getMemberId(), bookB.getItemId(), orderCount)
            ordersService.orders(memberB.getMemberId(), bookB.getItemId(), orderCount)
            ordersService.cancelOrders(ordersId)

            // when
            val ordersSearch = OrdersSearch(orderStatus = OrderStatus.CANCEL)
            val orders = ordersService.findOrdersQueryDsl(ordersSearch)

            // then
            Assertions.assertThat(orders).hasSize(1)
                .withFailMessage("취소된 주문은 1개여야 합니다.")
        }
    }

    private fun createTestMemberA(): Member {
        val member = Member(
            name = "memberA",
            address = Address(city = "서울", street = "1", zipcode = "1111")
        )
        em.persist(member)
        return member
    }

    private fun createTestMemberB(): Member {
        val member = Member(
            name = "memberB",
            address = Address(city = "제주", street = "1", zipcode = "1111")
        )
        em.persist(member)
        return member
    }

    private fun createTestBookA(): Book {
        val book = Book(
            name = "bookA",
            price = 10000,
            stockQuantity = 10,
            author = "authorA",
            isbn = "1"
        )
        em.persist(book)
        return book
    }

    private fun createTestBookB(): Book {
        val book = Book(
            name = "bookB",
            price = 20000,
            stockQuantity = 5,
            author = "authorB",
            isbn = "2"
        )
        em.persist(book)
        return book
    }
}
