package com.bci.infrastructure.output.repository.entity

import groovy.transform.Canonical
import lombok.AccessLevel
import lombok.experimental.FieldDefaults

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity(name = "Phone")
@Table(name = "phones")
@Canonical
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
