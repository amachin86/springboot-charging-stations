package com.example.demotest.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.validation.constraints.Positive;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Location {

    private String address;
    @Positive(message = "The value of the latitude must be positive")
    private double latitude;
    @Positive(message = "The value of the latitude must be positive")
    private double longitude;

}
