package com.nagornov.scientist.repository;

import com.nagornov.scientist.model.Biography;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiographyRepository extends JpaRepository<Biography, Integer> {

}
