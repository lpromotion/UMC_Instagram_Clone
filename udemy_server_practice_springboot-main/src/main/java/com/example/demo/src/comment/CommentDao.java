package com.example.demo.src.comment;

import com.example.demo.src.comment.*;
import com.example.demo.src.comment.model.*;
import com.example.demo.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CommentDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /** 댓글 생성 **/
    public int createComment(int userIdxByJwt, PostCommentReq postCommentReq){
        String createCommentQuery = "insert into Comment (userIdx, postIdx, parrentCommentIdx, content) VALUES (?,?,?,?)";
        Object[] createCommentParams = new Object[]{
                userIdxByJwt,
                postCommentReq.getPostIdx(),
                postCommentReq.getParrentCommentIdx(),
                postCommentReq.getContent()};
        this.jdbcTemplate.update(createCommentQuery, createCommentParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    /** 유저 확인 validation **/
    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }

    /** 게시물 확인 validation **/
    public int checkCommentExist(int commentIdx){
        String checkPostExistQuery = "select exists(select commentIdx from Comment where commentIdx = ?)";
        int checkCommentExistParams = commentIdx;
        return this.jdbcTemplate.queryForObject(checkPostExistQuery,
                int.class,
                checkCommentExistParams);

    }

    /** 댓글 수정 **/
    public int updateComment(int commentIdx, String content){
        String updateCommentQuery = "UPDATE Comment SET content=? WHERE commentIdx=?";
        Object[] updateCommentParams = new Object[] {content, commentIdx};
        return this.jdbcTemplate.update(updateCommentQuery, updateCommentParams);

    }

    /** 댓글 삭제 **/
    public int deleteComment(int commentIdx){
        String deleteCommentQuery = "UPDATE Comment SET status='INACTIVE' WHERE commentIdx=?";
        Object[] deleteCommentParams = new Object[] {commentIdx};
        return this.jdbcTemplate.update(deleteCommentQuery, deleteCommentParams);

    }

    /** 댓글 좋아요 생성 **/
    public int createCommentLike(int userIdxByJwt, int commentIdx){
        String createCommentQuery = "insert into CommentLike (userIdx, commentIdx) VALUES (?,?)";
        Object[] createCommentParams = new Object[]{
                userIdxByJwt,
                commentIdx};
        this.jdbcTemplate.update(createCommentQuery, createCommentParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    /** 댓글 좋아요 취소 **/
    public int deleteCommentLike(int commentLikeIdx){
        String deleteCommentLikeQuery = "UPDATE CommentLike SET status='INACTIVE' WHERE commentLikeIdx=?";
        Object[] deleteCommentLikeParams = new Object[] {commentLikeIdx};
        return this.jdbcTemplate.update(deleteCommentLikeQuery, deleteCommentLikeParams);

    }
}
