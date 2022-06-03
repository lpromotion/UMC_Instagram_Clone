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
}
