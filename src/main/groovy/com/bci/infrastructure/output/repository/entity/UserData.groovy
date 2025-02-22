package com.bci.infrastructure.output.repository.entity

import groovy.transform.builder.Builder
import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import lombok.experimental.FieldDefaults
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import java.time.LocalDate

@Data
@Builder
@DynamicUpdate
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    UUID userId;
    String name;
    String email;
    String password;
    @OneToMany(mappedBy = "user")
    List<PhoneData> phones;
    LocalDate created;
    @Column(name = "last_login")
    LocalDate lastLogin;
    String token;
    @Column(name = "is_active")
    boolean isActive;
}
