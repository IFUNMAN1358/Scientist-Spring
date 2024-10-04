package com.nagornov.scientist.service;

import com.nagornov.scientist.model.Location;
import com.nagornov.scientist.repository.LocationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public List<Location> getAllLocation() {
        return locationRepository.getAllLocation();
    }

}
