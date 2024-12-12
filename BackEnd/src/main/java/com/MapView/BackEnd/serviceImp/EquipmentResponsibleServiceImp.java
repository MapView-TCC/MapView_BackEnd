package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleSearchDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.infra.Exception.OperativeFalseException;
import com.MapView.BackEnd.infra.Exception.OperativeTrueException;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.EquipmentResponsibleRepository;
import com.MapView.BackEnd.repository.ResponsibleRepository;
import com.MapView.BackEnd.service.EquipmentResponsibleService;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleCreateDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleUpdateDTO;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class  EquipmentResponsibleServiceImp implements EquipmentResponsibleService {

    private final EntityManager entityManager;

    private final EquipmentResponsibleRepository equipmentResponsibleRepository;


    private final EquipmentRepository equipmentRepository;


    private  final  ResponsibleRepository responsibleRepository;

    public EquipmentResponsibleServiceImp(EntityManager entityManager, EquipmentResponsibleRepository equipmentResponsibleRepository, EquipmentRepository equipmentRepository, ResponsibleRepository responsibleRepository) {
        this.entityManager = entityManager;
        this.equipmentResponsibleRepository = equipmentResponsibleRepository;
        this.equipmentRepository = equipmentRepository;
        this.responsibleRepository = responsibleRepository;
    }

    @Override
    public EquipmentResponsibleDetailsDTO getEquipmentResponsible(Long id_equip_resp) {
        EquipmentResponsible equipmentResponsible = this.equipmentResponsibleRepository.findById(id_equip_resp)
                .orElseThrow(() -> new NotFoundException("Equipment Responsible with ID " + id_equip_resp + " not found."));

        return new EquipmentResponsibleDetailsDTO(equipmentResponsible);
    }

    @Override
    public List<EquipmentResponsibleDetailsDTO> getAllEquipmentResponsible() {
        return equipmentResponsibleRepository.findAllByOperativeTrue().stream().map(EquipmentResponsibleDetailsDTO::new).toList();
    }

    @Override
    public EquipmentResponsibleDetailsDTO createEquipmentResponsible(EquipmentResponsibleCreateDTO data) {
        Equipment equipment = equipmentRepository.findById(data.equipment())
                .orElseThrow(() -> new NotFoundException("Equipment id not found"));

        Responsible responsible = responsibleRepository.findById(data.responsible())
                .orElseThrow(() -> new NotFoundException("Responsible id not found"));

        EquipmentResponsible equipmentResponsible = new EquipmentResponsible(data, equipment, responsible);

        equipmentResponsibleRepository.save(equipmentResponsible);
        System.out.println("Post: Equipment responsible ");

        return new EquipmentResponsibleDetailsDTO(equipmentResponsible);

    }

    @Override
    public EquipmentResponsibleDetailsDTO updateEquipmentResponsible(Long id_equip_resp, EquipmentResponsibleUpdateDTO dados) {

        var equipmentResponsible = equipmentResponsibleRepository.findById(id_equip_resp)
                .orElseThrow(() -> new NotFoundException("Equipment Responsible with ID " + id_equip_resp + " not found."));

        if (dados.equipment() != null) {
            var equipment = equipmentRepository.findById(dados.equipment())
                    .orElseThrow(() -> new NotFoundException("Equipment with ID " + dados.equipment() + " not found."));
            if (!equipment.isOperative()) {
                throw new OperativeFalseException("Cannot assign inactive equipment.");
            }
            equipmentResponsible.setEquipment(equipment);
        }

        if (dados.responsible() != null) {
            var responsible = responsibleRepository.findById(dados.responsible())
                    .orElseThrow(() -> new NotFoundException("Responsible with ID " + dados.responsible() + " not found."));
            if (!responsible.isOperative()) {
                throw new OperativeFalseException("Cannot assign inactive responsible.");
            }
            equipmentResponsible.setResponsible(responsible);
        }

        if (dados.start_usage() != null) {
            equipmentResponsible.setStart_usage(dados.start_usage());
        }

        if (dados.end_usage() != null) {
            if (equipmentResponsible.getStart_usage() == null) {
                throw new IllegalArgumentException("Start date is required before setting the end date.");
            }
            if (dados.end_usage().isBefore(dados.start_usage())) {
                throw new IllegalArgumentException("End date must be later than start date.");
            }
            equipmentResponsible.setEnd_usage(dados.end_usage());
        }

        equipmentResponsibleRepository.save(equipmentResponsible);
        return new EquipmentResponsibleDetailsDTO(equipmentResponsible);
    }


    @Override
    public void activateEquipmentResponsible(Long id_equip_resp) {
        var equipmentResponsible = equipmentResponsibleRepository.findById(id_equip_resp)
                .orElseThrow(() -> new NotFoundException("Equipment Responsible with ID " + id_equip_resp + " not found."));

        if (equipmentResponsible.isOperative()) {
            throw new OperativeTrueException("Equipment Responsible is already active.");
        }
        equipmentResponsible.setOperative(true);
    }

    @Override
    public void inactivateEquipmentResponsible(Long id_equip_resp) {
        var equipmentResponsible = equipmentResponsibleRepository.findById(id_equip_resp)
                .orElseThrow(() -> new NotFoundException("Equipment Responsible with ID " + id_equip_resp + " not found."));

        if (!equipmentResponsible.isOperative()) {
            throw new OperativeFalseException("Equipment Responsible is already inactive.");
        }
        equipmentResponsible.setOperative(false);
    }

    @Override
    public EquipmentResponsibleSearchDetailsDTO getEquipmentInventory(Long code) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Equipment> criteriaQuery = criteriaBuilder.createQuery(Equipment.class);

        // Select From EquipmentResponsible
        Root<Equipment> equipmentRoot = criteriaQuery.from(Equipment.class);
        //Join<EquipmentResponsible, Equipment> equipmentJoin = equipmentRoot.join("equipment");
        //Join<EquipmentResponsible, Responsible> responsibleJoin = equipmentRoot.join("responsible");

        // Join para obter os dados do equipamento
        //Join<Equipment, MainOwner> mainOwnerJoin = equipmentJoin.join("owner");
        //Join<Equipment, Location> locationJoin = equipmentJoin.join("location");
        //Join<Location, Post> postJoin = locationJoin.join("post");
        //oin<Location, Environment> environmentJoin = locationJoin.join("environment");

        List<Predicate> predicates = new ArrayList<>();

        // WHERE


        if (code != null) {
            predicates.add(criteriaBuilder.equal(equipmentRoot.get("id_equipment"),code));
        }

        // Filtra apenas responsáveis operacionais
        predicates.add(criteriaBuilder.equal(equipmentRoot.get("operative"), true));

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Executa a consulta
        try {
            Equipment query = entityManager.createQuery(criteriaQuery).getSingleResult();

            List<ResponsibleDetailsDTO> responsibles = new ArrayList<>();
            List<EquipmentResponsible> equipmentResponsible = equipmentResponsibleRepository.findByEquipment(query);
            if (equipmentResponsible != null) {
                for (EquipmentResponsible equiprep : equipmentResponsible) {
                    responsibles.add(new ResponsibleDetailsDTO(equiprep.getResponsible()));
                }
            }

            // Mapeia a lista de EquipmentResponsible para EquipmentResponsibleDetailsDTO
            return new EquipmentResponsibleSearchDetailsDTO(new EquipmentDetailsDTO(query),responsibles);

        }catch (NoResultException e){
            throw new NotFoundException("Equipment with code " + code + " not found or is inactive.");
        }
    }
}
