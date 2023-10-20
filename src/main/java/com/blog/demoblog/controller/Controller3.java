package com.blog.demoblog.controller;

import com.blog.demoblog.Entity.PostEntity;
import com.blog.demoblog.Entity.TagEntity;
import com.blog.demoblog.service.PostService;
import com.blog.demoblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class Controller3 {
    @Autowired
    PostService ps;

    @Autowired
    TagService ts;

    @RequestMapping("/search")
    public String searchByString(@RequestParam(value = "search", required = false) String search, @PageableDefault(size = 5) Pageable pageable, Model model) {
        Page<PostEntity> page = ps.searchByString(search, pageable);
        model.addAttribute("blogs", page.getContent());
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("search", search); // Pass the search parameter to the view
        return "search-page";
    }


    @RequestMapping("/sort")
    public String viewBlogsSorted(Model model, @PageableDefault(size = 5) Pageable pageable, @RequestParam("sort") String sort) {
        Page<PostEntity> page = ps.getPostsSortedByField(pageable, sort);
        model.addAttribute("blogs", page.getContent());
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        return "viewBlog";
    }




}
