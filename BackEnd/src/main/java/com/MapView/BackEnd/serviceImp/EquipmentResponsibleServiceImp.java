package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.infra.Exception.OperativeFalseException;
import com.MapView.BackEnd.infra.Exception.OpetativeTrueException;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.EquipmentResponsibleRepository;
import com.MapView.BackEnd.repository.ResponsibleRepository;
import com.MapView.BackEnd.service.EquipmentResponsibleService;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleCreateDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleUpdateDTO;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.MapView.BackEnd.serviceImp.EquipmentServiceImp.getStartDateFromQuarter;

@Service
public class EquipmentResponsibleServiceImp implements EquipmentResponsibleService {

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
                .orElseThrow(() -> new NotFoundException("Equipment responsible id not found"));

        return new EquipmentResponsibleDetailsDTO(equipmentResponsible);
    }

    @Override
    public List<EquipmentResponsibleDetailsDTO> getAllEquipmentResponsible() {
        return equipmentResponsibleRepository.findAllByOperativeTrue().stream().map(EquipmentResponsibleDetailsDTO::new).toList();
    }

    @Override
    public EquipmentResponsibleDetailsDTO createEquipmentResponsible(EquipmentResponsibleCreateDTO data) {
        Equipment equipment = equipmentRepository.findById(data.id_equipment())
                .orElseThrow(() -> new NotFoundException("Equipment id not found"));

        Responsible responsible = responsibleRepository.findById(data.id_responsible())
                .orElseThrow(() -> new NotFoundException("Responsible id not found"));

        EquipmentResponsible equipmentResponsible = new EquipmentResponsible(data, equipment, responsible);

        equipmentResponsibleRepository.save(equipmentResponsible);
        System.out.println("Post: Equipmentresponsible ");

        return new EquipmentResponsibleDetailsDTO(equipmentResponsible);

    }

    @Override
    public EquipmentResponsibleDetailsDTO updateEquipmentResponsible(Long id_equip_resp, EquipmentResponsibleUpdateDTO dados) {

        var equipmentResponsible = equipmentResponsibleRepository.findById(id_equip_resp)
                .orElseThrow(() -> new NotFoundException("Equipment responsible id not found"));

        if (dados.id_equipment() != null){
            var equipment = equipmentRepository.findById(dados.id_equipment())
                    .orElseThrow(() -> new NotFoundException("Equipment id not found"));
            if (!equipment.isOperative()){
                throw new OperativeFalseException("The inactive equipment cannot be accessed.");
            }
            equipmentResponsible.setIdEquipment(equipment);
        }

        if (dados.id_responsible() != null){
            var responsible = responsibleRepository.findById(dados.id_responsible())
                    .orElseThrow(() -> new NotFoundException("Responsible id not found"));
            if (!responsible.isOperative()){
                throw new OperativeFalseException("The inactive responsible cannot be accessed.");
            }
            equipmentResponsible.setId_responsible(responsible);
        }

        if (dados.start_usage() != null){
            equipmentResponsible.setStart_usage(dados.start_usage());
        }

        if (dados.end_usage() != null) {
            // ver se a data inicial não está vazia
            if (dados.start_usage() == null) {
                throw new IllegalArgumentException("Start date must be set before setting the end date.");
            }
            // O método isBefore é usado para comparar instantes de tempo, datas e horas.
            if (dados.end_usage().isBefore(dados.start_usage())) {
                throw new IllegalArgumentException("The end date must be greater than or equal to the start date.");
            }

            equipmentResponsible.setEnd_usage(dados.end_usage());
        }

        equipmentResponsibleRepository.save(equipmentResponsible);
        return new EquipmentResponsibleDetailsDTO(equipmentResponsible);
    }


    @Override
    public void activateEquipmentResponsible(Long id_equip_resp) {
        var equipmentResponsible = equipmentResponsibleRepository.findById(id_equip_resp) .orElseThrow(() -> new NotFoundException("Equipment id not found"));
        if (equipmentResponsible.isOperative()){
            throw new OpetativeTrueException("It is already activate");
        }
        equipmentResponsible.setOperative(true);
        throw new OperativeFalseException("");
    }

    @Override
    public void inactivateEquipmentResponsible(Long id_equip_resp) {
        var equipmentResponsible = equipmentResponsibleRepository.findById(id_equip_resp).orElseThrow(() -> new NotFoundException("Equipment id not found"));
        if (!equipmentResponsible.isOperative()){
            throw new OperativeFalseException("It is already inactivate");
        }
        equipmentResponsible.setOperative(false);
        throw new OperativeFalseException("");
    }

    @Override
    public List<EquipmentResponsibleDetailsDTO> getEquipmentInventory(int page, int itens, String validity,
                                                                      String environment, String id_owner, String id_equipment,
                                                                      String name_equipment, String post) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<EquipmentResponsible> criteriaQuery = criteriaBuilder.createQuery(EquipmentResponsible.class);

        // Select From EquipmentResponsible
        Root<EquipmentResponsible> equipmentResponsibleRoot = criteriaQuery.from(EquipmentResponsible.class);
        Join<EquipmentResponsible, Equipment> equipmentJoin = equipmentResponsibleRoot.join("idEquipment");
        Join<EquipmentResponsible, Responsible> responsibleJoin = equipmentResponsibleRoot.join("id_responsible");

        // Join para obter os dados do equipamento
        Join<Equipment, MainOwner> mainOwnerJoin = equipmentJoin.join("owner");
        Join<Equipment, Location> locationJoin = equipmentJoin.join("location");
        Join<Location, Post> postJoin = locationJoin.join("post");
        Join<Location, Environment> environmentJoin = locationJoin.join("environment");

        List<Predicate> predicates = new ArrayList<>();

        // WHERE

        if (validity != null) {
            LocalDate validDate = getStartDateFromQuarter(validity);
            predicates.add(criteriaBuilder.equal(equipmentJoin.get("validity"), validDate));
        }
        if (environment != null) {
            predicates.add(criteriaBuilder.like(environmentJoin.get("environment_name"), "%" + environment.toLowerCase() + "%"));
        }
        if (id_owner != null) {
            predicates.add(criteriaBuilder.like(mainOwnerJoin.get("id_owner"), "%" + id_owner.toLowerCase() + "%"));
        }
        if (id_equipment != null) {
            predicates.add(criteriaBuilder.like(equipmentJoin.get("idEquipment"), "%" + id_equipment.toLowerCase() + "%"));
        }
        if (name_equipment != null) {
            predicates.add(criteriaBuilder.like(equipmentJoin.get("name_equipment"), "%" + name_equipment.toLowerCase() + "%"));
        }
        if (post != null) {
            predicates.add(criteriaBuilder.like(postJoin.get("post"), "%" + post.toLowerCase() + "%"));
        }

        // Filtra apenas responsáveis operacionais
        predicates.add(criteriaBuilder.equal(equipmentResponsibleRoot.get("operative"), true));

        criteriaQuery.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // Executa a consulta
        TypedQuery<EquipmentResponsible> query = entityManager.createQuery(criteriaQuery);
        List<EquipmentResponsible> equipmentResponsibleList = query.getResultList();

        // Mapeia a lista de EquipmentResponsible para EquipmentResponsibleDetailsDTO
        return equipmentResponsibleList.stream()
                .map(EquipmentResponsibleDetailsDTO::new)
                .collect(Collectors.toList());
    }

}
