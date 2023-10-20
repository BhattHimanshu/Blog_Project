package com.blog.demoblog.controller;

import com.blog.demoblog.Entity.PostEntity;
import com.blog.demoblog.Entity.TagEntity;
import com.blog.demoblog.service.PostService;
import com.blog.demoblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class Controller3 {
    @Autowired
    PostService ps;

    @Autowired
    TagService ts;

    @RequestMapping("/search")
    public String searchByString(@RequestParam("search") String search, Model model) {
        Set<Integer> postIds = ps.findPostIdsByTagName(search);
        List<PostEntity> posts = ps.getBySearchString(search, postIds);
        List<TagEntity> tags = ts.findAll();
        List<PostEntity> postEntityList=ps.getAllPost();
        List<String> authors = ps.findAllAuthors(postEntityList); // returning list of string of all authors

        model.addAttribute("blogs", posts);
        model.addAttribute("users", authors);
        model.addAttribute("tags", tags);
        return "search-page";
    }
}
