package com.nagornov.scientist.repository;

import com.nagornov.scientist.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query("select l from Location l where l.country = :country and (:region is null or l.region = :region)")
    Optional<Location> findLocationByCountryAndRegionIsNull(@Param("country") String country, @Param("region") String region);

    @Query("select l from Location l")
    List<Location> getAllLocation();

}
