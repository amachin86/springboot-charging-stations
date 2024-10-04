package com.example.demotest.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String name;
    private String email;
    private String password;
    private String roles;  //ROLE_ADMIN ROLE_USER;


}
