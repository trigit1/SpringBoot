package com.example.springbootjwt.controllers;

import com.example.springbootjwt.models.AuthRequest;
import com.example.springbootjwt.models.UserInfo;
import com.example.springbootjwt.services.JwtService;
import com.example.springbootjwt.services.UserInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInforService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo){
        service.addUser(userInfo);
        return "User added successfully";
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome user profile";
    }

    @GetMapping("/admin/userProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome admin profile";
    }


    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        //Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));
        String email = authRequest.getUsername();
        String password= authRequest.getPassword();
        if((email.equals("test@gmail.com")) && (password.equals("123"))){
            return  jwtService.generateToken(authRequest.getUsername());
        }else{
            throw new UsernameNotFoundException("invalid user request");
        }
    }
}
