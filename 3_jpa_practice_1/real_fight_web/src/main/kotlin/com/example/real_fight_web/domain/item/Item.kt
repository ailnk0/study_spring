package com.example.real_fight_web.domain.item

import com.example.real_fight_web.domain.*
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
open class Item(
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    open val id: Long = 0,

    open var name: String = "",

    open var price: Int = 0,

    open var stockQuantity: Int = 0,

    @OneToMany(mappedBy = "item", cascade = [CascadeType.ALL], orphanRemoval = true)
    open val orderItems: MutableList<OrderItem> = mutableListOf(),

    @ManyToMany(mappedBy = "items")
    open val categories: MutableList<Category> = mutableListOf()
) {
    fun addCategory(category: Category) {
        categories.add(category)
    }
}
