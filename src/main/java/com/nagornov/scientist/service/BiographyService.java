package com.nagornov.scientist.service;

import com.nagornov.scientist.dto.biography.LocationCountResponse;
import com.nagornov.scientist.dto.biography.ScienceCountResponse;
import com.nagornov.scientist.dto.user.BiographyFormRequest;
import com.nagornov.scientist.dto.user.UserBiographyResponse;
import com.nagornov.scientist.exception.biography.LocationNotFoundException;
import com.nagornov.scientist.exception.biography.ScienceNotFoundException;
import com.nagornov.scientist.exception.biography.UserBiographyNotFoundException;
import com.nagornov.scientist.exception.user.UserBiographyAlreadyExistsException;
import com.nagornov.scientist.exception.user.UserNotFoundException;
import com.nagornov.scientist.mapper.BiographyMapper;
import com.nagornov.scientist.mapper.UserMapper;
import com.nagornov.scientist.model.Biography;
import com.nagornov.scientist.model.Location;
import com.nagornov.scientist.model.Science;
import com.nagornov.scientist.model.User;
import com.nagornov.scientist.repository.BiographyRepository;
import com.nagornov.scientist.repository.LocationRepository;
import com.nagornov.scientist.repository.ScienceRepository;
import com.nagornov.scientist.repository.UserRepository;
import com.nagornov.scientist.security.JwtAuthentication;
import com.nagornov.scientist.util.ImageUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class BiographyService {

    private final UserRepository userRepository;
    private final BiographyRepository biographyRepository;
    private final ScienceRepository scienceRepository;
    private final LocationRepository locationRepository;
    private final BiographyMapper biographyMapper;
    private final UserMapper userMapper;


    public UserBiographyResponse getUserBiography(JwtAuthentication authInfo) {
        User user = userRepository.findUserByUserIdWithBiography(UUID.fromString(authInfo.getUserId()))
                .orElseThrow(() -> new UserNotFoundException("User not found", 500));
        return userMapper.toUserBiographyResponse(user);
    }

    public List<UserBiographyResponse> getAllByIsConfirmed() {
        return userRepository.getAllUsersWithBiographyByIsConfirmed()
                .stream().map(userMapper::toUserBiographyResponse).toList();
    }

    public List<ScienceCountResponse> getAllByScience() {
        List<ScienceCountResponse> response = new ArrayList<>();

        List<Science> scienceList = scienceRepository.getAllScience();
        List<User> userList = userRepository.getAllWithBiography();

        for (Science science : scienceList) {
            Integer count = 0;
            for (User user : userList) {
                if (user.getBiography() != null && user.getBiography().getScience() == science) {
                    count++;
                }
            }
            response.add(new ScienceCountResponse(science, count));
        }
        return response;
    }

    public List<LocationCountResponse> getAllByLocation() {
        List<LocationCountResponse> response = new ArrayList<>();

        List<Location> locationList = locationRepository.getAllLocation();
        List<User> userList = userRepository.getAllWithBiography();

        for (Location location : locationList) {
            Integer count = 0;
            for (User user : userList) {
                if (user.getBiography() != null && user.getBiography().getLocation().equals(location)) {
                    count++;
                }
            }
            response.add(new LocationCountResponse(location, count));
        }
        return response;
    }

    public UserBiographyResponse getUserBiographyByUserId(String userId) {
        User user = userRepository.findUserByUserIdWithBiography(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found", 400));
        return userMapper.toUserBiographyResponse(user);
    }

    public List<UserBiographyResponse> getAllByScienceId(Integer scienceId) {
        return userRepository.getAllUsersWithBiographyByScienceId(scienceId).stream()
                .map(userMapper::toUserBiographyResponse).toList();
    }

    public List<UserBiographyResponse> getAllByLocationId(Integer locationId) {
        return userRepository.getAllUsersWithBiographyByLocationId(locationId).stream()
                .map(userMapper::toUserBiographyResponse).toList();
    }

    public List<UserBiographyResponse> searchAllByFio(String fio) {
        String[] parts = fio.split(" ");
        String part1 = parts.length > 0 ? parts[0] : "";
        String part2 = parts.length > 1 ? parts[1] : "";
        String part3 = parts.length > 2 ? parts[2] : "";

        return userRepository.getAllUsersWithBiographyByFio(part1, part2, part3).stream()
                .map(userMapper::toUserBiographyResponse).toList();
    }

    @Transactional
    public Biography createBiography(JwtAuthentication authInfo, BiographyFormRequest request) {
        User user = userRepository.findUserByUserIdWithBiography(UUID.fromString(authInfo.getUserId()))
                .orElseThrow(() -> new UserNotFoundException("User not found", 400));

        if (user.getBiography() != null) {
            throw new UserBiographyAlreadyExistsException("User biography already exists", 500);
        }

        Science science = scienceRepository.findScienceByName(request.getScienceName())
                .orElseThrow(() -> new ScienceNotFoundException("Science not found", 400));

        Location location = locationRepository.findLocationByCountryAndRegionIsNull(
                request.getCountry(), request.getRegion().equals("null") ? null : request.getRegion()
        ).orElseThrow(() -> new LocationNotFoundException("Location not found", 400));

        String imageName = null;
        if (request.getImage() != null) {
            imageName = ImageUtil.saveImage(request.getImage(), "biography_img");
        }

        Biography creatingBiography = biographyMapper.toBiography(request);
        creatingBiography.setImageName(imageName);
        creatingBiography.setScience(science);
        creatingBiography.setLocation(location);

        Biography biography = biographyRepository.save(creatingBiography);

        user.setBiography(biography);
        userRepository.save(user);
        return biography;
    }

    @Transactional
    public Biography updateBiography(JwtAuthentication authInfo, BiographyFormRequest request) {
        User user = userRepository.findUserByUserIdWithBiography(UUID.fromString(authInfo.getUserId()))
                .orElseThrow(() -> new UserNotFoundException("User not found", 400));

        if (user.getBiography() == null) {
            throw new UserBiographyNotFoundException("User biography not found", 500);
        }

        Science science = scienceRepository.findScienceByName(request.getScienceName())
                .orElseThrow(() -> new ScienceNotFoundException("Science not found", 400));

        Location location = locationRepository.findLocationByCountryAndRegionIsNull(
                request.getCountry(), request.getRegion().equals("null") ? null : request.getRegion()
        ).orElseThrow(() -> new LocationNotFoundException("Location not found", 400));

        String imageName = user.getBiography().getImageName();
        if (request.getImage() != null) {
            if (user.getBiography().getImageName() != null) {
                ImageUtil.deleteImage(user.getBiography().getImageName(), "biography_img");
            }
            imageName = ImageUtil.saveImage(request.getImage(), "biography_img");
        }

        Biography creatingBiography = biographyMapper.toBiography(request);
        creatingBiography.setBiographyId(user.getBiography().getBiographyId());
        creatingBiography.setImageName(imageName);
        creatingBiography.setScience(science);
        creatingBiography.setLocation(location);

        Biography biography = biographyRepository.save(creatingBiography);

        user.setBiography(biography);
        userRepository.save(user);
        return biography;
    }

    @Transactional
    public void setBiographyConfirm(String userId) {
        User user = userRepository.findUserByUserIdWithBiography(UUID.fromString(userId)).orElseThrow(
                () -> new UserNotFoundException("User not found", 400)
        );

        if (user.getBiography() == null) {
            throw new UserBiographyNotFoundException("User biography not found", 500);
        }

        user.getBiography().setIsConfirmed(true);
        biographyRepository.save(user.getBiography());
    }

    @Transactional
    public void deleteBiography(JwtAuthentication authInfo, String userId) {

        Optional<User> existingUser = Optional.empty();

        if (authInfo != null) {
            existingUser = userRepository.findUserByUserIdWithBiography(UUID.fromString(authInfo.getUserId()));
        } else if (userId != null) {
            existingUser = userRepository.findUserByUserIdWithBiography(UUID.fromString(userId));
        }

        if (existingUser.isEmpty()) {
            throw new UserNotFoundException("User not found", 400);
        }
        User user = existingUser.get();

        if (user.getBiography() == null) {
            throw new UserBiographyNotFoundException("User biography not found", 500);
        }

        if (user.getBiography().getImageName() != null) {
            ImageUtil.deleteImage(user.getBiography().getImageName(), "biography_img");
        }
        biographyRepository.delete(user.getBiography());

        user.setBiography(null);
        userRepository.save(user);
    }

}