package com.sparta.myblog.repository;

import com.sparta.myblog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByModifiedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end);
}
