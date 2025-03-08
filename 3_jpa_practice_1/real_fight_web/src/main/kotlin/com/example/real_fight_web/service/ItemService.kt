package com.example.real_fight_web.service

import com.example.real_fight_web.domain.item.*
import com.example.real_fight_web.repository.*
import org.springframework.transaction.annotation.Transactional

import org.springframework.stereotype.*

@Service
@Transactional(readOnly = true)
class ItemService(private val itemRepository: ItemRepository) {
    @Transactional
    fun saveItem(item: Item) {
        itemRepository.save(item)
    }

    fun findItems(): List<Item> {
        return itemRepository.findAll()
    }

    fun findOne(itemId: Long): Item {
        return itemRepository.findOne(itemId) ?: throw IllegalStateException("상품 정보가 없습니다.")
    }
}
