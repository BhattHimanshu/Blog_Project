package com.blog.demoblog.service;

import com.blog.demoblog.Entity.CommentEntity;
import com.blog.demoblog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void addComment(CommentEntity commentEntity){
        commentRepository.save(commentEntity);
    }

    public List<CommentEntity> getAllComment(){
        return commentRepository.findAll();
    }

    public void delete(Long killingId){
        Integer intBlogId = killingId.intValue();
        commentRepository.deleteById(intBlogId);

    }
}


