package com.MapView.BackEnd;

import com.MapView.BackEnd.dtos.Area.AreaCreateDTO;
import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.entities.Area;
import com.MapView.BackEnd.entities.UserLog;
import com.MapView.BackEnd.infra.Exception.ExistingEntityException;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.AreaRepository;
import com.MapView.BackEnd.repository.UserLogRepository;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.serviceImp.AreaServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(OutputCaptureExtension.class)
public class AreaTest {

    @InjectMocks
    private AreaServiceImp areaServiceImp;

    @Mock
    private AreaRepository areaRepository;

    @Mock
    private UserLogRepository userLogRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testName(CapturedOutput output) {
        System.out.println("Hello World!");
        assertThat(output).contains("World");
    }

    @Test
    public void createArea_ShouldReturnAreaDetailsDTO() {
        // ARRANGE
        AreaCreateDTO areaCreateDTO = new AreaCreateDTO("Teste codigo", "teste nome");

        Area area = new Area(areaCreateDTO);
        when(areaRepository.save(any(Area.class))).thenReturn(area);

        // Simula o que deve ser retornado ao salvar o UserLog
        when(userLogRepository.save(any(UserLog.class))).thenReturn(new UserLog());

        // ACT
        AreaDetailsDTO result = areaServiceImp.createArea(areaCreateDTO, 1L);

        // ASSERT
        assertNotNull(result);
        assertEquals(areaCreateDTO.area_code(), result.area_code());
        assertEquals(areaCreateDTO.area_name(), result.area_name());
    }

    @Test
    public void createArea_UserNotFound_ShouldThrowNotFoundException() {
        // ARRANGE
        AreaCreateDTO areaCreateDTO = new AreaCreateDTO("Teste codigo", "teste nome");
        when(userLogRepository.findById(1L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            areaServiceImp.createArea(areaCreateDTO, 1L);
        });

        assertEquals("Id not found", exception.getMessage());
    }

    @Test
    public void createArea_ExistingEntity_ShouldThrowExistingEntityException() {
        // ARRANGE
        AreaCreateDTO areaCreateDTO = new AreaCreateDTO("Teste codigo", "teste nome");


        Area area = new Area(areaCreateDTO);
        when(areaRepository.save(any(Area.class))).thenThrow(new DataIntegrityViolationException(""));

        // ACT & ASSERT
        ExistingEntityException exception = assertThrows(ExistingEntityException.class, () -> {
            areaServiceImp.createArea(areaCreateDTO, 1L);
        });

        assertTrue(exception.getMessage().contains("Area already exists with that name"));
    }
}
