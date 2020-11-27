package com.example.MyProgect.service.servicimpl;

import com.example.MyProgect.model.CustomUserDetails;
import com.example.MyProgect.model.User;
import com.example.MyProgect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("No user with email " + email);
        }
        else {
            return new CustomUserDetails(user);
        }
    }
}
