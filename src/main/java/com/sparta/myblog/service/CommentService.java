package com.sparta.myblog.service;

import com.sparta.myblog.domain.Comment;
import com.sparta.myblog.dto.CommentRequestDto;
import com.sparta.myblog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long update(Long id, CommentRequestDto requestDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        comment.setBlogId(requestDto.getBlogId());
        comment.setContents(requestDto.getContents());
        return comment.getCommentId();
    }
}
