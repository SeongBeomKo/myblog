package com.sparta.myblog.controller;

import com.sparta.myblog.domain.Blog;
import com.sparta.myblog.domain.Comment;
import com.sparta.myblog.dto.BlogRequestDto;
import com.sparta.myblog.repository.BlogRepository;
import com.sparta.myblog.repository.CommentRepository;
import com.sparta.myblog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BlogController {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    @PostMapping("/blogs")
    public Blog createBlog(@RequestBody BlogRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Blog blog = new Blog(requestDto);
        blog.setName(userDetails.getUser().getNickname());
        return blogRepository.save(blog);
    }

    @GetMapping("/blogs")
    public List<Blog> getBlog() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        return blogRepository.findAllByModifiedAtBetweenOrderByCreatedAtDesc(start, end);
    }

    @GetMapping("/blogs/detail")
    public ModelAndView getOneBlogAndComments(@RequestParam Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new NullPointerException("")
        );
        List<Comment> comment = commentRepository.findAllByBlogIdOrderByCreatedAtDesc(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("post"); // 뷰의 이름
        mv.addObject("data", blog);
        mv.addObject("commentList", comment);
        if(userDetails != null) {
            mv.addObject("user", userDetails.getUser().getNickname());
        } else
            mv.addObject("user", "visitor");
        return mv;
    }

    @DeleteMapping("/blogs/{id}")
    public Long deleteBlog(@PathVariable Long id) {
        blogRepository.deleteById(id);
        return id;
    }
}
