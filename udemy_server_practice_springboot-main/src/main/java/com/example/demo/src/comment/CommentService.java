package com.example.demo.src.comment;

import com.example.demo.config.BaseException;
import com.example.demo.src.comment.*;
import com.example.demo.src.post.PostDao;
import com.example.demo.src.post.PostProvider;
import com.example.demo.src.post.model.*;
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
}
