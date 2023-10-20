package com.blog.demoblog.Entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Post_Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    public Post_Tag() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
    public Post_Tag(PostEntity post, TagEntity tag) {
        this.post = post;
        this.tag = tag;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

}
