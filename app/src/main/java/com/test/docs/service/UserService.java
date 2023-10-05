package com.test.docs.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.docs.entity.User;
import com.test.docs.repository.UserRepository;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    private PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findById(String userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findById(userId).get();

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), getAuthority());
    }

    public List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
