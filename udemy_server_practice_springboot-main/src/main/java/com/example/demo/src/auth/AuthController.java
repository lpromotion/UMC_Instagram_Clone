package com.example.demo.src.auth;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.auth.*;
import com.example.demo.src.auth.model.PostLoginReq;
import com.example.demo.src.auth.model.PostLoginRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/auth")
public class AuthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AuthProvider authProvider;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final JwtService jwtService;



    public AuthController(AuthProvider authProvider, AuthService authService, JwtService jwtService){
        this.authProvider = authProvider;
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /** 로그인 **/
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
        try{
            if(postLoginReq.getEmail() == null) // 이메일이 비어있을 경우
            {
                return new BaseResponse<>(BaseResponseStatus.POST_USERS_EMPTY_EMAIL);
            }
            if(postLoginReq.getPwd() == null) // 비밀번호가 비어있을 경우
            {
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_EMPTY_PASSWORD);
            }

            if(!isRegexEmail(postLoginReq.getEmail())){ // 이메일 형식 확인
                return new BaseResponse<>(BaseResponseStatus.POST_POSTS_INVALID_PASSWORD);
            }

            PostLoginRes postLoginRes = authService.LogIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);

        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
