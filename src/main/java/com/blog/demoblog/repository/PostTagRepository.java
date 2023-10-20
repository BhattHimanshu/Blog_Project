package com.blog.demoblog.repository;

import com.blog.demoblog.Entity.Post_Tag;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTagRepository extends JpaRepository<Post_Tag, Integer> {
}
