package com.sparta.myblog.domain;

import com.sparta.myblog.dto.BlogRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlogTest {
    @Test
    @DisplayName("정상 케이스")
    void createProduct_Normal() {
// given
        String title = "오리온 꼬북칩 초코츄러스맛";
        String name = "꼬부기";
        String contents = "꼬부꼬부꼬북이 꼬북칩 존맛탱";

        BlogRequestDto requestDto = new BlogRequestDto(
                title,
                name,
                contents
        );

// when
        Blog blog = new Blog(requestDto);

// then
        assertNull(blog.getId());
        assertEquals(title, blog.getTitle());
        assertEquals(name, blog.getName());
        assertEquals(contents, blog.getContents());
    }
}