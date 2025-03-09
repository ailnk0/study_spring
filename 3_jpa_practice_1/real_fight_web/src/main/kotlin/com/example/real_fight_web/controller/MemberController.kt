package com.example.real_fight_web.controller

import com.example.real_fight_web.domain.*
import com.example.real_fight_web.service.*
import jakarta.validation.*
import org.springframework.stereotype.*
import org.springframework.ui.*
import org.springframework.validation.*
import org.springframework.web.bind.annotation.*

@Controller
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/members/new")
    fun createForm(model: Model): String {
        model.addAttribute("memberForm", MemberForm());
        return "members/createMemberForm"
    }

    @PostMapping("/members/new")
    fun create(@Valid memberForm: MemberForm, result: BindingResult): String {
        if (result.hasErrors()) {
            return "members/createMemberForm"
        }
        val address = Address(
            memberForm.city,
            memberForm.street,
            memberForm.zipcode
        )
        val member = Member(
            name = memberForm.name,
            address = address
        )
        memberService.join(member)
        return "redirect:/"
    }
}
