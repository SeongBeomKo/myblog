package com.sparta.myblog.service;

import com.sparta.myblog.domain.Blog;
import com.sparta.myblog.dto.BlogRequestDto;
import com.sparta.myblog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public Long update(Long id, BlogRequestDto requestDto) {
        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        blog.setName(requestDto.getName());
        blog.setTitle(requestDto.getTitle());
        blog.setContents(requestDto.getContents());
        return blog.getId();
    }
}
