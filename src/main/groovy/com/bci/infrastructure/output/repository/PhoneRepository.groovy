package com.bci.infrastructure.output.repository

import com.bci.infrastructure.output.repository.entity.PhoneData
import com.bci.infrastructure.output.repository.entity.UserData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhoneRepository extends JpaRepository<PhoneData, Long> {

}