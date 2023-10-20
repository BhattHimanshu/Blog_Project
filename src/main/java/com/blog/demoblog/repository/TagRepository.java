package com.blog.demoblog.repository;

import com.blog.demoblog.Entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<TagEntity,Integer> {
    boolean existsByName(String data);

    @Query("SELECT t FROM TagEntity t where t.name in :tagsName")
    List<TagEntity> findAllByName(@Param("tagsName") List<String> name);
}
