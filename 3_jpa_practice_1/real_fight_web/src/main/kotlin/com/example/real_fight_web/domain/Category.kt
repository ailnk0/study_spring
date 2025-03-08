package com.example.real_fight_web.domain

import com.example.real_fight_web.domain.item.*
import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class Category {
    @Id
    @GeneratedValue
    var id: Long = 0

    var name: String = ""

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    var items: MutableList<Item> = mutableListOf()

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category? = null

    @OneToMany(mappedBy = "parent")
    var child: MutableList<Category> = mutableListOf()
}
