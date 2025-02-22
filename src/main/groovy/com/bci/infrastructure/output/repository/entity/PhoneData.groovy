package com.bci.infrastructure.output.repository.entity

import groovy.transform.Canonical
import groovy.transform.builder.Builder
import lombok.AccessLevel
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import lombok.experimental.FieldDefaults

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Phone")
@Table(name = "phones")
@Canonical
@FieldDefaults(level = AccessLevel.PRIVATE)
class PhoneData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long number;
    @Column(name = "city_code")
    Integer cityCode
    @Column(name = "country_code")
    String countryCode;
    @ManyToOne
    @JoinColumn(name = "userId")
    UserData user
}
