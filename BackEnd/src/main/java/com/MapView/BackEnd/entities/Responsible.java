package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Table(name = "responsible")
@Entity(name = "responsible")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_responsible")

public class Responsible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_responsible;
    @Column(name = "responsible_name")
    private String responsible;
    private String edv;
    @OneToOne
    @JoinColumn(name = "id_classes")
    private Classes classes;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private Users user;
    @JsonIgnore
    private boolean operative;

    @OneToMany(mappedBy = "responsible")

    private List<EquipmentResponsible> equipmentResponsibles = new ArrayList<>();

    public  Responsible(String responsible_name, String edv,Classes classes,Users users){
        this.responsible = responsible_name;
        this.edv = edv;
        this.classes = classes;
        this.user = users;
        this.operative = true;

    }

    public  Responsible(ResponsibleCrateDTO data, Classes classes, Users users){
        this.responsible = data.responsible();
        this.edv = data.edv();
        this.classes = classes;
        this.user = users;
        this.operative = true;

    }

    public boolean status_check(){
        return this.operative;
    }

}
