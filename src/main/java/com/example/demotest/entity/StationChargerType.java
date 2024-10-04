package com.example.demotest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "charging_station_type")
public class StationChargerType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double power_levels;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fk_station_id")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private ChargingStation chargingStation;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        return "StationChargerType{" +
                "id=" + id +
                ", power_levels=" + power_levels +
                ", chargingStation=" + chargingStation +
                ", status=" + status +
                '}';
    }
}
