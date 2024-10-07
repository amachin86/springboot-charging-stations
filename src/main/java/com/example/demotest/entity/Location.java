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
    private double latitude;
    private double longitude;

}
