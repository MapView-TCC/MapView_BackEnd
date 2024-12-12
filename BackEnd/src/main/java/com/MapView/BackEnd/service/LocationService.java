package com.MapView.BackEnd.service;

import com.MapView.BackEnd.dtos.Location.LocationCreateDTO;
import com.MapView.BackEnd.dtos.Location.LocationDetailsDTO;
import com.MapView.BackEnd.dtos.Location.LocationUpdateDTO;

import java.util.List;

public interface LocationService {

    LocationDetailsDTO getLocation(Long id_location);
    List<LocationDetailsDTO> getAllLocation();
    LocationDetailsDTO createLocation(LocationCreateDTO data);
    LocationDetailsDTO updateLocation(Long id_location, LocationUpdateDTO data);


}
