package com.example.springbootjwt;

import com.example.springbootjwt.controllers.UserController;
import com.example.springbootjwt.models.AuthRequest;
import com.example.springbootjwt.models.UserInfo;
import com.example.springbootjwt.services.JwtService;
import com.example.springbootjwt.services.UserInforService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserInforService userInforService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testWelcome() {
        String response = userController.welcome();
        assertEquals("Welcome this endpoint is not secure", response);
    }

    @Test
    public void testAddNewUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Test User");
        userInfo.setEmail("test@gmail.com");
        userInfo.setPassword("password");
        userInfo.setRoles("ROLE_USER");

        doNothing().when(userInforService).addUser(any(UserInfo.class));

        String response = userController.addNewUser(userInfo);
        assertEquals("User added successfully", response);
        verify(userInforService, times(1)).addUser(any(UserInfo.class));
    }

    @Test
    public void testUserProfile() {
        String response = userController.userProfile();
        assertEquals("Welcome user profile", response);
    }

    @Test
    public void testAdminProfile() {
        String response = userController.adminProfile();
        assertEquals("Welcome admin profile", response);
    }

    @Test
    public void testAuthenticateAndGetTokenSuccess() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("test@gmail.com");
        authRequest.setPassword("123");

        when(jwtService.generateToken(anyString())).thenReturn("fake-jwt-token");

        String response = userController.authenticateAndGetToken(authRequest);
        assertEquals("fake-jwt-token", response);
    }

    @Test
    public void testAuthenticateAndGetTokenFailure() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("wrong@gmail.com");
        authRequest.setPassword("wrong");

        assertThrows(UsernameNotFoundException.class, () -> {
            userController.authenticateAndGetToken(authRequest);
        });
    }
}
