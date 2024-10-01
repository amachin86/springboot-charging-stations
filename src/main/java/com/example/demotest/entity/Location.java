package com.example.demotest.entity;

import lombok.*;

import javax.persistence.Embeddable;


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
