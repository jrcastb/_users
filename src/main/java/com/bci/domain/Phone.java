package com.bci.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Phone {
    private long number;
    private int cityCode;
    private String countryCode;
}
