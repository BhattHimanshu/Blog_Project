package com.blog.demoblog.service;

import com.blog.demoblog.repository.PostTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostTagService {

    @Autowired
    PostTagRepository postTagRepository;


}
