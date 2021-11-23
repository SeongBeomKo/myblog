package com.sparta.myblog.service;

import com.sparta.myblog.domain.UserRoleEnum;
import com.sparta.myblog.domain.User;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(SignupRequestDto requestDto) {
        String nickname = requestDto.getNickname();
        String email = requestDto.getEmail();
        // 회원 ID 중복 확인
        Optional<User> found = userRepository.findByEmail(email);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        //패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }
        // 여기 방문자 추가

        User user = new User(nickname, password, email, role);
        userRepository.save(user);
    }
}
