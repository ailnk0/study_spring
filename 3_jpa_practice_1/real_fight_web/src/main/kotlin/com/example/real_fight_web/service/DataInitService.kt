package com.example.real_fight_web.service

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.domain.item.*
import com.example.real_fight_web.repository.*
import org.springframework.stereotype.*
import org.springframework.transaction.annotation.*

@Service
@Transactional(readOnly = true)
class DataInitService(
    private val memberRepository: MemberRepository,
    private val itemRepository: ItemRepository,
    private val ordersRepository: OrdersRepository
) {

    @Transactional
    fun insertSampleData() {
        if (memberRepository.count() == 0L) {
            memberRepository.saveAll(
                listOf(
                    Member(name = "회원1", address = Address("서울", "1", "1111")),
                    Member(name = "회원2", address = Address("부산", "2", "2222")),
                    Member(name = "회원3", address = Address("광주", "3", "3333")),
                    Member(name = "회원4", address = Address("대구", "4", "4444")),
                    Member(name = "회원5", address = Address("인천", "5", "5555")),
                    Member(name = "회원6", address = Address("울산", "6", "6666"))
                )
            )
        }

        if (itemRepository.count() == 0L) {
            itemRepository.saveAll(
                listOf(
                    Book(name = "JPA1 BOOK", price = 10000, stockQuantity = 100, author = "저자1", isbn = "isbn1"),
                    Book(name = "JPA2 BOOK", price = 20000, stockQuantity = 100, author = "저자2", isbn = "isbn2"),
                    Book(name = "JPA3 BOOK", price = 30000, stockQuantity = 100, author = "저자3", isbn = "isbn3"),
                    Book(name = "JPA4 BOOK", price = 40000, stockQuantity = 100, author = "저자4", isbn = "isbn4"),
                    Book(name = "JPA5 BOOK", price = 50000, stockQuantity = 100, author = "저자5", isbn = "isbn5"),
                    Album(name = "JPA1 ALBUM", price = 30000, stockQuantity = 100, artist = "가수1", etc = "etc1"),
                    Album(name = "JPA2 ALBUM", price = 40000, stockQuantity = 100, artist = "가수2", etc = "etc2"),
                    Album(name = "JPA3 ALBUM", price = 50000, stockQuantity = 100, artist = "가수3", etc = "etc3"),
                    Album(name = "JPA4 ALBUM", price = 60000, stockQuantity = 100, artist = "가수4", etc = "etc4"),
                    Album(name = "JPA5 ALBUM", price = 70000, stockQuantity = 100, artist = "가수5", etc = "etc5")
                )
            )
        }

        if (ordersRepository.count() == 0L) {
            val member1 = memberRepository.findByName("회원1")[0]
            val item1 = itemRepository.findByName("JPA1 BOOK")[0]
            val item2 = itemRepository.findByName("JPA5 BOOK")[0]
            val orderItems = mutableListOf(
                OrderItem.createOrderItem(item = item1, count = 2),
                OrderItem.createOrderItem(item = item2, count = 1)
            )
            val delivery1 = Delivery(address = member1.address, status = DeliveryStatus.READY)
            val orders1 = Orders.createOrders(member = member1, delivery = delivery1, orderItems = orderItems)
            ordersRepository.save(orders1)

            val member2 = memberRepository.findByName("회원2")[0]
            val item3 = itemRepository.findByName("JPA2 ALBUM")[0]
            val item4 = itemRepository.findByName("JPA4 ALBUM")[0]
            val orderItems2 = mutableListOf(
                OrderItem.createOrderItem(item = item3, count = 3),
                OrderItem.createOrderItem(item = item4, count = 4)
            )
            val delivery2 = Delivery(address = member2.address, status = DeliveryStatus.READY)
            val orders2 = Orders.createOrders(member = member2, delivery = delivery2, orderItems = orderItems2)
            ordersRepository.save(orders2)
        }
    }
}