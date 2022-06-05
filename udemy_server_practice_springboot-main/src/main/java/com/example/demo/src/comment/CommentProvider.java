package com.example.demo.src.comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.comment.model.*;
import com.example.demo.src.comment.*;
import com.example.demo.src.post.PostDao;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CommentProvider {
    private final CommentDao commentDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CommentProvider(CommentDao commentDao, JwtService jwtService) {
        this.commentDao = commentDao;
        this.jwtService = jwtService;
    }

    /** 유저 확인 validation **/
    public int checkUserExist(int userIdx) throws BaseException{
        try{
            return commentDao.checkUserExist(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 댓글 확인 validation **/
    public int checkCommentExist(int commentIdx) throws BaseException{
        try{
            return commentDao.checkCommentExist(commentIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
