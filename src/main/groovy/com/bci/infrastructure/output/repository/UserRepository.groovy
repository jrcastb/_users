package com.bci.infrastructure.output.repository

import com.bci.infrastructure.output.repository.entity.UserData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

import java.time.LocalDate

@Repository
interface UserRepository extends JpaRepository<UserData, UUID> {

    Optional<UserData> findByEmail(String email);

    @Query("UPDATE User SET token = :token, lastLogin = :lastLogin WHERE email = :email")
    void updateTokenAndLastLoginByEmail(@Param("token") String token, @Param("lastLogin") LocalDate lastLogin, @Param("email") String email)

}