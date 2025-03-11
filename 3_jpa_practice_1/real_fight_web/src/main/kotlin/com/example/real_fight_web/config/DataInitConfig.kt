package com.example.real_fight_web.config

import com.example.real_fight_web.service.*
import org.springframework.boot.*
import org.springframework.context.annotation.*

@Configuration
class DataInitConfig(
    private val dataInitService: DataInitService,
) {
    @Bean
    fun init(): ApplicationRunner {
        return ApplicationRunner {
            dataInitService.insertSampleData()
        }
    }
}
