package com.bci.infrastructure.output.repository.entity

import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.experimental.FieldDefaults

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Getter
@Setter
@Builder(toBuilder = true)
@Entity(name = "Phone")
@Table(name = "phones")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
class PhoneData {
    @Id
    Long id;
    Long number;
    @Column(name = "city_code")
    Integer cityCode;
    @Column(name = "country_code")
    String countryCode;
}
