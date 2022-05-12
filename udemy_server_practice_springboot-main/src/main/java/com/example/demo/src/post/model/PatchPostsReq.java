package com.example.demo.src.post.model;
/** 게시물 생성 **/
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchPostsReq {
    private int userIdx;
    private String content;
}
