package com.blog.demoblog.Entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="postee")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "excerpt")
    private String excerpt;


    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "author")
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "published_at")
    private java.util.Date publishedAt;


    @Column(name = "is_published")
    private boolean isPublished;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private java.util.Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at" )
    private java.util.Date updatedAt;

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.DETACH, CascadeType.MERGE,
                    CascadeType.REFRESH})
    @JoinTable(name = "post_tags",
            joinColumns = {@JoinColumn(name = "postId")},
            inverseJoinColumns = {@JoinColumn(name = "tagId")})
    private Set<TagEntity> tags = new HashSet<>();

    private String email;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
        publishedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();

    }

        public Set<TagEntity> getTags() {
            return tags;
        }

    public void setTags(HashSet<TagEntity> tagEntities) {
        this.tags=tagEntities;
    }
}
