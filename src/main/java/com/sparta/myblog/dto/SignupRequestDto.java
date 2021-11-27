package com.sparta.myblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
public class SignupRequestDto {

    @Pattern(regexp="(?=.*[0-9])|(?=.*[a-zA-Z]).{3,}", message = "닉네임은 3자 이상의 영문 대,소문자 혹은 숫자만 가능")
    private String nickname;

    //@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z]).{4,}", message = "비밀번호는 영문 대,소문자와 숫자가 포함된 4자 이상.")
    @Size(min = 4, message = "비밀번호는 4자 이상.")
    private String password;

    private String passwordCheck;

    private String email;
}
