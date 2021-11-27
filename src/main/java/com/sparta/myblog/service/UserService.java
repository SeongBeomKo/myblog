package com.sparta.myblog.service;

import com.sparta.myblog.domain.User;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(SignupRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();

        //패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        User user = new User(nickname, password, email);
        userRepository.save(user);
    }


}
