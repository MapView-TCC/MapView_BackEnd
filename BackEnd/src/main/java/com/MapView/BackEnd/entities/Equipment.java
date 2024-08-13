package com.MapView.BackEnd.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "equipment")
@Entity(name = "equipment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_equipment")
public class Equipment {

    @Id
    private String id_equipment;
    private String rfid;
    private String type;
    private String model;
    private String validity; // sera?
    private String admin_rights;
    private String observation;
    private Location id_location;
    private MainOwner id_owner;
    private boolean operative;
}
