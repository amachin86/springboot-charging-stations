package com.example.demotest.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String name;
    private String email;
    //    private String password;
    private String roles;;
    private long  id;
}
