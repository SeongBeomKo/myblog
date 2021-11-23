package com.sparta.myblog.controller;

import com.sparta.myblog.domain.Comment;
import com.sparta.myblog.dto.CommentRequestDto;
import com.sparta.myblog.repository.CommentRepository;
import com.sparta.myblog.service.CommentService;
import com.sparta.myblog.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;

    @PostMapping("/comments")
    public Comment createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Comment comment = new Comment(requestDto);
        comment.setNickname(userDetails.getUser().getNickname());
        return commentRepository.save(comment);
    }

    @DeleteMapping("/comments/{id}")
    public Long deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
        return id;
    }

    @PutMapping("/comments/{id}")
    public Long updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.update(id, requestDto);
    }
}
