package com.example.real_fight_web.controller

import com.example.real_fight_web.dto.*
import com.example.real_fight_web.service.*
import org.springframework.stereotype.*
import org.springframework.ui.*
import org.springframework.web.bind.annotation.*

@Controller
class OrdersController(
    private val orderService: OrdersService,
    private val memberService: MemberService,
    private val itemService: ItemService,
) {
    @GetMapping("/order")
    fun createForm(model: Model): String {
        val members = memberService.findMembers()
        val items = itemService.findItems()
        model.addAttribute("members", members)
        model.addAttribute("items", items)
        return "order/orderForm"
    }

    @PostMapping("/order")
    fun order(
        @RequestParam("memberId") memberId: Long,
        @RequestParam("itemId") itemId: Long,
        @RequestParam("count") count: Int
    ): String {
        orderService.orders(memberId, itemId, count)
        return "redirect:/orders"
    }

    @GetMapping("/orders")
    fun orderList(@ModelAttribute("orderSearch") orderSearch: OrdersSearch, model: Model): String {
        val orders = orderService.findOrdersJpqlStr(orderSearch)
        model.addAttribute("orders", orders)
        return "order/orderList"
    }

    @PostMapping("/orders/{orderId}/cancel")
    fun cancelOrder(@PathVariable orderId: Long): String {
        orderService.cancelOrders(orderId)
        return "redirect:/orders"
    }
}
