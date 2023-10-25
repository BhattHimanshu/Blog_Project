package com.blog.demoblog.security;

import com.blog.demoblog.Entity.User;
import com.blog.demoblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User existingUsers = userRepository.findByEmail(email);

        if(existingUsers == null) {
            throw new UsernameNotFoundException("User not found with associated email : " + email);
        }

        return new org.springframework.security.core.userdetails.User(existingUsers.getEmail(), existingUsers.getPassword(), new ArrayList<>());
    }
}
