package com.nagornov.scientist.controller;

import com.nagornov.scientist.dto.biography.LocationCountResponse;
import com.nagornov.scientist.dto.biography.ScienceCountResponse;
import com.nagornov.scientist.dto.user.BiographyFormRequest;
import com.nagornov.scientist.dto.user.UserBiographyResponse;
import com.nagornov.scientist.model.Biography;
import com.nagornov.scientist.security.JwtAuthentication;
import com.nagornov.scientist.service.BiographyService;
import com.nagornov.scientist.service.JwtService;
import com.nagornov.scientist.util.ImageUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BiographyController {

    private final BiographyService biographyService;
    private final JwtService jwtService;

    //
    // ALL
    //

    @GetMapping("/api/biography/image/{imageName}")
    public ResponseEntity<?> getBiographyImage(@PathVariable("imageName") String imageName) {
        try {
            Resource image = ImageUtil.getImage(imageName, "biography_img");
            return ResponseEntity.status(200)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Image not found");
        }
    }

    @GetMapping("/api/biography/{userId}")
    public ResponseEntity<?> getUserBiographyByUserId(@PathVariable("userId") String userId) {
        UserBiographyResponse userBiography = biographyService.getUserBiographyByUserId(userId);
        return ResponseEntity.status(200).body(userBiography);
    }

    @GetMapping("/api/biography/all/science")
    public ResponseEntity<?> getCountByScience() {
        List<ScienceCountResponse> responseList = biographyService.getAllByScience();
        return ResponseEntity.status(200).body(responseList);
    }

    @GetMapping("/api/biography/all/location")
    public ResponseEntity<?> getCountByLocation() {
        List<LocationCountResponse> responseList = biographyService.getAllByLocation();
        return ResponseEntity.status(200).body(responseList);
    }

    @GetMapping("/api/biography/all/science/{scienceId}")
    public ResponseEntity<?> getAllByScienceId(@PathVariable("scienceId") Integer scienceId) {
        List<UserBiographyResponse> responseList = biographyService.getAllByScienceId(scienceId);
        return ResponseEntity.status(200).body(responseList);
    }

    @GetMapping("/api/biography/all/location/{locationId}")
    public ResponseEntity<?> getAllByLocationId(@PathVariable("locationId") Integer locationId) {
        List<UserBiographyResponse> responseList = biographyService.getAllByLocationId(locationId);
        return ResponseEntity.status(200).body(responseList);
    }

    @GetMapping("/api/biography/all/search/{fio}")
    public ResponseEntity<?> searchAllUserBiographyByFIO(@PathVariable("fio") String fio) {
        List<UserBiographyResponse> responseList = biographyService.searchAllByFio(fio);
        return ResponseEntity.status(200).body(responseList);
    }

    //
    // USER
    //

    @GetMapping("/api/biography")
    public ResponseEntity<?> getUserBiography() {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        UserBiographyResponse response =  biographyService.getUserBiography(authInfo);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/api/biography")
    public ResponseEntity<?> createBiography(@ModelAttribute BiographyFormRequest request) {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        Biography biography = biographyService.createBiography(authInfo, request);
        return ResponseEntity.status(201).body(biography);
    }

    @PutMapping("/api/biography")
    public ResponseEntity<?> updateBiography(@ModelAttribute BiographyFormRequest request) {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        Biography biography = biographyService.updateBiography(authInfo, request);
        return ResponseEntity.status(201).body(biography);
    }

    @DeleteMapping("/api/biography")
    public ResponseEntity<?> deleteBiographyByToken() {
        JwtAuthentication authInfo = jwtService.getAuthInfo();
        biographyService.deleteBiography(authInfo, null);
        return ResponseEntity.status(204).body("Biography deleted successfully");
    }

    //
    // MANAGER
    //

    @GetMapping("/api/biography/all/unconfirmed")
    public ResponseEntity<?> getAllByIsConfirmed() {
        jwtService.getAuthInfo();
        List<UserBiographyResponse> responseList = biographyService.getAllByIsConfirmed();
        return ResponseEntity.status(200).body(responseList);
    }

    @PatchMapping("/api/biography/{userId}/confirm")
    public ResponseEntity<?> setBiographyConfirm(@PathVariable("userId") String userId) {
        jwtService.getAuthInfo();
        biographyService.setBiographyConfirm(userId);
        return ResponseEntity.status(204).body("Biography successfully confirmed");
    }

    @DeleteMapping("/api/biography/{userId}")
    public ResponseEntity<?> deleteBiographyByUserId(@PathVariable("userId") String userId) {
        jwtService.getAuthInfo();
        biographyService.deleteBiography(null, userId);
        return ResponseEntity.status(204).body("Biography deleted successfully");
    }

}
