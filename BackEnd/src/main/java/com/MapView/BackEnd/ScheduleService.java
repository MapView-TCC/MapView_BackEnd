package com.MapView.BackEnd;

import com.MapView.BackEnd.entities.Equipment;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.entities.TrackingHistory;
import com.MapView.BackEnd.repository.EquipmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component

public class ScheduleService {

    private final EquipmentRepository equipmentRepository;
    public ScheduleService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    // toda a segunda feira ele vai fazer essa função
    @Scheduled(cron = "0 0 0 * * MON")
    public void executed() {
        List<Equipment> equipmentList = equipmentRepository.findAllByOperativeTrue();

        for (Equipment e : equipmentList) {
            LocalDate validity = e.getValidity();

            if (validity != null && LocalDate.now().getYear() == validity.getYear()) {

                if (getTrimestre(validity.getMonthValue()) == getTrimestre(LocalDate.now().getMonthValue())) {

                    TrackingHistory trackingHistory = new TrackingHistory();

                }
            }
        }
    }

    // Função para calcular o trimestre baseado no mês
    public static int getTrimestre(int month) {
        return (month - 1) / 3 + 1;
    }

}
