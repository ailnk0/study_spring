package com.example.real_fight_web.domain.item

import com.example.real_fight_web.domain.*
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    val id: Long = 0

    val name: String = ""
    val price: Int = 0
    val stockQuantity: Int = 0

    @OneToMany(mappedBy = "item")
    val orderItems: MutableList<OrderItem> = mutableListOf()

    @ManyToMany(mappedBy = "items")
    val categories: MutableList<Category> = mutableListOf()
}
