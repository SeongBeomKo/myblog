package com.sparta.myblog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.security.UserDetailsImpl;
import com.sparta.myblog.service.KakaoUserService;
import com.sparta.myblog.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    public UserController(UserService userService, KakaoUserService kakaoUserService) {
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
    }

    // 회원 로그인/회원가입 페이지
    @GetMapping("/user/login")
    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        System.out.println(userDetails);
        if(userDetails != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("message", "이미 로그인 하셨습니다.");
        } else
            model.addAttribute("loggedIn", false);
        return "login_and_signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid SignupRequestDto requestDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            // 회원가입 실패시, 입력 데이터를 유지
            model.addAttribute("userDto", requestDto);
            // 유효성 통과 못한 필드와 메시지를 핸들링
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            return "login_and_signup";
        }

        boolean checkEmailDuplicate = userService.checkEmailDuplicate(requestDto.getEmail());
        boolean checkNicknameDuplicate = userService.checkNicknameDuplicate(requestDto.getNickname());

        if(checkEmailDuplicate) {
            model.addAttribute("error", "이미 가입된 이메일 주소입니다.");
            return "login_and_signup";
        } else if(checkNicknameDuplicate) {
            model.addAttribute("error", "이미 사용중인 닉네임입니다.");
            return "login_and_signup";
        } else if(!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다");
            return "login_and_signup";
        } else if(!requestDto.getPassword().contains(requestDto.getNickname())) {
            model.addAttribute("error", "비밀번호는 닉네임을 포함할 수 없습니다.");
            return "login_and_signup";
        }
        else {
            userService.registerUser(requestDto);
            model.addAttribute("success", "회원가입 성공");
            return "login_and_signup";
        }
    }

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        kakaoUserService.kakaoLogin(code);
        return "redirect:/";
    }
}
