package com.sparta.myblog.service;

import com.sparta.myblog.domain.Blog;
import com.sparta.myblog.domain.Comment;
import com.sparta.myblog.dto.BlogRequestDto;
import com.sparta.myblog.repository.BlogRepository;
import com.sparta.myblog.repository.CommentRepository;
import com.sparta.myblog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    public Blog createBlog(BlogRequestDto requestDto) {
        return blogRepository.save(new Blog(requestDto));
    }

    public List<Blog> getBlog() {
        LocalDateTime start = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
        return blogRepository.findAllByModifiedAtBetweenOrderByCreatedAtDesc(start, end);
    }

    public Long deleteBlog(Long id) {
        blogRepository.deleteById(id);
        return id;
    }

    public ModelAndView getOneBlogAndComments(Long id, UserDetailsImpl userDetails) {

        Blog blog = blogRepository.findById(id).orElseThrow(
                () -> new NullPointerException("")
        );
        List<Comment> comment = commentRepository.findAllByBlogIdOrderByCreatedAtDesc(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("post"); // 뷰의 이름
        mv.addObject("data", blog);
        mv.addObject("commentList", comment);
        if (userDetails != null) {
            mv.addObject("user", userDetails.getUser().getNickname());
        } else
            mv.addObject("user", "visitor");
        return mv;
    }

}
