package com.helloshop.controller;

import com.helloshop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

  private final MemberService memberSerive;

  @Autowired
  public MemberController(MemberService memberSerive) {
    this.memberSerive = memberSerive;
  }

  public MemberService getMemberSerive() {
    return memberSerive;
  }
}
