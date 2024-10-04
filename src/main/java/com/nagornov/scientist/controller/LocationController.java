package com.nagornov.scientist.controller;

import com.nagornov.scientist.model.Location;
import com.nagornov.scientist.model.Science;
import com.nagornov.scientist.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/api/location/all")
    public ResponseEntity<?> getAllLocation() {
        List<Location> locationList = locationService.getAllLocation();
        return ResponseEntity.status(200).body(locationList);
    }

}
