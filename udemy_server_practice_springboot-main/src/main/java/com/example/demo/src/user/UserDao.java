package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /** 유저 피드 조회 - 유저 정보 **/
    public GetUserInfoRes selectUserInfo(int userIdx){
        String selectUsersInfoQuery =
                "SELECT u.nickName as nickName,\n" +
                "       u.name as name,\n" +
                "       u.profileImgUrl as profileImgUrl,\n" +
                "       u.website as website,\n" +
                "       u.introduce as introduction,\n" +
                "       If(followerCount is null, 0, followerCount) as followerCount,\n" +
                "       If(followingCount is null, 0, followingCount) as followingCount,\n" +
                "       If(postCount is null, 0, postCount) as postCount\n" +
                "FROM User as u\n" +
                "    left join(select userIdx, count(postIdx) as postCount from Post where status = 'ACTIVE' group by userIdx)\n" +
                "        p on p.userIdx - u.userIdx\n" +
                "    left join(select followerIdx, count(followIdx) as followerCount from Follow where status = 'ACTIVE' group by followerIdx)\n" +
                "        fc on fc.followerIdx = u.userIdx\n" +
                "    left join(select followingIdx, count(followIdx) as followingCount from Follow where status = 'ACTIVE' group by followingIdx)\n" +
                "        f on f.followingIdx = u.userIdx\n" +
                "WHERE u.userIdx = ? and u.status = 'ACTIVE'";
        int selectUserInfoParam = userIdx;
        return this.jdbcTemplate.queryForObject(selectUsersInfoQuery, // 리스트면 query, 리스트가 아니면 queryForObject
                (rs,rowNum) -> new GetUserInfoRes(
                        rs.getString("nickName"),
                        rs.getString("name"),
                        rs.getString("profileImgUrl"),
                        rs.getString("website"),
                        rs.getString("introduction"),
                        rs.getInt("followerCount"),
                        rs.getInt("followingCount"),
                        rs.getInt("postCount")
                ), selectUserInfoParam);
    }

    /** 유저 피드 조회 - 유저 게시물 **/
    public List<GetUserPostsRes> selectUserPosts(int userIdx){
        String selectUserPostsQuery =
                "SELECT p.postIdx as postIdx,\n" +
                "       pi.ImgUrl as postImgUrl\n" +
                "FROM Post as p\n" +
                "    join PostImgUrl as pi on pi.postIdx = p.postIdx and pi.status = 'ACTIVE'\n" +
                "    join User as u on u.userIdx = p.userIdx\n" +
                "WHERE p.status = 'ACTIVE' and u.userIdx = ?\n" +
                "GROUP BY p.postIdx\n" +
                "HAVING min(pi.postImgUrlIdx)\n" +
                "ORDER BY p.postIdx;";
        int selectUserPostsParam = userIdx;
        return this.jdbcTemplate.query(selectUserPostsQuery, // 리스트면 query, 리스트가 아니면 queryForObject
                (rs,rowNum) -> new GetUserPostsRes(
                        rs.getInt("postIdx"),
                        rs.getString("postImgUrl")
                ), selectUserPostsParam);
    }

    public GetUserRes getUsersByEmail(String email){
        String getUsersByEmailQuery = "select userIdx,name,nickName,email from User where email=?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.queryForObject(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email")),
                getUsersByEmailParams);
    }


    public GetUserRes getUsersByIdx(int userIdx){
        String getUsersByIdxQuery = "select userIdx,name,nickName,email from User where userIdx=?";
        int getUsersByIdxParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUsersByIdxQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("name"),
                        rs.getString("nickName"),
                        rs.getString("email")),
                getUsersByIdxParams);
    }

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into User (email, name, pwd, birth, nickname) VALUES (?,?,?,?,?)";
        Object[] createUserParams = new Object[]{
                postUserReq.getEmail(),
                postUserReq.getName(),
                postUserReq.getPwd(),
                postUserReq.getBirth(),
                postUserReq.getNickname()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from User where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    /** 유저가 존재하는지 validation **/
    public int checkUserExist(int userIdx){
        String checkUserExistQuery = "select exists(select userIdx from User where userIdx = ?)";
        int checkUserExistParams = userIdx;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);

    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update User set nickName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getNickName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }




}
