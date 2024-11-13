package com.MapView.BackEnd.entities;

import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleCreateDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleCrateDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;


@Table(name = "equipment_responsible", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_equipment", "id_responsible"})})
@Entity(name = "equipment_responsible")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id_equip_resp")
public class EquipmentResponsible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_equip_resp;

    @ManyToOne()

    @JoinColumn(name = "id_equipment", nullable = false)
    @JsonIgnoreProperties({"equipmentResponsibles", "otherPropertiesToIgnore"}) // Ignora o relacionamento com "equipmentResponsibles" em Responsible
    @JsonIgnore
    private Equipment equipment;

    @ManyToOne()

    @JoinColumn(name = "id_responsible", nullable = false )
    @JsonIgnoreProperties({"equipmentResponsibles", "otherPropertiesToIgnore"}) // Ignora o relacionamento com "equipmentResponsibles" em Responsible

    private Responsible responsible;

    private LocalDate start_usage;
    private LocalDate end_usage;

    private boolean operative;


    public EquipmentResponsible(EquipmentResponsibleCreateDTO equipmentResponsibleCreateDTO, Equipment equipment,
                                Responsible responsible) {
        this.equipment = equipment;
        this.responsible = responsible;
        this.start_usage = equipmentResponsibleCreateDTO.start_usage();
        this.end_usage = null;
        this.operative = true;
    }

    public EquipmentResponsible(  Responsible responsible, Equipment equipment) {
        this.equipment = equipment;
        this.responsible = responsible;
        this.start_usage = LocalDate.now();
        this.end_usage = null;
        this.operative = true;
    }


}
