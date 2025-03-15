package com.bci.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class User {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
    private LocalDate created;
    private LocalDate lastLogin;
    private String token;
    private boolean isActive;
}
