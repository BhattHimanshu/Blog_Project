package com.blog.demoblog.service;

import com.blog.demoblog.Entity.PostEntity;
import com.blog.demoblog.repository.PostRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<PostEntity> getAllPost(){
        return postRepository.findAll();
    }

    public PostEntity getPostById(Integer id){
        return postRepository.getReferenceById(id);
    }

    public void deleteBlogById(Integer blogId) {
        // Use the deleteById method from the PostRepository to delete the blog by its ID

        postRepository.deleteById(blogId);
    }

    public Set<Integer> findPostIdsByTagName(String searchString) {
        return postRepository.findAllPostIdByTagName(searchString);
    }

    public List<PostEntity> getBySearchString(String searchString, Set<Integer> postIds) {
        return postRepository.getBySearchString(searchString, postIds);
    }
    public List<String> findAllAuthors(List<PostEntity> lis) {
        List<String> str = new ArrayList<>();
        for(PostEntity pe : lis){
            str.add(pe.getAuthor());
        }
        return str;
    }
}
