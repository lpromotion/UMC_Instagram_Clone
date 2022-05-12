package com.example.demo.src.post.model;
/** 게시글을 반환하는 객체 **/
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostImgUrlsReq {
    private String imgUrl;

}
