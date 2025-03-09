package com.example.real_fight_web.config

import com.querydsl.jpa.impl.*
import jakarta.persistence.*
import org.springframework.context.annotation.*

@Configuration
class QueryDslConfig {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Bean
    fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory(entityManager)
    }
}
