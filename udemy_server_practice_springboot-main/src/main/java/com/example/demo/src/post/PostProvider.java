package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.GetPostRes;
import com.example.demo.src.post.model.GetPostsRes;
import com.example.demo.src.post.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class PostProvider {

    private final PostDao postDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostProvider(PostDao postDao, JwtService jwtService) {
        this.postDao = postDao;
        this.jwtService = jwtService;
    }


    /** 게시물 리스트 조회 **/
    public List<GetPostsRes> retrievePosts(int userIdxByJwt) throws BaseException {

        if(checkUserExist(userIdxByJwt) == 0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }


        try{

            List<GetPostsRes> getPosts = postDao.selectPosts(userIdxByJwt);

            return getPosts;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 게시물 조회 **/
    public GetPostRes retrievePost(int userIdxByJwt, int postIdx) throws BaseException {

        if(checkUserExist(userIdxByJwt) == 0){
            throw new BaseException(USERS_EMPTY_USER_ID);
        }


        try{

            GetPostRes getPost = postDao.selectPost(userIdxByJwt, postIdx);

            return getPost;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 유저 확인 validation **/
    public int checkUserExist(int userIdx) throws BaseException{
        try{
            return postDao.checkUserExist(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    /** 게시물 확인 validation **/
    public int checkPostExist(int postIdx) throws BaseException{
        try{
            return postDao.checkPostExist(postIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
