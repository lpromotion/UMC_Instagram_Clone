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
}
