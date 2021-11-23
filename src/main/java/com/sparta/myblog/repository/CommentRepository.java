package com.sparta.myblog.repository;

import com.sparta.myblog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByModifiedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end);
    List<Comment> findAllByBlogIdOrderByCreatedAtDesc(Long blogId);
}
