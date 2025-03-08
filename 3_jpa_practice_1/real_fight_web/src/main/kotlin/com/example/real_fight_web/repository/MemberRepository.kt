package com.example.real_fight_web.repository

import com.example.real_fight_web.domain.*
import jakarta.persistence.*
import org.springframework.stereotype.*

@Repository
class MemberRepository(
    @PersistenceContext
    private val em: EntityManager
) {
    fun save(member: Member): Long {
        em.persist(member)
        return member.id
    }

    fun findOne(id: Long): Member? = em.find(Member::class.java, id)

    fun findAll(): List<Member> {
        return em.createQuery("select m from Member m", Member::class.java)
            .resultList
    }

    fun findByName(name: String): List<Member> {
        return em.createQuery("select m from Member m where m.name = :name", Member::class.java)
            .setParameter("name", name)
            .resultList
    }
}
