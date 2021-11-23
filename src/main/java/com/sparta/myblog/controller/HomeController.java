package com.sparta.myblog.controller;

import com.sparta.myblog.domain.User;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.service.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
       // if(userDetails != null) {
            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("Can't find " + userDetails.getUsername()));
            model.addAttribute("username", user.getNickname());
        //}
        return "index";
        //return "login_and_signup";
    }

    @GetMapping("/visitor")
    public String visitorPass(Model model) {
        model.addAttribute("username", "visitor");
        return "index";
    }
}
