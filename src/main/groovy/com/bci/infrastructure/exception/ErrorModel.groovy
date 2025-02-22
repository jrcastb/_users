package com.bci.infrastructure.exception

import groovy.transform.Canonical
import groovy.transform.builder.Builder

@Canonical
@Builder
import java.sql.Timestamp


class ErrorModel {
    private Timestamp timestamp;
    private Integer codigo;
    private String detail;

    Timestamp getTimestamp() {
        return timestamp;
    }

    void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    Integer getCodigo() {
        return codigo;
    }

    void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    String getDetail() {
        return detail;
    }

    void setDetail(String detail) {
        this.detail = detail;
    }
}


