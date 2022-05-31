package com.example.demo.src.post.model;
/** 게시글을 반환하는 객체 **/
import com.example.demo.src.user.model.GetUserInfoRes;
import com.example.demo.src.user.model.GetUserPostsRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPostsRes {

    private int postIdx;
    private int userIdx;
    private String nickName;
    private String profileImgUrl;
    private String content;
    private int postLikeCount;
    private int commentCount;
    private String updatedAt;
    private String likeOrNot; // 본인이 글을 좋아요 했는지 아닌지
    private List<GetPostImgRes> imgs;

}
