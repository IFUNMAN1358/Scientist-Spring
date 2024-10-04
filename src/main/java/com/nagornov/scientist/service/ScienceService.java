package com.nagornov.scientist.service;

import com.nagornov.scientist.model.Science;
import com.nagornov.scientist.repository.ScienceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ScienceService {

    private final ScienceRepository scienceRepository;

    public List<Science> getAllScience() {
        return scienceRepository.getAllScience();
    }

}
