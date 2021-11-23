package com.sparta.myblog.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private String contents;
    private Long blogId;
}
