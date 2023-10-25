package com.blog.demoblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(
                configurer -> configurer
//                        .requestMatchers(HttpMethod.GET,"/").permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers(HttpMethod.GET,"/sort").permitAll()
                        .requestMatchers(HttpMethod.GET,"/filter").permitAll()
                        .requestMatchers(HttpMethod.GET,"/search").permitAll()
                        .requestMatchers(HttpMethod.POST,"/filter").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers(HttpMethod.GET,"/loginCustom").permitAll()
                        .requestMatchers(HttpMethod.POST,"/loginCustom").permitAll()
                        .anyRequest().authenticated()



        ).formLogin(form->form
                .loginPage("/login")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
//                .defaultSuccessUrl("/")
        );

//        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
