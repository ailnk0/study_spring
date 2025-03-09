package com.example.real_fight_web.controller

import com.example.real_fight_web.domain.item.*
import com.example.real_fight_web.service.*
import org.springframework.stereotype.*
import org.springframework.ui.*
import org.springframework.web.bind.annotation.*

@Controller
class ItemController(private val itemService: ItemService) {
    @GetMapping("/items/new")
    fun createForm(): String {
        return "items/createItemForm"
    }

    @PostMapping("/items/new")
    fun create(bookForm: BookForm): String {
        val book = Book(
            name = bookForm.name,
            price = bookForm.price,
            stockQuantity = bookForm.stockQuantity,
            author = bookForm.author,
            isbn = bookForm.isbn
        )
        itemService.saveItem(book)
        return "redirect:/"
    }

    @GetMapping("/items")
    fun list(model: Model): String {
        val items = itemService.findItems()
        model.addAttribute("items", items)
        return "items/itemList"
    }
}
