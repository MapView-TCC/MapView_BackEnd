package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentCreateDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentDetailsDTO;
import com.MapView.BackEnd.dtos.FormsRegisterEnviroment.FormsRegisterEnviromentDetailsDTO;
import com.MapView.BackEnd.dtos.FormsRegisterEnviroment.FormsRegisterEnvironmentCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryCreateDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;

import org.springframework.stereotype.Service;

@Service

public class FormsRegisterEnviromentImp {
    private final BuildingServiceImp buildingServiceImp;
    private final AreaServiceImp areaServiceImp;
    private final RaspberryServiceImp raspberryServiceImp;
    private final EnvironmentServiceImp environmentServiceImp;

    public FormsRegisterEnviromentImp(BuildingServiceImp buildingServiceImp, AreaServiceImp areaServiceImp, RaspberryServiceImp raspberryServiceImp, EnvironmentServiceImp environmentServiceImp) {
        this.buildingServiceImp = buildingServiceImp;
        this.areaServiceImp = areaServiceImp;
        this.raspberryServiceImp = raspberryServiceImp;
        this.environmentServiceImp = environmentServiceImp;
    }


    public FormsRegisterEnviromentDetailsDTO createFormsRegisterEnvironment(FormsRegisterEnvironmentCreateDTO data,Long userLog){
        BuildingDetailsDTO build =  buildingServiceImp.getBuilding(data.id_building(), userLog);
        AreaDetailsDTO area = areaServiceImp.getArea(userLog, data.id_area());
        RaspberryDetailsDTO raspberry = raspberryServiceImp.createRaspberry(new RaspberryCreateDTO(data.raspberry_name(), build.id_building(), area.id_area()),userLog);
        EnvironmentDetailsDTO environment = environmentServiceImp.createEnvironment(new EnvironmentCreateDTO(data.environment_name(), raspberry.id_raspberry()),userLog);

        return new FormsRegisterEnviromentDetailsDTO(build,environment);

    }
}
