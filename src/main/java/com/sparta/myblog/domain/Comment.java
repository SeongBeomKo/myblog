package com.sparta.myblog.domain;

import com.sparta.myblog.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Setter
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String contents;
    @Column(nullable = false)
    private Long blogId;

    public Comment(CommentRequestDto requestDto) {
        contents = requestDto.getContents();
        blogId = requestDto.getBlogId();
    }
}
