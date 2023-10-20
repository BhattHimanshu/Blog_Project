package com.blog.demoblog.service;

import com.blog.demoblog.Entity.PostEntity;
import com.blog.demoblog.repository.PostRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    public void addPost(PostEntity post){
        postRepository.save(post);
    }

    public Page<PostEntity> getAllPost(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public PostEntity getPostById(Integer id){
        return postRepository.getReferenceById(id);
    }

    public void deleteBlogById(Integer blogId) {
        postRepository.deleteById(blogId);
    }

    public List<PostEntity> getBySearchString(String searchString) {
        return postRepository.getBySearchString(searchString);
    }

}
