package com.bci.infrastructure.output.repository.entity

import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.experimental.FieldDefaults
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Getter
@Setter
@Builder(toBuilder = true)
@DynamicUpdate
@DynamicInsert
@Entity(name = "User")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserData {
    @Id
    UUID id;
    String name;
    String email;
    String password;
    @OneToMany(mappedBy = "PhoneData")
    List<PhoneData> phones;
    LocalDate created;
    @Column(name = "last_login")
    LocalDate lastLogin;
    String token;
    @Column(name = "is_active")
    boolean isActive;
}
