package com.bci.exception;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ErrorModel {
    private Timestamp timestamp;
    private Integer codigo;
    private String detail;
}