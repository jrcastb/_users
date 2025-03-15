package com.bci.domain.dto;

import com.bci.domain.Phone;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class LoginResponse {
    private UUID id;
    private LocalDate created;
    private LocalDate lastLogin;
    private String token;
    private boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
}
