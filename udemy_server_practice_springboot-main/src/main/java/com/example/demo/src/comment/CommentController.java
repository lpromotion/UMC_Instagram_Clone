package com.example.demo.src.comment;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.comment.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CommentProvider commentProvider;
    @Autowired
    private final CommentService commentService;
    @Autowired
    private final JwtService jwtService;

    public CommentController(CommentProvider commentProvider, CommentService commentService, JwtService jwtService){
        this.commentProvider = commentProvider;
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    /** 댓글 생성 **/
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCommentRes> createPosts(@RequestBody PostCommentReq postCommentReq ) {
        try{
            int userIdxByJwt = jwtService.getUserIdx();

            // validation
            if(postCommentReq.getContent().length() > 200)
            { // 댓글의 글자수가 많을 때
                return new BaseResponse<>(BaseResponseStatus.POST_COMMENTS_INVALID_CONTENTS);
            }
            if(postCommentReq.getContent().length() < 1)
            { // 댓글을 쓰지 않았을 때
                return new BaseResponse<>(BaseResponseStatus.POST_COMMENTS_EMPTY_CONTENTS);
            }

            PostCommentRes postCommentRes = commentService.createComment(userIdxByJwt, postCommentReq);
            return new BaseResponse<>(postCommentRes); // 생성된 게시물의 postIdx

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
