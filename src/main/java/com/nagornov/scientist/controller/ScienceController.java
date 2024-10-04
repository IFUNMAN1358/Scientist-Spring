package com.nagornov.scientist.controller;

import com.nagornov.scientist.model.Science;
import com.nagornov.scientist.service.ScienceService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ScienceController {

    private final ScienceService scienceService;

    @GetMapping("/api/science/all")
    public ResponseEntity<?> getAllScience() {
        List<Science> scienceList = scienceService.getAllScience();
        return ResponseEntity.status(200).body(scienceList);
    }

}
