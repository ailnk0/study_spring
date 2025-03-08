package com.example.real_fight_web.repository

import com.example.real_fight_web.domain.item.*
import jakarta.persistence.*
import org.springframework.stereotype.*

@Repository
class ItemRepository(
    private val em: EntityManager
) {
    fun save(item: Item) {
        if (item.id == null) {
            em.persist(item)
        } else {
            em.merge(item)
        }
    }

    fun findOne(id: Long): Item {
        return em.find(Item::class.java, id)
    }

    fun findAll(): List<Item> {
        return em.createQuery("select i from Item i", Item::class.java)
            .resultList
    }
}
