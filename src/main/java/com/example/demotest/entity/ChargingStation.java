package com.example.demotest.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "charging_station")
public class ChargingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Embedded
    /*
    So, we can use @AttributeOverrides and @AttributeOverride to override the column properties of our embedded type.
    @Embedded
    @AttributeOverrides({
    @AttributeOverride( name = "address", column = @Column(name = "address")),
    @AttributeOverride( name = "latitude", column = @Column(name = "latitude")),
    @AttributeOverride( name = "longitude", column = @Column(name = "longitude"))
    })

     */
    private Location location;

    @Enumerated(EnumType.STRING)
    private ChargerType chargerType;

    @ManyToOne
    @JoinColumn(name = "charging_station_type_id")
    private StationChargerType stationchargerType;

    //contable
   // private int numberOfChargingPoints;

    @Enumerated(EnumType.STRING)
    private Status status;
}




