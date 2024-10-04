package com.nagornov.scientist.dto.biography;

import com.nagornov.scientist.model.Science;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScienceCountResponse {

    private Science science;
    private Integer count;

}
