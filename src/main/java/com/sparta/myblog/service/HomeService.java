package com.sparta.myblog.service;

import com.sparta.myblog.domain.User;
import com.sparta.myblog.repository.UserRepository;
import com.sparta.myblog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final UserRepository userRepository;

    public String home(Model model, UserDetailsImpl userDetails) {
        User user = userRepository.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + userDetails.getUsername()));
        model.addAttribute("username", user.getNickname());
        return "index";
    }
}
