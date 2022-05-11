package com.example.demo.src.post.model;
/** 사진 반환하는 객체 **/
import com.example.demo.src.user.model.GetUserInfoRes;
import com.example.demo.src.user.model.GetUserPostsRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPostImgRes {

    private int postImgIdx;
    private String imgUrl;


}
