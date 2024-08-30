package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.dtos.UserLog.UserLogCreateDTO;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.repository.AreaRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.service.AreaService;
import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;
import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.infra.NotFoundException;
import com.MapView.BackEnd.service.UserLogService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AreaServiceImp implements AreaService {


    private final AreaRepository areaRepository;
    private final UserLogService userLogService;

    public AreaServiceImp(AreaRepository areaRepository, UserLogService userLogService) {
        this.areaRepository = areaRepository;
        this.userLogService = userLogService;

    }

    @Override
    public AreaDetailsDTO getArea(Long id_area) {
        Area area = this.areaRepository.findById(id_area).orElseThrow(() -> new NotFoundException("Id not found"));

        if (!area.check_status()){
            return null;
        }



        return new AreaDetailsDTO(area);
    }

    @Override
    public List<AreaDetailsDTO> getAllArea(int page, int itens) {
        var userLog = new UserLog(null,"Area",null,null,"Create new Area", EnumAction.READ);
        userLogService.createUserLog(user_id,userLog);
        return areaRepository.findAllByOperativeTrue(PageRequest.of(page, itens)).stream().map(AreaDetailsDTO::new).toList();
    }

    @Override
    public AreaDetailsDTO createArea(AreaCreateDTO dados, Long user_id) {
        var area = new Area(dados);
        Long id_area = areaRepository.save(area).getId_area();

        var userLog = new UserLog(null,"Area",id_area,null,"Create new Area", EnumAction.CREATE);
        userLogService.createUserLog(user_id,userLog);

        return new AreaDetailsDTO(area);
    }

    @Override
    public AreaDetailsDTO updateArea(Long id_area, AreaUpdateDTO dados,Long user_id) {
        var area = areaRepository.findById(id_area).orElseThrow(() -> new NotFoundException("Id not found"));
        var userlog = new UserLog(null,"Area",id_area,null,"Infos update",EnumAction.UPDATE);

        if (dados.area_name() != null){
            area.setArea_name(dados.area_name());
            userlog.setField("area_name");
        }
        if (dados.area_code() != null){
            area.setArea_code(dados.area_code());
            userlog.setField(userlog.getField()+","+"area_code");
        }
        userLogService.createUserLog(user_id,userlog);
        areaRepository.save(area);

        return new AreaDetailsDTO(area);
    }

    @Override
    public void activateArea(Long id_area) {
        var areaClass = areaRepository.findById(id_area);
        if (areaClass.isPresent()){
            var area = areaClass.get();
            area.setOperative(true);
        }
    }

    @Override
    public void inactivateArea(Long id_area) {
        var areaClass = areaRepository.findById(id_area);
        if (areaClass.isPresent()){
            var area = areaClass.get();
            area.setOperative(false);
        }
    }
}
