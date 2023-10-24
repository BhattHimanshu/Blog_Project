package com.blog.demoblog.repository;

import com.blog.demoblog.Entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<PostEntity,Integer> {


    @Query("SELECT p FROM PostEntity p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "EXISTS (SELECT t FROM p.tags t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<PostEntity> getBySearchString(@Param("search") String search);

    @Query("SELECT p FROM PostEntity p WHERE " +
            "LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.content) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.author) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "EXISTS (SELECT t FROM p.tags t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<PostEntity> getBySearchString(@Param("search") String search, Pageable pageable);


    List<PostEntity> findByAuthorIn(List<String> authors);

    List<PostEntity> findByTagsIdIn(List<Integer> tagIds);

    List<PostEntity> findByAuthorInAndTagsIdIn(List<String> authors, List<Integer> tagIds);

}
