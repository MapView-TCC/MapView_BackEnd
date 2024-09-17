package com.MapView.BackEnd;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.repository.EquipmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component

public class ScheduleService {

    private EquipmentRepository equipmentRepository;
    public ScheduleService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void executed(){
        List<Equipment> equipment = equipmentRepository.findAllByOperativeTrue();

        for (Equipment e: equipment){
            int ano = Integer.parseInt(e.getValidity().substring(0,4));
            int trimestre = Integer.parseInt(e.getValidity().substring(6));

            if(LocalDate.now().getYear() == ano){
                if(getTrimestre() == trimestre){
                    new TrackingHistory();
                }
            }

        }
    }
    public static int getTrimestre() {
        int month = LocalDate.now().getMonthValue();
        return (month + 1) / 3 + 1 ;
    }
}
