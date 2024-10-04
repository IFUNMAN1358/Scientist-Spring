package com.nagornov.scientist.dto.user;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BiographyFormRequest {

    private MultipartFile image;

    private String degree;
    private String rank;
    private String scienceName;

    private String country;
    private String region;

    private String content;
    private String publications;

}
