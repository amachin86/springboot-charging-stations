package com.example.demotest.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Types;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "charging_station")
public class ChargingStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "org.hibernate.type.UUIDCharType")
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

    @JsonIgnore
    @OneToMany(mappedBy = "chargingStation", cascade = CascadeType.ALL)
    private Set<StationChargerType> stationChargerTypes = new HashSet<>();

    //contable
     private int numberOfChargingPoints;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void setStationChargerTypes(Set<StationChargerType> stationChargerTypes) {
        this.stationChargerTypes = stationChargerTypes;
        for (StationChargerType stationChargerType: stationChargerTypes){
            stationChargerType.setChargingStation(this);
        }
        //update numberOfChargingPoints
       // numberOfChargingPoints = stationChargerTypes.size();
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setChargerType(ChargerType chargerType) {
        this.chargerType = chargerType;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getNumberOfChargingPoints() {
        return this.stationChargerTypes.size();
    }
}




