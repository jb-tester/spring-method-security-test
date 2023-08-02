package com.mytests.spring.security.methodsecuritytest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig  {

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                new User("user", passwordEncoder().encode("password"), List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_GUEST"))),
                new User("guest", passwordEncoder().encode("password"), List.of(new SimpleGrantedAuthority("ROLE_GUEST"))),
                new User("admin", passwordEncoder().encode("password"), List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_GUEST"))),
                new User("irina", passwordEncoder().encode("password"), List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_GUEST")))
        );
    }
    /*@Bean
    public UserDetailsService userDetailsService()  {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER","GUEST").build());
        manager.createUser(users.username("admin").password("password").roles("ADMIN","GUEST","USER").build());
        manager.createUser(users.username("irina").password("password").roles("ADMIN","GUEST","USER").build());
        manager.createUser(users.username("guest").password("password").roles("GUEST").build());
        return manager;
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}