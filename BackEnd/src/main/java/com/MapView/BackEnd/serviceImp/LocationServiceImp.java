package com.MapView.BackEnd.serviceImp;

import com.MapView.BackEnd.infra.Exception.LocationAlreadyExistsException;
import com.MapView.BackEnd.infra.Exception.OperativeFalseException;
import com.MapView.BackEnd.repository.EnvironmentRepository;
import com.MapView.BackEnd.repository.LocationRepository;
import com.MapView.BackEnd.repository.PostRepository;
import com.MapView.BackEnd.service.LocationService;
import com.MapView.BackEnd.dtos.Location.LocationCreateDTO;
import com.MapView.BackEnd.dtos.Location.LocationDetailsDTO;
import com.MapView.BackEnd.dtos.Location.LocationUpdateDTO;
import com.MapView.BackEnd.entities.Location;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
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
    public LocationDetailsDTO getLocation(Long id_location) {
        var loc = locationRepository.findById(id_location)
                .orElseThrow(() -> new NotFoundException("Location with id " + id_location + " not found."));
        return new LocationDetailsDTO(loc);
    }

    @Override
    public List<LocationDetailsDTO> getAllLocation() {
        return this.locationRepository.findAll().stream().map(LocationDetailsDTO::new).toList();
    }

    @Override
    public LocationDetailsDTO createLocation(LocationCreateDTO data) {
        try {
            var post = postRepository.findById(data.post())
                    .orElseThrow(() -> new NotFoundException("Post with id " + data.post() + " not found."));
            if (!post.isOperative()) {
                throw new OperativeFalseException("The post with id " + data.post() + " is inactive and cannot be accessed.");
            }

            var environment = environmentRepository.findById(data.environment())
                    .orElseThrow(() -> new NotFoundException("Environment with id " + data.environment() + " not found."));
            if (!environment.isOperative()) {
                throw new OperativeFalseException("The environment with id " + data.environment() + " is inactive and cannot be accessed.");
            }

            var location = new Location(post, environment);
            location = locationRepository.save(location);
            System.out.println("Post: " + post);
            return new LocationDetailsDTO(location);


        }catch (DataIntegrityViolationException e ){
            throw new LocationAlreadyExistsException("A location with the specified post and environment already exists.");
        }

    }

    @Override
    public LocationDetailsDTO updateLocation(Long id_location, LocationUpdateDTO data) {
        var location = locationRepository.findById(id_location)
                .orElseThrow(() -> new NotFoundException("Location with id " + id_location + " not found."));

        var post = postRepository.findById(data.post())
                .orElseThrow(() -> new NotFoundException("Post with id " + data.post() + " not found."));

        if(!post.isOperative()){
            throw new OperativeFalseException("The inactive post cannot be accessed.");
        }

        var environment = environmentRepository.findById(data.environment())
                .orElseThrow(() -> new NotFoundException("Environment with id " + data.environment() + " not found."));

        if(!environment.isOperative()){
            throw new OperativeFalseException("The environment with id " + data.environment() + " is inactive and cannot be accessed.");
        }

        location.setEnvironment(environment);
        location.setPost(post);

        locationRepository.save(location);
        return new LocationDetailsDTO(location);
    }
}
