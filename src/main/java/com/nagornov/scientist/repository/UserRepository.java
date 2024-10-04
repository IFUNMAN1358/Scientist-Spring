package com.nagornov.scientist.repository;

import com.nagornov.scientist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("select u from User u where u.email = :email")
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query("select u from User u where u.userId = :userId")
    Optional<User> findUserByUserId(@Param("userId") UUID userId);

    @Transactional(readOnly = true)
    @Query("select u from User u left join fetch u.biography where u.userId = :userId")
    Optional<User> findUserByUserIdWithBiography(@Param("userId") UUID userId);

    @Transactional(readOnly = true)
    @Query("select u from User u left join fetch u.biography b where b.isConfirmed = false")
    List<User> getAllUsersWithBiographyByIsConfirmed();

    @Transactional(readOnly = true)
    @Query("select u from User u left join fetch u.biography where u.biography.isConfirmed = true")
    List<User> getAllWithBiography();

    @Transactional(readOnly = true)
    @Query("select u from User u left join fetch u.biography where u.biography.science.scienceId = :scienceId and u.biography.isConfirmed = true")
    List<User> getAllUsersWithBiographyByScienceId(@Param("scienceId") Integer scienceId);

    @Transactional(readOnly = true)
    @Query("select u from User u left join fetch u.biography where u.biography.location.locationId = :locationId and u.biography.isConfirmed = true")
    List<User> getAllUsersWithBiographyByLocationId(@Param("locationId") Integer locationId);

    @Transactional(readOnly = true)
    @Query("select u from User u left join fetch u.biography b " +
            "where (" +
            "(:part1 = '' or lower(u.lastName) like lower(concat('%', :part1, '%')) " +
            "or lower(u.firstName) like lower(concat('%', :part1, '%')) " +
            "or lower(u.patronymic) like lower(concat('%', :part1, '%')))" +
            "and (:part2 = '' or lower(u.lastName) like lower(concat('%', :part2, '%')) " +
            "or lower(u.firstName) like lower(concat('%', :part2, '%')) " +
            "or lower(u.patronymic) like lower(concat('%', :part2, '%')))" +
            "and (:part3 = '' or lower(u.lastName) like lower(concat('%', :part3, '%')) " +
            "or lower(u.firstName) like lower(concat('%', :part3, '%')) " +
            "or lower(u.patronymic) like lower(concat('%', :part3, '%')))" +
            ") and u.biography.isConfirmed = true")
    List<User> getAllUsersWithBiographyByFio(
            @Param("part1") String part1,
            @Param("part2") String part2,
            @Param("part3") String part3
    );

}
