package com.helloshop.controller;

import com.helloshop.service.MemberService;

public class MemberController {

  private final MemberService memberSerive;

  public MemberController(MemberService memberSerive) {
    this.memberSerive = memberSerive;
  }
}
