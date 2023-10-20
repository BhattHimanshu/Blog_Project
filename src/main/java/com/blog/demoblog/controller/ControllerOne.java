package com.blog.demoblog.controller;

import com.blog.demoblog.Entity.CommentEntity;
import com.blog.demoblog.Entity.PostEntity;
import com.blog.demoblog.Entity.Post_Tag;
import com.blog.demoblog.Entity.TagEntity;
import com.blog.demoblog.repository.TagRepository;
import com.blog.demoblog.service.CommentService;
import com.blog.demoblog.service.PostService;
import com.blog.demoblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ControllerOne {

    @Autowired
    PostService ps;
    @Autowired
    CommentService cs;
    @Autowired
    TagService ts;
    @Autowired
    TagRepository tagrepo;

    @GetMapping("/")
    public String viewBlogs(Model model) {
        List<PostEntity> list = ps.getAllPost();
        model.addAttribute("blogs", list);
        return "viewBlog";
    }

    @RequestMapping("/addBlog")
    public String publishPost(@ModelAttribute("blogPost") PostEntity post,@RequestParam("tagsHtml") String tagFromHtml) {
        List<TagEntity> tags = ts.findTagIds(tagFromHtml);
        post.setTags(new HashSet<>(tags));
        ps.addPost(post);
        return "redirect:/";
    }

    @RequestMapping(value = "/editAddBlog/{blogId}")
    public String editaddBlog(@ModelAttribute PostEntity postEntity,@PathVariable Long blogId,@RequestParam("tagsHtml") String tagFromHtml) {

        List<TagEntity> tagList = ts.findTagIds(tagFromHtml);
        postEntity.setTags(new HashSet<>(tagList));
        postEntity.setId(blogId);
        ps.addPost(postEntity);
        return "redirect:/";
    }


    @RequestMapping("/newBlog")
    public String newBlog() {
        return "newBlog";
    }



    @RequestMapping("/blogLink/{blogId}")
    public String update(@PathVariable Long blogId, Model model , Model m2) {
        Integer intBlogId = blogId.intValue();
        PostEntity postEntity = ps.getPostById(intBlogId);
        model.addAttribute("blog", postEntity);

        List<CommentEntity> list1 = cs.getAllComment();
        List<CommentEntity> list2 = new ArrayList<>();

        for(CommentEntity ce : list1) {
            if(Objects.equals(ce.getPost_id(), blogId)){
                list2.add(ce);
            }
        }
        m2.addAttribute("commentsList",list2);
        return "update-page";
    }

   @RequestMapping("/editBlog/{blogId}")
    public String edit(@PathVariable Long blogId, Model model){
       Integer intBlogId = blogId.intValue();
       PostEntity postEntity = ps.getPostById(intBlogId);
       model.addAttribute("blog", postEntity);

       // Get the tags associated with the blog post
       Set<TagEntity> tags = postEntity.getTags();

       // Convert the set of tags into a comma-separated string
       String tagsString = tags.stream()
               .map(TagEntity::getName)
               .collect(Collectors.joining(", "));


       model.addAttribute("tags", tagsString);
       return "editBlog";
   }
   @RequestMapping("/delete/{blogId}")
   public String delete(@PathVariable Long blogId, Model model){
       Integer intBlogId = blogId.intValue();
       ps.deleteBlogById(intBlogId);
       return "redirect:/";
   }
}
