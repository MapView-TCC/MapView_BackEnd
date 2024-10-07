package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryWrongLocationDTO;
import com.MapView.BackEnd.entities.*;
import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.repository.*;
import com.MapView.BackEnd.service.TrackingHistoryService;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrackingHistoryServiceImp implements TrackingHistoryService {


    private final TrackingHistoryRepository trackingHistoryRepository;
    private final EnviromentRepository enviromentRepository;
    private final EquipmentRepository equipmentRepository;
    private final LocationRepository locationRepository;
    private final EquipmentResponsibleRepository equipmentResponsibleRepository;


    public TrackingHistoryServiceImp(TrackingHistoryRepository trackingHistoryRepository, EnviromentRepository enviromentRepository, EquipmentRepository equipmentRepository, LocationRepository locationRepository, EquipmentResponsibleRepository equipmentResponsibleRepository) {
        this.trackingHistoryRepository = trackingHistoryRepository;
        this.enviromentRepository = enviromentRepository;
        this.equipmentRepository = equipmentRepository;
        this.locationRepository = locationRepository;
        this.equipmentResponsibleRepository = equipmentResponsibleRepository;
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
    public TrackingHistoryDetailsDTO createTrackingHistory(TrackingHistoryCreateDTO dados) {
        Enviroment local_tracking = enviromentRepository.findById(dados.id_environment())
                .orElseThrow(() -> new NotFoundException("Id not found"));

        Optional<Equipment> equipment = equipmentRepository.findByRfid(dados.rfid());

        if (equipment.isEmpty()) {
            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(dados.rfid(), local_tracking, EnumColors.RED));
            Equipment emptyEquipment = new Equipment(UUID.randomUUID().toString().substring(0,8), dados.rfid());
            equipmentRepository.save(emptyEquipment);
            trackingHistoryRepository.save(trackingHistory);
            return new TrackingHistoryDetailsDTO(trackingHistory);
        }

        TrackingHistory last_track = trackingHistoryRepository.findTopByEquipmentOrderByDatetimeDesc(equipment.get());
        if (last_track == null){
            throw new NotFoundException("RFID tag is not linked to any equipment2");
        }

        Enviroment last_track_local = last_track.getEnvironment();


        if (last_track_local.equals(local_tracking)) {
            if (last_track.getAction().equals(EnumTrackingAction.OUT)){

                if (local_tracking.getEnvironment_name().equals("BTC")){

                    System.out.println("_-----1--------");
                    TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(),EnumTrackingAction.ENTER, EnumColors.GREEN));
                    return new TrackingHistoryDetailsDTO(trackingHistory);
                }
                System.out.println("_-----2--------");
                TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(),EnumTrackingAction.OUT, EnumColors.GREEN));
                return new TrackingHistoryDetailsDTO(trackingHistory);
            }
            if (local_tracking.getEnvironment_name().equals("BTC")){
                System.out.println("_-----3--------");
                TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(),EnumTrackingAction.OUT, EnumColors.YELLOW));
                return new TrackingHistoryDetailsDTO(trackingHistory);
            }
            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(),EnumTrackingAction.OUT, EnumColors.GREEN));
            System.out.println("_-----4--------");
            return new TrackingHistoryDetailsDTO(trackingHistory);
        }
        System.out.println("_-----5--------");
        trackingHistoryRepository.save(new TrackingHistory(last_track_local,equipment.get(), dados.rfid(), EnumTrackingAction.OUT,EnumColors.GREEN));
        TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment.get(), dados.rfid(), EnumTrackingAction.ENTER, EnumColors.GREEN));

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
                .filter(t -> (id_equipment == null || (t.getEquipment() != null && t.getEquipment().getIdEquipment().equals(id_equipment))))
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
        Enviroment enviroment = enviromentRepository.findById(id_environment)
                .orElseThrow(() -> new NotFoundException("Enviroment not found"));

        List<TrackingHistory> trackingHistory = trackingHistoryRepository.findByEnvironment(enviroment);
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

                        wrongLocationDTOs.add(new TrackingHistoryWrongLocationDTO(equipment, enviroment,responsibles));
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



