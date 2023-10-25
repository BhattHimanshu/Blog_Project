package com.blog.demoblog.controller;

import com.blog.demoblog.Entity.*;
import com.blog.demoblog.repository.PostRepository;
import com.blog.demoblog.repository.TagRepository;
import com.blog.demoblog.repository.UserRepository;
import com.blog.demoblog.service.CommentService;
import com.blog.demoblog.service.PostService;
import com.blog.demoblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

//    @GetMapping("/")
//    public String viewBlogs(Model model) {
//        List<PostEntity> blogs = ps.getAllPost();
//        model.addAttribute("blogs", blogs);
//
//        return "viewBlog";
//    }

    @GetMapping("/")
    public String viewBlogWithPagination(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "sort", defaultValue = "asc") String sort,
            Model model) {

        int pageSize = 10;

        // Determine the sorting field based on the sort parameter
        String sortingField = "publishedAt";

        if (sort.equals("desc")) {
            sortingField = "publishedAt"; // Change this to the desired sorting field
        }

        Page<PostEntity> blogs = ps.findAllWithPaginationAndSorting(page, pageSize, sortingField, sort);
        model.addAttribute("blogs", blogs);
        model.addAttribute("sort", sort); // Pass the sorting direction to the template
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
        postEntity.setCreatedAt(postEntity.getCreatedAt());
        postEntity.setPublishedAt(postEntity.getPublishedAt());
        ps.addPost(postEntity);
        return "redirect:/";
    }


    @RequestMapping("/newBlog")
    public String newBlog() {
        return "newBlog";
    }


    @RequestMapping("/blogLink/{blogId}")
    public String update(@PathVariable long blogId, Model model , Model m2) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User author = userRepository.findByName(username);

        String author1 = postRepository.findById((int)blogId).get().getAuthor();

        System.out.println("Logged in user: " + username + " -> author: " + author1);

        model.addAttribute("logged_in_user",username);
        model.addAttribute("post_author",author1);

        model.addAttribute("authorName", author);

//        System.out.println("Logged in user: " + username + " -> author" + author.getEmail());

        Integer intBlogId = Integer.parseInt(""+blogId);
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
