package com.example.demotest.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserRequestDto {

    private String name;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", message = "email field is not valid")
    private String email;

    @NotBlank(message = "password shouldn't be NULL or EMPTY")
    private String password;
    @NotBlank(message = "roles shouldn't be NULL or EMPTY")
    private String roles;  //ROLE_ADMIN ROLE_USER;


}
