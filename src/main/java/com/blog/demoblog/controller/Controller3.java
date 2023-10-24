package com.blog.demoblog.controller;

import com.blog.demoblog.Entity.PostEntity;
import com.blog.demoblog.Entity.TagEntity;
import com.blog.demoblog.repository.PostRepository;
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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class Controller3 {
    @Autowired
    PostService ps;

    @Autowired
    TagService ts;

    @Autowired
    PostRepository postRepository;
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
    public String GetPostsWithSorting(Model model ,@RequestParam(name = "page", defaultValue = "0") int page,
                                      @RequestParam(name = "sort", defaultValue = "asc") String direction){

        int pageSize = 10;
        if(direction.equals("asc")) {
            Page<PostEntity> sortedList = ps.findPostsWithSortingASC(page,pageSize,"publishedAt",direction);
            model.addAttribute("blogs",sortedList);
            model.addAttribute("sort",direction);
        }
        else {
            Page<PostEntity> sortedList = ps.findPostsWithSortingDSC(page,pageSize,"publishedAt",direction);
            model.addAttribute("blogs",sortedList);
            model.addAttribute("sort",direction);
        }

        return "viewBlog";
    }


    @GetMapping("/filter")
    public String filter(Model model){
        List<PostEntity>postEntityList=postRepository.findAll();
        Set<String> set = new HashSet<>();

        for(PostEntity postEntity:postEntityList){
            set.add(postEntity.getAuthor());
        }

        List<String> filterList = new ArrayList<>(set);

        model.addAttribute("posts",filterList);

        List<TagEntity> tags = ts.findAll();

        model.addAttribute("tags",tags);


        return "filterpage";
    }

    @PostMapping("/filter")
    public String filterResults(@RequestParam(value = "selectedAuthors", required = false) List<String> selectedAuthors,
                                @RequestParam(value = "selectedTags", required = false) List<Integer> selectedTags,
                                Model model) {
        // Use the selectedAuthors and selectedTags lists to filter your results
        List<PostEntity> blogs = ps.filterPosts(selectedAuthors, selectedTags);

        // Populate your model with the filtered results
        model.addAttribute("blogs", blogs);

        return "filtered_results"; // Return a page to display filtered results
    }
}
