package com.helloshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

  @GetMapping("/")
  public String indexPage() {
    return "index";
  }

  @GetMapping("error-msg")
  public String errorPage(@RequestParam("msg") String msg, Model model) {
    model.addAttribute("msg", msg);
    return "error-msg";
  }
}
