package com.bci.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class SignUpResponse {
    private UUID id;
    private LocalDate created;
    private LocalDate lastLogin;
    private String token;
    private boolean isActive;
}
