package com.web.store.config;


import com.web.store.repository.UserRepository;
import com.web.store.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/registration", "/login", "/css/main.css", "/css/*.css"
                        , "/images/*.jpg", "/images/*.png").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll().defaultSuccessUrl("/user-page", true)
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository repository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserDetailsServiceImpl userService = new UserDetailsServiceImpl(repository);
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }

}