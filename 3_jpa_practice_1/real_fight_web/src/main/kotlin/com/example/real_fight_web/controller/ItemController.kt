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

    @GetMapping("/items/{itemId}/edit")
    fun updateItemForm(@PathVariable itemId: Long, model: Model): String {
        val book = itemService.findOne(itemId) as Book
        val bookId = book.id ?: throw IllegalStateException("책 정보가 없습니다.")
        val form = BookForm(
            id = bookId,
            name = book.name,
            price = book.price,
            stockQuantity = book.stockQuantity,
            author = book.author,
            isbn = book.isbn
        )

        model.addAttribute("form", form)
        return "items/updateItemForm"
    }

    @PostMapping("/items/{itemId}/edit")
    fun updateItem(@PathVariable itemId: Long, @ModelAttribute("form") form: BookForm): String {
        val book = Book(
            name = form.name,
            price = form.price,
            stockQuantity = form.stockQuantity,
            author = form.author,
            isbn = form.isbn
        )
        book.id = itemId

        itemService.saveItem(book)
        return "redirect:/items"
    }
}
