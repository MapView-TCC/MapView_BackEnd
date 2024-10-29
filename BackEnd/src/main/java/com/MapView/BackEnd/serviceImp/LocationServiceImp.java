package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.infra.Exception.LocationAlreadyExistsException;
import com.MapView.BackEnd.infra.Exception.OperativeFalseException;
import com.MapView.BackEnd.repository.EnvironmentRepository;
import com.MapView.BackEnd.repository.LocationRepository;
import com.MapView.BackEnd.repository.PostRepository;
import com.MapView.BackEnd.service.LocationService;
import com.MapView.BackEnd.dtos.Location.LocationCreateDTO;
import com.MapView.BackEnd.dtos.Location.LocationDetalsDTO;
import com.MapView.BackEnd.dtos.Location.LocationUpdateDTO;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImp implements LocationService {

    private final LocationRepository locationRepository;
    private final PostRepository postRepository;
    private final EnvironmentRepository environmentRepository;

    public LocationServiceImp(LocationRepository locationRepository, PostRepository postRepository, EnvironmentRepository environmentRepository) {
        this.locationRepository = locationRepository;
        this.postRepository = postRepository;
        this.environmentRepository = environmentRepository;
    }

    @Override
    public LocationDetalsDTO getLocation(Long id_location) {
        var loc = locationRepository.findById(id_location).orElseThrow(() -> new NotFoundException("Location id not found"));
        return new LocationDetalsDTO(loc);
    }

    @Override
    public List<LocationDetalsDTO> getAllLocation() {
        return this.locationRepository.findAll().stream().map(LocationDetalsDTO::new).toList();
    }

    @Override
    public LocationDetalsDTO createLocation(LocationCreateDTO data) {
        try {
            var post = postRepository.findById(data.post()).orElseThrow(() -> new NotFoundException("Post Id not Found"));
            if (!post.isOperative()) {
                throw new OperativeFalseException("The inactive post cannot be accessed.");
            }

            var environment = environmentRepository.findById(data.environment()).orElseThrow(() -> new NotFoundException("Environment Id Not Found"));
            if (!environment.isOperative()) {
                throw new OperativeFalseException("The inactive environment cannot be accessed.");
            }

            var location = new Location(post, environment);
            locationRepository.save(location);
            System.out.println("Post: Post ");
            return new LocationDetalsDTO(location);


        }catch (DataIntegrityViolationException e ){
            throw new LocationAlreadyExistsException("The location with these values has been created");
        }

    }

    @Override
    public LocationDetalsDTO updateLocation(Long id_location, LocationUpdateDTO data) {
        var location = locationRepository.findById(id_location).orElseThrow(() -> new NotFoundException("Location Id Not Found"));

        var post = postRepository.findById(data.post()).orElseThrow(() -> new NotFoundException("Post Id not Found"));
        if(!post.isOperative()){
            throw new OperativeFalseException("The inactive post cannot be accessed.");
        }
        var environment = environmentRepository.findById(data.environment()).orElseThrow(() -> new NotFoundException("Environment Id Not Found"));
        if(!environment.isOperative()){
            throw new OperativeFalseException("The inactive environment cannot be accessed.");
        }

        if(data.environment() != null){
            location.setEnvironment(environment);
        }
        if(data.post() != null){
            location.setPost(post);
        }
        locationRepository.save(location);
        return new LocationDetalsDTO(location);
    }
}
