package com.example.demo.src.auth;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.auth.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class AuthService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final AuthDao authDao;
    private final AuthProvider authProvider;
    private final JwtService jwtService;


    @Autowired
    public AuthService(AuthDao authDao, AuthProvider authProvider, JwtService jwtService) {
        this.authDao = authDao;
        this.authProvider = authProvider;
        this.jwtService = jwtService;

    }

    public PostLoginRes LogIn(PostLoginReq postLoginReq) throws BaseException{
        User user = authDao.getPwd(postLoginReq);
        String encryptPwd;

        try{
            // 암호화 -> SHA256
            encryptPwd = new SHA256().encrypt(postLoginReq.getPwd()); // 암호화
        }
        catch (Exception exception){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        // 암호화 준 비밀번호를 확인
        if(user.getPwd().equals(encryptPwd)){
            // 비교를 해주고, 이상이 없다면 jwt 발급
            int userIdx = user.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLoginRes(userIdx, jwt);
        }
        else
            throw new BaseException(FAILED_TO_LOGIN);
    }
}
