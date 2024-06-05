package com.example.springbootjwt.services;

import com.example.springbootjwt.models.UserInfo;
import com.example.springbootjwt.models.UserInforDetails;
import com.example.springbootjwt.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInforService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetails = repository.findByName(username);

        return  userDetails.map(UserInforDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found "+username));

    }

    public String addUser(UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return  "User added successfully";
    }
}
