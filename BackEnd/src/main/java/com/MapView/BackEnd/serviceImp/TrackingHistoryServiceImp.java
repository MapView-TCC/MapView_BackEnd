package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.Raspberry;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.enums.EnumColors;
import com.MapView.BackEnd.enums.EnumTrackingAction;
import com.MapView.BackEnd.repository.EnviromentRepository;
import com.MapView.BackEnd.repository.EquipmentRepository;
import com.MapView.BackEnd.repository.RaspberryRepository;
import com.MapView.BackEnd.repository.TrackingHistoryRepository;
import com.MapView.BackEnd.service.TrackingHistoryService;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryCreateDTO;
import com.MapView.BackEnd.dtos.TrackingHistory.TrackingHistoryDetailsDTO;
import com.MapView.BackEnd.entities.Enviroment;
import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.infra.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TrackingHistoryServiceImp implements TrackingHistoryService {


    private final TrackingHistoryRepository trackingHistoryRepository;
    private final EnviromentRepository enviromentRepository;
    private final EquipmentRepository equipmentRepository;



    public TrackingHistoryServiceImp(TrackingHistoryRepository trackingHistoryRepository, EnviromentRepository enviromentRepository, EquipmentRepository equipmentRepository) {
        this.trackingHistoryRepository = trackingHistoryRepository;
        this.enviromentRepository = enviromentRepository;
        this.equipmentRepository = equipmentRepository;

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


        Equipment equipment = equipmentRepository.findById(dados.id_equipment())
                .orElseThrow(() -> new NotFoundException("Id not found"));

        Enviroment local_tracking = enviromentRepository.findById(dados.id_environment())
                .orElseThrow(() -> new NotFoundException("Id not found"));


        TrackingHistory last_track = trackingHistoryRepository.findTopByIdEquipmentOrderByDatetimeDesc(equipment);

        // Se não houver registro anterior, considere a etiqueta não cadastrada
        if (last_track == null) {
            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.RED));
            return new TrackingHistoryDetailsDTO(trackingHistory);
        }

        Enviroment last_track_local = last_track.getId_environment();
        Enviroment local_notebook = equipment.getLocation().getEnvironment();

        if(local_notebook == local_tracking) {

            if (last_track_local.equals(local_tracking)) {
                if (last_track.getAction().equals(EnumTrackingAction.OUT)) {
                    TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.GREEN));
                    System.out.println("_-----1--------");
                    return new TrackingHistoryDetailsDTO(trackingHistory);
                }
                System.out.println("_-----2--------");
                TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.OUT, EnumColors.YELLOW));
                return new TrackingHistoryDetailsDTO(trackingHistory);
            }

            System.out.println("_-----3--------");
            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.GREEN));
            return new TrackingHistoryDetailsDTO(trackingHistory);

        }
        TrackingHistory trackingHistory2 = trackingHistoryRepository.save(new TrackingHistory(last_track_local, equipment, EnumTrackingAction.OUT, EnumColors.GREEN));
        trackingHistoryRepository.save(trackingHistory2);
        TrackingHistory trackingHistory1 = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.GREEN));
        trackingHistoryRepository.save(trackingHistory1);




        if (last_track_local == local_tracking){

            if (last_track.getAction() == EnumTrackingAction.ENTER){
                System.out.println("_-----4--------");
                TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.OUT, EnumColors.YELLOW));
                return new TrackingHistoryDetailsDTO(trackingHistory);

            }
            System.out.println("_-----5--------");
            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.YELLOW));
            return new TrackingHistoryDetailsDTO(trackingHistory);
        }
        System.out.println("_-----6--------");
        return new TrackingHistoryDetailsDTO(last_track);

    }

//    @Override
//    public TrackingHistoryDetailsDTO createTrackingHistory(TrackingHistoryCreateDTO dados) {
//
//        // Verifica se o equipamento existe na tabela
//        Equipment equipment = equipmentRepository.findById(dados.id_equipment())
//                .orElse(null);
//
//        if (equipment == null) {
//            // Se o equipamento não existe, define a cor como vermelho
//            Enviroment local_tracking = enviromentRepository.findById(dados.id_environment())
//                    .orElseThrow(() -> new NotFoundException("Id not found"));
//
//            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, null, EnumTrackingAction.ENTER, EnumColors.RED));
//            return new TrackingHistoryDetailsDTO(trackingHistory);
//        }
//
//        // Equipamento encontrado, continua com a lógica normal
//        Enviroment local_tracking = enviromentRepository.findById(dados.id_environment())
//                .orElseThrow(() -> new NotFoundException("Id not found"));
//
//        TrackingHistory last_track = trackingHistoryRepository.findTopByIdEquipmentOrderByDatetimeDesc(equipment);
//
//        // Se não houver registro anterior, o equipamento foi visto pela primeira vez
//        if (last_track == null) {
//            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.YELLOW));
//            return new TrackingHistoryDetailsDTO(trackingHistory);
//        }
//
//        Enviroment last_track_local = last_track.getId_environment();
//        Enviroment local_notebook = equipment.getLocation().getEnvironment();
//
//        if (local_notebook.equals(local_tracking)) {
//            // Equipamento está na sala de origem
//            if (last_track_local.equals(local_tracking)) {
//                // Já estava na mesma sala
//                if (last_track.getAction().equals(EnumTrackingAction.OUT)) {
//                    // Última ação foi saída
//                    TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.GREEN));
//                    return new TrackingHistoryDetailsDTO(trackingHistory);
//                }
//                // Última ação foi entrada
//                TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.GREEN));
//                return new TrackingHistoryDetailsDTO(trackingHistory);
//            }
//            // Equipamento entrou na sala de origem
//            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.GREEN));
//            return new TrackingHistoryDetailsDTO(trackingHistory);
//        }
//
//        // Equipamento está fora da sala de origem
//        if (last_track_local.equals(local_notebook)) {
//            // Última localização foi a sala de origem
//            TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.OUT, EnumColors.YELLOW));
//            return new TrackingHistoryDetailsDTO(trackingHistory);
//        }
//
//        // Última localização foi diferente e agora está em um novo local
//        TrackingHistory trackingHistory = trackingHistoryRepository.save(new TrackingHistory(local_tracking, equipment, EnumTrackingAction.ENTER, EnumColors.YELLOW));
//        return new TrackingHistoryDetailsDTO(trackingHistory);
//    }


    @Override
    public List<TrackingHistoryDetailsDTO> FilterTracking(int page, int itens, EnumTrackingAction action, Integer day,
                                                          Integer month, Integer year, EnumColors colors, String id_equipment) {

        List<TrackingHistory> filterTracking;

        // Se a ação não for nula, filtra pelo valor da ação
        filterTracking = trackingHistoryRepository.findAll(PageRequest.of(page, itens)).stream()
                .filter(t -> (action == null || t.getAction() == action))
                .filter(t -> (colors == null || t.getWarning() == colors))
                .filter(t -> (id_equipment == null || (t.getIdEquipment() != null && t.getIdEquipment().getId_equipment().equals(id_equipment))))
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
}
