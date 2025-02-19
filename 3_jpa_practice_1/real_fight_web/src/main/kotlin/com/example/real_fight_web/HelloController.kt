package com.example.real_fight_web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.ui.Model

@Controller
class HelloController {

    @GetMapping("hello")
    fun hello(model: Model): String {
        model.addAttribute("data", "Hello")
        return "hello"
    }
}
