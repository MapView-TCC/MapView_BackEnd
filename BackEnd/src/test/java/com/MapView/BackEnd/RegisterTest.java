import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.MapView.BackEnd.dtos.Area.AreaDetailsDTO;
import com.MapView.BackEnd.dtos.Building.BuildingDetailsDTO;
import com.MapView.BackEnd.dtos.Classes.ClassesDetaiLDTO;
import com.MapView.BackEnd.dtos.CostCenter.CostCenterDetailsDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentCreateDTO;
import com.MapView.BackEnd.dtos.Environment.EnvironmentDetailsDTO;
import com.MapView.BackEnd.dtos.Equipment.EquipmentDetailsDTO;
import com.MapView.BackEnd.dtos.EquipmentResponsible.EquipmentResponsibleDetailsDTO;
import com.MapView.BackEnd.dtos.Location.LocationDetalsDTO;
import com.MapView.BackEnd.dtos.MainOwner.MainOwnerDetailsDTO;
import com.MapView.BackEnd.dtos.Post.PostDetailDTO;
import com.MapView.BackEnd.dtos.Raspberry.RaspberryDetailsDTO;
import com.MapView.BackEnd.dtos.Register.RegisterCreateDTO;
import com.MapView.BackEnd.dtos.Register.RegisterDetailsDTO;
import com.MapView.BackEnd.dtos.Responsible.ResponsibleDetailsDTO;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.dtos.*;
import com.MapView.BackEnd.repository.*;
import com.MapView.BackEnd.serviceImp.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

public class RegisterTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private EnvironmentRepository environmentRepository;
    @Mock
    private EquipmentRepository equipmentRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private UserLogRepository userLogRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MainOwnerRepository mainOwnerRepository;
    @Mock
    private ResponsibleRepository responsibleRepository;
    @Mock
    private EquipmentResponsibleRepository equipmentResponsibleRepository;
    @Mock
    private ClassesRepository classesRepository;

    @Mock
    private EquipmentServiceImp equipmentServiceImp;
    @Mock
    private LocationServiceImp locationServiceImp;
    @Mock
    private PostServiceImp postServiceImp;
    @Mock
    private EnvironmentServiceImp environmentServiceImp;
    @Mock
    private MainOwnerServiceImp mainOwnerServiceImp;
    @Mock
    private CostCenterServiceImp costCenterServiceImp;
    @Mock
    private ClassesServiceImp classesServiceImp;
    @Mock
    private ResponsibleServiceImp responsibleServiceImp;
    @Mock
    private EquipmentResponsibleServiceImp equipmentResponsibleServiceImp;
    @Mock
    private RaspberryServiceImp raspberryServiceImp;
    @Mock
    private BuildingServiceImp buildingServiceImp;
    @Mock
    private AreaServiceImp areaServiceImp;


    @InjectMocks
    private RegisterServiceImp registerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSuccessfully() {
        // Arrange
        Long userLogId = 1L;
        Users user = new Users();
        user.setId_user(userLogId);

        RegisterCreateDTO registerData = new RegisterCreateDTO();
        // Preencher registerData conforme necessário

        when(userRepository.findById(userLogId)).thenReturn(Optional.of(user));
        // Mockar os serviços usados no método register
        when(postServiceImp.createPost(any(), eq(userLogId))).thenReturn(new PostDetailDTO(1L, "post"));
        when(buildingServiceImp.createBuilding(any(), eq(userLogId))).thenReturn(new BuildingDetailsDTO(1L, "ETS"));
        when(areaServiceImp.createArea(any(), eq(userLogId))).thenReturn(new AreaDetailsDTO(1L, "teste", "teste name"));
        when(raspberryServiceImp.createRaspberry(any(), eq(userLogId))).thenReturn(new RaspberryDetailsDTO("RP001", 1L, 1L));
        when(environmentServiceImp.createEnvironment(any(), eq(userLogId))).thenReturn(new EnvironmentDetailsDTO(1L,"nome", "RP001"));
        when(locationServiceImp.createLocation(any())).thenReturn(new LocationDetalsDTO(1L, 1L, 1L));
        when(costCenterServiceImp.createCostCenter(any(), eq(userLogId))).thenReturn(new CostCenterDetailsDTO(1L, "NOME CENTER"));
        when(mainOwnerServiceImp.createMainOwner(any(), eq(userLogId))).thenReturn(new MainOwnerDetailsDTO(1L, 1L));
        when(equipmentServiceImp.createEquipment(any(), eq(userLogId))).thenReturn(new EquipmentDetailsDTO("IDEQUIP", "TESTE", 1230000456789456, "TIPO", "DESKTOP_TINK", "2025.Q2"));
        when(classesServiceImp.createClasses(any(), eq(userLogId))).thenReturn(new ClassesDetaiLDTO());
        when(responsibleServiceImp.createResposible(any(), eq(userLogId))).thenReturn(new ResponsibleDetailsDTO());
        when(equipmentResponsibleServiceImp.createEquipmentResponsible(any())).thenReturn(new EquipmentResponsibleDetailsDTO());

        // Act
        RegisterDetailsDTO result = registerService.register(registerData, userLogId);

        // Assert
        assertNotNull(result);
        // Adicione mais asserts conforme necessário
        verify(userRepository).findById(userLogId);
        // Verifique interações com outros mocks
    }

    @Test
    public void testRegisterUserNotFound() {
        // Arrange
        Long userLogId = 1L;
        RegisterCreateDTO registerData = new RegisterCreateDTO();

        when(userRepository.findById(userLogId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> {
            registerService.register(registerData, userLogId);
        });
    }
}
