package com.blog.demoblog.repository;

import com.blog.demoblog.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<PostEntity,Integer> {


    @Query(value = "select post_tags.post_id from post_tags inner join tags on" +
            " tags.id = post_tags.tag_id where tags.name like :searchString",
            nativeQuery = true)
    Set<Integer> findAllPostIdByTagName(@Param("searchString") String searchString);


    @Query("select p from PostEntity as p where p.id in :postIds or lower(p.title) like lower(CONCAT('%',:searchString,'%')) or " +
            "lower(p.content) like lower(CONCAT('%',:searchString,'%')) or lower(p.author) like lower(CONCAT('%',:searchString,'%')) or " +
            "lower(p.excerpt) like lower(CONCAT('%',:searchString,'%'))")
    List<PostEntity> getBySearchString(@Param("searchString") String searchString, @Param("postIds") Set<Integer> postIds);

}
