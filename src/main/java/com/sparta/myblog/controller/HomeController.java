package com.sparta.myblog.controller;

import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
           return homeService.home(model, userDetails);
    }

    @GetMapping("/visitor")
    public String visitorPass(Model model) {
        model.addAttribute("username", "visitor");
        return "index";
    }
}
