package com.example.MyProgect.config;

import com.example.MyProgect.model.Role;
import com.example.MyProgect.service.servicimpl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder builder, @Autowired CustomUserDetailsService customUserDetailsService) throws Exception {

        builder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authorizeRequests()
                .antMatchers("/admin**").hasAuthority(Role.ADMIN.name())
                .antMatchers("/store/", "/store/**").anonymous()
                .antMatchers("/resources/**", "/registration", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout().deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedPage("/store");
    }
}
