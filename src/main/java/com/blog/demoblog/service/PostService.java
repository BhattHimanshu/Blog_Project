package com.blog.demoblog.service;

import com.blog.demoblog.Entity.PostEntity;
import com.blog.demoblog.repository.PostRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<PostEntity> getAllPost() {
        return postRepository.findAll();
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
    public Page<PostEntity> searchByString(String search, Pageable pageable) {
        return postRepository.getBySearchString(search, pageable);
    }


    public Page<PostEntity> findPostsWithSortingASC(int page, int pageSize, String field, String sort){
        Sort sortObject = Sort.by(sort.equalsIgnoreCase("asc") ? Sort.Order.asc(field) : Sort.Order.desc(field));
        PageRequest pageRequest = PageRequest.of(page, pageSize, sortObject);
        // Retrieve the paginated and sorted data from the repository
        return postRepository.findAll(pageRequest);
    }
    public Page<PostEntity> findPostsWithSortingDSC(int page, int pageSize, String field, String sort){
        Sort sortObject = Sort.by(sort.equalsIgnoreCase("asc") ? Sort.Order.asc(field) : Sort.Order.desc(field));
        PageRequest pageRequest = PageRequest.of(page, pageSize, sortObject);

        // Retrieve the paginated and sorted data from the repository
        return postRepository.findAll(pageRequest);
    }

    public Page<PostEntity> findAllWithPagination(int offset ,int pageSize){
        return postRepository.findAll(PageRequest.of(offset,pageSize));
    }

    public Page<PostEntity> findAllWithPaginationAndSorting(int page, int pageSize, String sortingField, String sort) {
        // Create a Sort object based on the sortingField and sort direction
        Sort sortObject = Sort.by(sort.equalsIgnoreCase("asc") ? Sort.Order.asc(sortingField) : Sort.Order.desc(sortingField));

        // Create a PageRequest object for pagination and sorting
        PageRequest pageRequest = PageRequest.of(page, pageSize, sortObject);

        // Retrieve the paginated and sorted data from the repository
        return postRepository.findAll(pageRequest);
    }

    public List<PostEntity> filterPosts(List<String> selectedAuthors, List<Integer> selectedTags) {
        if (selectedAuthors == null && selectedTags == null) {
            return postRepository.findAll();
        } else if (selectedAuthors != null && selectedTags == null) {
            return postRepository.findByAuthorIn(selectedAuthors);
        } else if (selectedAuthors == null && selectedTags != null) {
            return postRepository.findByTagsIdIn(selectedTags);
        } else {
            return postRepository.findByAuthorInAndTagsIdIn(selectedAuthors, selectedTags);
        }
    }


}

