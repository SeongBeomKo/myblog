package com.sparta.myblog.controller;

import com.sparta.myblog.domain.Blog;
import com.sparta.myblog.dto.BlogRequestDto;
import com.sparta.myblog.repository.BlogRepository;
import com.sparta.myblog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BlogController {

    private final BlogRepository blogRepository;

    private final BlogService blogService;

    @PostMapping("/api/blogs")
    public Blog createBlog(@RequestBody BlogRequestDto requestDto) {
        Blog blog = new Blog(requestDto);
        return blogRepository.save(blog);
    }

    @GetMapping("/api/blogs")
    public List<Blog> getBlog() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        return blogRepository.findAllByModifiedAtBetweenOrderByCreatedAtDesc(start, end);
    }

    @GetMapping("/api/blogs/detail")
    public ModelAndView getOneBlog(@RequestParam Long id) {

        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new NullPointerException("")
        );
        ModelAndView mv = new ModelAndView();
        mv.setViewName("post"); // 뷰의 이름
        mv.addObject("data", blog);
        return mv;
    }

    @PutMapping("/api/blogs/{id}")
    public Long updateBlog(@PathVariable Long id, @RequestBody BlogRequestDto requestDto) {
        return blogService.update(id, requestDto);
    }

    @DeleteMapping("/api/blogs/{id}")
    public Long deleteBlog(@PathVariable Long id) {
        blogRepository.deleteById(id);
        return id;
    }
}
