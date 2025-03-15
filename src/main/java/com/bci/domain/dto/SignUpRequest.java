package com.bci.domain.dto;

import com.bci.domain.Phone;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
    private List<Phone> phones;
}
