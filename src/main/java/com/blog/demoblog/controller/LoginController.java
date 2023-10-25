package com.blog.demoblog.controller;

import com.blog.demoblog.Entity.User;
import com.blog.demoblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String login(){
        return "Login";
    }


    @GetMapping("/register")
    public String register(){
        return "Register";
    }

    @PostMapping("/register")
    public void afterRegister(@RequestParam("name") String name , @RequestParam("email") String email ,@RequestParam("password") String password){
        User user=new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles("AUTHOR");

        userRepository.save(user);

    }
}
