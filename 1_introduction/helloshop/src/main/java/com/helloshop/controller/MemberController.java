package com.helloshop.controller;

import com.helloshop.repository.Member;
import com.helloshop.service.MemberService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

  private final MemberService service;

  public MemberController(MemberService service) {
    this.service = service;
  }

  @GetMapping("members")
  public String membersPage(Model model) {
    List<Member> members = service.findMembers();
    model.addAttribute("members", members);
    return "members/page";
  }

  @GetMapping("members/new")
  public String joinNewMemberPage() {
    return "members/new/page";
  }

  @PostMapping("members/new")
  public String joinNewMember(MemberForm form) {
    Member newMember = new Member(form.getEmail());
    try {
      service.join(newMember);
      return "redirect:/members";
    } catch (IllegalStateException e) {
      return "redirect:/error-msg?msg=" + e.getMessage();
    }
  }
}
