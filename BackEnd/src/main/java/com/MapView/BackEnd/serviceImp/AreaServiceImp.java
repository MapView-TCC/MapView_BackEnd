package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.enums.EnumAction;
import com.MapView.BackEnd.infra.Exception.BlankErrorException;
import com.MapView.BackEnd.infra.Exception.ExistingEntityException;
import com.MapView.BackEnd.infra.Exception.OperativeFalseException;
import com.MapView.BackEnd.repository.AreaRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.service.AreaService;
import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Area.AreaUpdateDTO;
import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.service.UserLogService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImp implements AreaService {


    private final AreaRepository areaRepository;
    private final UserLogRepository userLogRepository;
    private final UserRepository userRepository;

    public AreaServiceImp(AreaRepository areaRepository, UserLogService userLogService, UserLogRepository userLogRepository, UserRepository userRepository) {
        this.areaRepository = areaRepository;
        this.userLogRepository = userLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AreaDetailsDTO getArea(Long userLog_id, Long id_area) {
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));
        Area area = this.areaRepository.findById(id_area).orElseThrow(() -> new NotFoundException("Area with ID " + id_area + " not found."));

        if (!area.isOperative()) {
            throw new OperativeFalseException("Cannot read inactive area.");
        }

        var userLog = new UserLog(user, "Area", id_area.toString(), "Read Area", EnumAction.READ);
        userLogRepository.save(userLog);

        return new AreaDetailsDTO(area);
    }

    @Override
    public List<AreaDetailsDTO> getAllArea(Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        var userLog = new UserLog(user, "Area", "Read All Areas", EnumAction.READ);
        userLogRepository.save(userLog);

        return areaRepository.findAllByOperativeTrue().stream().map(AreaDetailsDTO::new).toList();
    }

    @Override
    public AreaDetailsDTO createArea(AreaCreateDTO data, Long userLog_id) {
        try {
            Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

            var area = new Area(data);
            Long id_area = areaRepository.save(area).getId_area();

            var userLog = new UserLog(user, "Area", id_area.toString(), "Create new Area", EnumAction.CREATE);
            userLogRepository.save(userLog);

            return new AreaDetailsDTO(area);
        } catch (DataIntegrityViolationException e) {
            throw new ExistingEntityException("An area already exists with the name: " + data.area_name() + " or code: " + data.area_code());
        }
    }

    @Override
    public AreaDetailsDTO updateArea(Long id_area, AreaUpdateDTO data, Long userLog_id) {
        Area area = areaRepository.findById(id_area).orElseThrow(() -> new NotFoundException("Area with ID " + id_area + " not found."));

        if (!area.isOperative()) {
            throw new OperativeFalseException("Cannot update inactive area.");
        }

        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));
        var userLog = new UserLog(user, "Area", id_area.toString(), null, "Update Area Information", EnumAction.UPDATE);

        if (data.area_name() != null && !data.area_name().isBlank()) {
            area.setArea_name(data.area_name());
            userLog.setField("Updated area_name to: " + data.area_name());
        }
        if (data.area_code() != null && !data.area_code().isBlank()) {
            area.setCode(data.area_code());
            userLog.setField(userLog.getField() + ", area_code to: " + data.area_code());
        }

        areaRepository.save(area);
        userLogRepository.save(userLog);

        return new AreaDetailsDTO(area);
    }


    @Override
    public void activateArea(Long id_area, Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        var area = areaRepository.findById(id_area).orElseThrow(() -> new NotFoundException("Area with ID " + id_area + " not found."));
        area.setOperative(true);
        areaRepository.save(area);

        var userLog = new UserLog(user, "Area", id_area.toString(), "Operative", "Activated Area", EnumAction.UPDATE);
        userLogRepository.save(userLog);
    }

    @Override
    public void inactivateArea(Long id_area, Long userLog_id) {
        Users user = this.userRepository.findById(userLog_id).orElseThrow(() -> new NotFoundException("User with ID " + userLog_id + " not found."));

        var area = areaRepository.findById(id_area).orElseThrow(() -> new NotFoundException("Area with ID " + id_area + " not found."));
        area.setOperative(false);
        areaRepository.save(area);

        var userLog = new UserLog(user, "Area", id_area.toString(), "Operative", "Inactivated Area", EnumAction.UPDATE);
        userLogRepository.save(userLog);
    }
}
