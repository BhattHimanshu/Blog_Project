package com.blog.demoblog.controller;

import com.blog.demoblog.Entity.CommentEntity;
import com.blog.demoblog.repository.CommentRepository;
import com.blog.demoblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ControllerTwo {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/commentAdd")
    public String commentAdd( @ModelAttribute CommentEntity commentEntity) {

        commentService.addComment(commentEntity);
        Long blogId = commentEntity.getPost_id();
        return "redirect:/blogLink/" + blogId;
    }

    @RequestMapping(value = "/deleteComment/{id}/{blogId}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long CId, @PathVariable("blogId") Long blogId) {
        commentService.delete(CId);
        return "redirect:/blogLink/" + blogId;
    }


}
