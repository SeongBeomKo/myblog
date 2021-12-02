package com.sparta.myblog.repository;

import com.sparta.myblog.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBlogIdOrderByCreatedAtDesc(Long blogId);
}
