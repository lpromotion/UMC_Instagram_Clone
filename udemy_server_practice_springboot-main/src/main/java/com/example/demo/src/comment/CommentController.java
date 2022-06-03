package com.example.demo.src.comment;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CommentProvider commentProvider;
    @Autowired
    private final CommentService commentService;
    @Autowired
    private final JwtService jwtService;

    public CommentController(CommentProvider commentProvider, CommentService commentService, JwtService jwtService){
        this.commentProvider = commentProvider;
        this.commentService = commentService;
        this.jwtService = jwtService;
    }
}
