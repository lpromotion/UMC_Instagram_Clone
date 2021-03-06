package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.*;
import com.example.demo.src.post.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;
    @Autowired
    private final JwtService jwtService;



    public PostController(PostProvider postProvider, PostService postService, JwtService jwtService){
        this.postProvider = postProvider;
        this.postService = postService;
        this.jwtService = jwtService;
    }


    /** 게시물 리스트 조회 **/
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPostsRes>> getPosts() {
        try{
            // jwt 토큰에서 userIdx 추출
            int userIdxByJwt = jwtService.getUserIdx();

            List<GetPostsRes> getPostsRes = postProvider.retrievePosts(userIdxByJwt);
            return new BaseResponse<>(getPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** 게시물 조회 **/
    @ResponseBody
    @GetMapping("/{postIdx}")
    public BaseResponse<GetPostRes> getPost(@PathVariable ("postIdx") int postIdx) {
        try{
            // jwt 토큰에서 userIdx 추출
            int userIdxByJwt = jwtService.getUserIdx();

            GetPostRes getPostRes = postProvider.retrievePost(userIdxByJwt, postIdx);
            return new BaseResponse<>(getPostRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** 게시물 생성 **/
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostsRes> createPosts(@RequestBody PostPostsReq postPostsReq ) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            if(postPostsReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }

            // validation
            if(postPostsReq.getContent().length() > 450)
            { // 게시글의 글자수가 많을 때
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_INVALID_CONTENTS);
            }
            if(postPostsReq.getPostImgUrls().size()<1)
            { // 이미지가 선택되지 않았을 때
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_EMPTY_IMGURL);
            }

            PostPostsRes postPostsRes = postService.createPost(postPostsReq.getUserIdx(), postPostsReq);
            return new BaseResponse<>(postPostsRes); // 생성된 게시물의 postIdx

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** 게시물 수정 **/
    @ResponseBody
    @PatchMapping("/{postIdx}")
    public BaseResponse<String> modifyPost(@PathVariable ("postIdx") int postIdx, @RequestBody PatchPostsReq patchPostsReq) {
        try{
            // jwt 토큰 검사
            int userIdxByJwt = jwtService.getUserIdx();
            if(patchPostsReq.getUserIdx() != userIdxByJwt){
                return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
            }

            // validation
            if(patchPostsReq.getContent().length() > 450)
            { // 게시글의 글자수가 많을 때
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_INVALID_CONTENTS);
            }

            postService.modifyPost(patchPostsReq.getUserIdx(), postIdx, patchPostsReq);
            String result = "게시물 정보 수정을 완료하였습니다.";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /** 게시물 삭제 **/
    @ResponseBody
    @PatchMapping("/{postIdx}/status")
    public BaseResponse<String> deletePost(@PathVariable ("postIdx") int postIdx) {
        try{

            postService.deletePost(postIdx);
            String result = "삭제를 성공했습니다.";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
