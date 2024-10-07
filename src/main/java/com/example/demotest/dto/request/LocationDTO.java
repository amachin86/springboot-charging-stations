package com.example.demotest.dto.request;

import lombok.*;
import org.springframework.validation.annotation.Validated;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
public class LocationDTO {
    @NotBlank(message = "The value of the address shouldn't be NULL or EMPTY")
    private String address;
    private double latitude;
    private double longitude;

}
