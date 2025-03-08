package com.example.real_fight_web.domain

import com.example.real_fight_web.domain.item.*
import jakarta.persistence.*
import jakarta.persistence.FetchType.*

@Entity
class Category(
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    val id: Long? = null,

    var name: String,

    @ManyToMany
    @JoinTable(
        name = "category_item",
        joinColumns = [JoinColumn(name = "category_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    val items: MutableList<Item> = mutableListOf(),

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    var parent: Category? = null,

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    val children: MutableList<Category> = mutableListOf()
) {
    fun addChildCategory(child: Category) {
        children.add(child)
        child.parent = this
    }

    fun addItem(item: Item) {
        items.add(item)
    }
}
