package com.example.demo.src.comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.comment.*;
import com.example.demo.src.comment.model.*;
import com.example.demo.src.post.model.PatchPostsReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CommentService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CommentDao commentDao;
    private final CommentProvider commentProvider;
    private final JwtService jwtService;


    @Autowired
    public CommentService(CommentDao commentDao, CommentProvider commentProvider, JwtService jwtService) {
        this.commentDao = commentDao;
        this.commentProvider = commentProvider;
        this.jwtService = jwtService;
    }

    /** 댓글 생성 **/
    public PostCommentRes createComment(int userIdxByJwt, PostCommentReq postCommentReq) throws BaseException {

        try{

            int commentIdx = commentDao.createComment(userIdxByJwt, postCommentReq); // commentIdx를 받아줌

            return new PostCommentRes(commentIdx);
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 댓글 수정 **/
    public void modifyComment(int userIdx, int commentIdx, PatchCommentReq patchCommentReq) throws BaseException {
        // validation
        if(commentProvider.checkUserExist(userIdx)==0) // 유저가 존재하는지
        {
            throw new BaseException(USERS_EMPTY_USER_ID);
        }
        if(commentProvider.checkCommentExist(commentIdx)==0) // 댓글이 존재하는지
        {
            throw new BaseException(COMMENTS_EMPTY_COMMENT_ID);
        }

        try{

            int result = commentDao.updateComment(commentIdx, patchCommentReq.getContent()); // commentIdx를 받아줌

            if(result == 0){
                throw new BaseException(MODIFY_FAIL_COMMENT);
            }

            // void 함수여서 리턴값 필요없음
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 댓글 삭제 **/
    public void deleteComment(int commentIdx) throws BaseException {

        try{

            int result = commentDao.deleteComment(commentIdx); // commentIdx를 받아줌

            if(result == 0){
                throw new BaseException(DELETE_FAIL_COMMENT);
            }

            // void 함수여서 리턴값 필요없음
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 댓글 좋아요 생성 **/
    public PostCommentLikeRes createCommentLike(int userIdxByJwt, int commentIdx) throws BaseException {

        try{

            int commentLikeIdx = commentDao.createCommentLike(userIdxByJwt, commentIdx); // commentLikeIdx를 받아줌

            return new PostCommentLikeRes(commentLikeIdx);
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
