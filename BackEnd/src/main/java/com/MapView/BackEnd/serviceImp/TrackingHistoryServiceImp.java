package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryWrongLocationDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.repository.*;
import com.MapView.BackEnd.service.TrackingHistoryService;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrackingHistoryServiceImp implements TrackingHistoryService {


    private final TrackingHistoryRepository trackingHistoryRepository;
    private final EnvironmentRepository environmentRepository;
    private final EquipmentRepository equipmentRepository;
    private final LocationRepository locationRepository;
    private final EquipmentResponsibleRepository equipmentResponsibleRepository;

    private  SimpMessagingTemplate template;


    public TrackingHistoryServiceImp(TrackingHistoryRepository trackingHistoryRepository, EnvironmentRepository environmentRepository, EquipmentRepository equipmentRepository, LocationRepository locationRepository, EquipmentResponsibleRepository equipmentResponsibleRepository, SimpMessagingTemplate template) {
        this.trackingHistoryRepository = trackingHistoryRepository;
        this.environmentRepository = environmentRepository;
        this.equipmentRepository = equipmentRepository;
        this.locationRepository = locationRepository;
        this.equipmentResponsibleRepository = equipmentResponsibleRepository;
        this.template = template;
    }

    @Override
    public TrackingHistoryDetailsDTO getTrackingHistory(Long id_tracking) {
        TrackingHistory trackingHistory = this.trackingHistoryRepository.findById(id_tracking)
                .orElseThrow(() -> new NotFoundException("Id not found"));

        return new TrackingHistoryDetailsDTO(trackingHistory);
    }

    @Override
    public List<TrackingHistoryDetailsDTO> getAllTrackingHistory(int page, int itens) {
        return trackingHistoryRepository.findAll(PageRequest.of(page, itens)).stream().map(TrackingHistoryDetailsDTO::new).toList();


    }

    @Override
    @CrossOrigin("http://localhost:3001")
    public TrackingHistoryDetailsDTO createTrackingHistory(TrackingHistoryCreateDTO dados) {
        Environment local_tracking = environmentRepository.findById(dados.id_environment())
                .orElseThrow(() -> new NotFoundException("Id not found"));

        Optional<Equipment> equipment = equipmentRepository.findByRfid(dados.rfid());

        if (equipment.isEmpty()) {
            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(dados.rfid(), local_tracking, EnumColors.RED));
            Equipment emptyEquipment = new Equipment(UUID.randomUUID().toString().substring(0,8), dados.rfid());
            equipmentRepository.save(emptyEquipment);
            trackingHistoryRepository.save(trackingHistory);
            template.convertAndSend("/equip",trackingHistory);

            return new TrackingHistoryDetailsDTO(trackingHistory);
        }

        TrackingHistory last_track = trackingHistoryRepository.findTopByEquipmentOrderByDatetimeDesc(equipment.get());
        if (last_track == null){
            throw new NotFoundException("RFID tag is not linked to any equipment");
        }

        Environment last_track_local = last_track.getEnvironment();


        if (last_track_local.equals(local_tracking)) {
            if (last_track.getAction().equals(EnumTrackingAction.OUT)){

                if (local_tracking.getEnvironment_name().equals("BTC")){

                    System.out.println("_-----1--------");
                    TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(),EnumTrackingAction.ENTER, EnumColors.GREEN));
                    template.convertAndSend("/equip",trackingHistory);
                    return new TrackingHistoryDetailsDTO(trackingHistory);
                }
                System.out.println("_-----2--------");
                TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(),EnumTrackingAction.OUT, EnumColors.GREEN));
                template.convertAndSend("/equip",trackingHistory);
                return new TrackingHistoryDetailsDTO(trackingHistory);
            }
            if (local_tracking.getEnvironment_name().equals("BTC")){
                System.out.println("_-----3--------");
                TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(),EnumTrackingAction.OUT, EnumColors.YELLOW));
                template.convertAndSend("/equip",trackingHistory);
                return new TrackingHistoryDetailsDTO(trackingHistory);
            }
            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(),EnumTrackingAction.OUT, EnumColors.GREEN));
            template.convertAndSend("/equip",trackingHistory);
            System.out.println("_-----4--------");
            return new TrackingHistoryDetailsDTO(trackingHistory);
        }
        System.out.println("_-----5--------");
        trackingHistoryRepository.save(new TrackingHistory(last_track_local,equipment.get(), dados.rfid(), EnumTrackingAction.OUT,EnumColors.GREEN));
        TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(), EnumTrackingAction.ENTER, EnumColors.GREEN));
        template.convertAndSend("/equip",trackingHistory);

        System.out.println("Se o rfid ta salvando aqui " + dados.rfid());
        System.out.println(trackingHistory.getRfid());

        return new TrackingHistoryDetailsDTO(trackingHistory);
    }




    @Override
    public List<TrackingHistoryDetailsDTO> FilterTracking(int page, int itens, EnumTrackingAction action, Integer day,
                                                          Integer month, Integer year, EnumColors colors, String id_equipment) {

        List<TrackingHistory> filterTracking;

        // Se a ação não for nula, filtra pelo valor da ação
        filterTracking = trackingHistoryRepository.findAll(PageRequest.of(page, itens)).stream()
                .filter(t -> (action == null || t.getAction() == action))
                .filter(t -> (colors == null || t.getWarning() == colors))
                .filter(t -> (id_equipment == null || (t.getEquipment() != null && t.getEquipment().getIdEquipment().equalsIgnoreCase(id_equipment))))
                .filter(t -> {
                    // Converte o Instant para LocalDateTime no fuso horário local
                    LocalDateTime dateTime = t.getDatetime().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    boolean match = true; // é true pois está assumindo que o registro deve ser incluído no resultado, a menos que uma das condições subsequentes o desqualifique.
                    // Se day, month, e year forem null: match permanece true e o registro passa no filtro.
                    // Se day for 30 e o dia em dateTime também for 30: match continua true.
                    // Se month for 8, mas o mês em dateTime for 7: match se torna false e o registro é filtrado fora.
                    if (day != null) {
                        match = match && dateTime.getDayOfMonth() == day;
                    }
                    if (month != null) {
                        match = match && dateTime.getMonthValue() == month;
                    }
                    if (year != null) {
                        match = match && dateTime.getYear() == year;
                    }
                    return match;
                })
                .toList();

        // Converte para DTO
        return filterTracking.stream()
                .map(TrackingHistoryDetailsDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteTracking(Long id_tracking) {
        var tracking = trackingHistoryRepository.findById(id_tracking).orElseThrow(() ->
                new NotFoundException("Id not found"));
        if (tracking != null){
            trackingHistoryRepository.deleteById(id_tracking);
        }
    }

    public List<TrackingHistoryWrongLocationDTO> findWrongLocationEquipments(Long id_environment) {
        // Verifica se o ambiente existe
        Environment environment = environmentRepository.findById(id_environment)
                .orElseThrow(() -> new NotFoundException("Environment not found"));

        List<TrackingHistory> trackingHistory = trackingHistoryRepository.findByEnvironment(environment);
        List<TrackingHistoryWrongLocationDTO> wrongLocationDTOs = new ArrayList<>();

        Set<String> addedEquipmentIds = new HashSet<>();  // Usar um Set para evitar duplicações

        for (TrackingHistory track : trackingHistory) {
            if (track.getAction().equals(EnumTrackingAction.ENTER)) {
                Equipment equipment = track.getEquipment();
                Long locationEquip = equipment.getLocation().getEnvironment().getId_environment();

                if (!locationEquip.equals(id_environment)) {
                    if (!addedEquipmentIds.contains(equipment.getIdEquipment())) {
                        addedEquipmentIds.add(equipment.getIdEquipment());

                        List<EquipmentResponsible> equipmentResponsibles = equipmentResponsibleRepository.findByIdEquipment(equipment);
                        List<String> responsibles = new ArrayList<>();

                        for (EquipmentResponsible responsible : equipmentResponsibles) {
                            responsibles.add(responsible.getId_responsible().getResponsible_name());
                        }

                        wrongLocationDTOs.add(new TrackingHistoryWrongLocationDTO(equipment, environment,responsibles));
                    }
                }
            }
        }

        return wrongLocationDTOs;
    }

    @Override
    public void deleteTrackingById(Long id_tracking) {
        var tracking  = trackingHistoryRepository.findById(id_tracking).orElseThrow(() ->
                new NotFoundException("Id not found"));
        if (tracking != null){
            trackingHistoryRepository.deleteById(id_tracking);
        }
    }

}



