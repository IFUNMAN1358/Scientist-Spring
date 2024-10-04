package com.nagornov.scientist.repository;

import com.nagornov.scientist.model.Science;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScienceRepository extends JpaRepository<Science, Integer> {

    @Query("select s from Science s where s.name = :name")
    Optional<Science> findScienceByName(@Param("name") String name);

    @Query("select s from Science s")
    List<Science> getAllScience();

}
