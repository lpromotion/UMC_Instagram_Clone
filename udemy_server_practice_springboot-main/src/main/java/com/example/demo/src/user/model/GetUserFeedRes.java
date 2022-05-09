package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetUserFeedRes {

    private boolean _isMyFeed; // 다른 유저의 피드를 볼 때 화면이 다름을 구분
    private GetUserInfoRes getUserInfo;
    private List<GetUserPostsRes> getUserPosts;

}
