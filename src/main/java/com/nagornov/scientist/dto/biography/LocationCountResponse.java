package com.nagornov.scientist.dto.biography;

import com.nagornov.scientist.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationCountResponse {

    private Location location;
    private Integer count;

}
