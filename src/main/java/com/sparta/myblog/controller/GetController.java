package com.sparta.myblog.controller;

import com.sparta.myblog.domain.Blog;
import com.sparta.myblog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class GetController {

    private final BlogRepository blogRepository;

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

}
