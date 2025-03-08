package com.example.real_fight_web.repository

import com.example.real_fight_web.domain.Member
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository

@Repository
class MemberRepository {
    @PersistenceContext
    lateinit var em: EntityManager

    fun save(member: Member): Long {
        em.persist(member)
        return member.id
    }

    fun find(id: Long): Member {
        return em.find(Member::class.java, id)
    }
}