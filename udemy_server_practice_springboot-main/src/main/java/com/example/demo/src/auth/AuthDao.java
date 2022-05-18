package com.example.demo.src.auth;

import com.example.demo.src.auth.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AuthDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select userIdx, name, nickname, email, pwd from User where email=?";
        String getPwdParam = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery, // 리스트면 query, 리스트가 아니면 queryForObject
                (rs,rowNum) -> new User(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email"),
                        rs.getString("pwd")
                ), getPwdParam);
    }
}
