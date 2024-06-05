package com.example.springbootjwt.models;


import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    String username;
    String password;
}
