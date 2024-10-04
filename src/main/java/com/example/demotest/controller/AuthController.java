package com.example.demotest.controller;


import com.example.demotest.Auth.JwtHelper;
import com.example.demotest.config.AuthConfig;
import com.example.demotest.dto.APIResponse;
import com.example.demotest.dto.request.JwtRequest;
import com.example.demotest.dto.request.UserRequestDto;
import com.example.demotest.dto.response.JwtResponse;
import com.example.demotest.dto.response.UserResponseDto;
import com.example.demotest.exp.UserAlreadyExistsException;
import com.example.demotest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;


    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<APIResponse> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        try {
            UserResponseDto userResponseDto = userService.createUser(userRequestDto);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userResponseDto.getEmail());
            log.info("from db info");
            log.info(userDetails.getUsername());
            log.info(userDetails.getPassword());

            String token = this.helper.generateToken(userDetails);
            JwtResponse jwtResponse = JwtResponse.builder().token(token).build();

            APIResponse<JwtResponse> responseDTO = APIResponse.<JwtResponse>builder()
                    .status("Success")
                    .results(jwtResponse)
                    .build();


            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
           // return new ResponseEntity<>(jwtResponse, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException ex) {
            // Handle the exception and return an appropriate response
            JwtResponse jwtResponse = new JwtResponse("User already exists: " + ex.getMessage());
            APIResponse<JwtResponse> responseDTO = APIResponse.<JwtResponse>builder()
                    .status("Success")
                    .results(jwtResponse)
                    .build();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDTO);
           // return ResponseEntity.status(HttpStatus.CONFLICT).body(new JwtResponse("User already exists: " + ex.getMessage()));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.helper.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder().token(token).build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        System.out.println("Login Info");
        System.out.println(email);
        System.out.println(password);
        System.out.println("------");
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        System.out.println(authentication.toString()+"  do authenticarte");
        try {
            System.out.println("Started");
            manager.authenticate(authentication);
            System.out.println("Eneded");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("Authentication successful for user: " + email);


        } catch (BadCredentialsException e) {
            System.out.println("Authentication not-successful for user: " + email);
            throw new BadCredentialsException(" Invalid Username or Password  !!");

        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(BadCredentialsException ex) {
        return "Credentials Invalid !!";
    }
}

