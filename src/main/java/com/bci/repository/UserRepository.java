package com.bci.repository;

import com.bci.repository.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserData, UUID> {

    Optional<UserData> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User SET token = :token, lastLogin = :lastLogin WHERE email = :email")
    void updateTokenAndLastLoginByEmail(@Param("token") String token, @Param("lastLogin") LocalDate lastLogin, @Param("email") String email);

}