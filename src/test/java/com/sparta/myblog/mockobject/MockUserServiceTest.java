package com.sparta.myblog.mockobject;


import com.sparta.myblog.Validator.SignUpValidator;
import com.sparta.myblog.domain.User;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MockUserServiceTest {

    @Mock
    UserRepository userRepository;


        @Nested
        @DisplayName("회원가입 테스트용 객체 생성")
        class CreateMockUsers {

            private String nickname;
            private String email;
            private String password;
            private String passwordCheck;

            @Test
            @DisplayName("정상 케이스1")
            void registerUser_Normal() {

                nickname = "helloboy";
                email = "kkkk@gmail.com";
                password = "qwer12";
                passwordCheck = "qwer12";

                SignupRequestDto requestDto = new SignupRequestDto(
                        nickname,
                        password,
                        passwordCheck,
                        email
                );
                SignUpValidator suv = new SignUpValidator(userRepository);
                assertEquals("회원가입 성공", suv.getValidMessageForTest(requestDto));
                User user = new User(requestDto.getNickname(), requestDto.getPassword(), requestDto.getEmail());
                userRepository.save(user);
            }

            @Test
            @DisplayName("정상 케이스2")
            void registerUser_Normal2() {

                nickname = "hellboy";
                email = "kkk@gmail.com";
                password = "qwer12";
                passwordCheck = "qwer12";

                SignupRequestDto requestDto = new SignupRequestDto(
                        nickname,
                        password,
                        passwordCheck,
                        email
                );
                SignUpValidator suv = new SignUpValidator(userRepository);
                assertEquals("회원가입 성공", suv.getValidMessageForTest(requestDto));
                User user = new User(requestDto.getNickname(), requestDto.getPassword(), requestDto.getEmail());
                userRepository.save(user);
            }

            @Nested
            @DisplayName("실패 케이스")
            class createMockUserFail {

                @Nested
                @DisplayName("닉네임 확인")
                class Nickname {
                    @Test
                    @DisplayName("사이즈 에러")
                    void fail1() {
                        nickname = "he";
                        email = "kkk@gmail.com";
                        password = "qwer12";
                        passwordCheck = "qwer12";

                        SignupRequestDto requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );

                        SignUpValidator suv = new SignUpValidator(userRepository);
                        assertEquals("닉네임을 확인하세요.", suv.getValidMessageForTest(requestDto));
                    }

                    @Test
                    @DisplayName("영문, 대소문자, 숫자 이외 사용")
                    void fail2() {
                        nickname = "hel_1";
                        email = "kkk@gmail.com";
                        password = "qwer12";
                        passwordCheck = "qwer12";

                        SignupRequestDto requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );

                        SignUpValidator suv = new SignUpValidator(userRepository);
                        assertEquals("닉네임을 확인하세요.", suv.getValidMessageForTest(requestDto));
                    }

                    @Test
                    @DisplayName("중복 닉네임 확인1")
                    void fail3() {
                        MockUserRepository mockUserRepository = new MockUserRepository();

                        nickname = "helloboy";
                        email = "kkkk@gmail.com";
                        password = "qwer12";
                        passwordCheck = "qwer12";

                        SignupRequestDto requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );
                        User user = new User(requestDto.getNickname(), requestDto.getPassword(), requestDto.getEmail());
                        mockUserRepository.save(user);

                        nickname = "helloboy";
                        email = "kkkkk@gmail.com";
                        password = "qwer12";
                        passwordCheck = "qwer12";

                        requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );
                        assertTrue(mockUserRepository.existsByNickname(requestDto.getNickname()));
                    }

                    @Test
                    @DisplayName("중복 닉네임 확인2")
                    void fail4() {

                        MockUserRepository mockUserRepository = new MockUserRepository();

                        nickname = "hellboy";
                        email = "kkkk@gmail.com";
                        password = "qwer12";
                        passwordCheck = "qwer12";

                        SignupRequestDto requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );
                        User user = new User(requestDto.getNickname(), requestDto.getPassword(), requestDto.getEmail());
                        mockUserRepository.save(user);

                        nickname = "hellboy";
                        email = "kkkkkk@gmail.com";
                        password = "qwer12";
                        passwordCheck = "qwer12";

                        requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );
                        assertTrue(mockUserRepository.existsByNickname(requestDto.getNickname()));
                    }

                    @Test
                    @DisplayName("중복 닉네임 확인3")
                    void fail5() {
                        MockUserRepository mockUserRepository = new MockUserRepository();

                        nickname = "helloboy";
                        email = "kkkk@gmail.com";
                        password = "qwer12";
                        passwordCheck = "qwer12";

                        SignupRequestDto requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );
                        User user = new User(requestDto.getNickname(), requestDto.getPassword(), requestDto.getEmail());
                        when(userRepository.save(user)).thenReturn(user);
                        //userRepository.save(user);

                        nickname = "helloboy";
                        email = "kkkkk@gmail.com";
                        password = "qwer12";
                        passwordCheck = "qwer12";

                        requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );
                        User user2 = new User(requestDto.getNickname(), requestDto.getPassword(), requestDto.getEmail());
                        assertEquals(userRepository.save(user).getNickname(), user2.getNickname());
                    }
                }

                @Nested
                @DisplayName("패스워드 확인")
                class password {

                    @Test
                    @DisplayName("사이즈 에러")
                    void fail1() {
                        nickname = "hell";
                        email = "kkk@gmail.com";
                        password = "qwe";
                        passwordCheck = "qwe";

                        SignupRequestDto requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );

                        SignUpValidator suv = new SignUpValidator(userRepository);
                        assertEquals("비밀번호를 확인하세요.", suv.getValidMessageForTest(requestDto));
                    }

                    @Test
                    @DisplayName("닉네임 포함 에러")
                    void fail2() {
                        nickname = "hell";
                        email = "kkk@gmail.com";
                        password = "hell1";
                        passwordCheck = "hell1";

                        SignupRequestDto requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );

                        SignUpValidator suv = new SignUpValidator(userRepository);
                        assertEquals("비밀번호는 닉네임을 포함할 수 없습니다.", suv.getValidMessageForTest(requestDto));
                    }

                    @Test
                    @DisplayName("비밀번호, 비밀번호 확인 불일치 에러")
                    void fail3() {
                        nickname = "qwer";
                        email = "kkk@gmail.com";
                        password = "hell12";
                        passwordCheck = "hell11";

                        SignupRequestDto requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );

                        SignUpValidator suv = new SignUpValidator(userRepository);
                        assertEquals("비밀번호가 일치하지 않습니다.", suv.getValidMessageForTest(requestDto));
                    }

                    @Test
                    @DisplayName("비밀번호, 비밀번호 확인 불일치 에러2")
                    void fail4() {
                        nickname = "qwer";
                        email = "kkk@gmail.com";
                        password = "hqwel12";
                        passwordCheck = "hrerell11";

                        SignupRequestDto requestDto = new SignupRequestDto(
                                nickname,
                                password,
                                passwordCheck,
                                email
                        );

                        SignUpValidator suv = new SignUpValidator(userRepository);
                        assertEquals("비밀번호가 일치하지 않습니다.", suv.getValidMessageForTest(requestDto));
                    }
                }

            }
        }


}
