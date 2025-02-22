package com.bci.infrastructure.exception

import com.bci.infrastructure.exception.messages.BusinessErrorMessage
import lombok.AccessLevel
import lombok.experimental.FieldDefaults


@FieldDefaults(level = AccessLevel.PRIVATE)
class BusinessException extends RuntimeException {

    final BusinessErrorMessage businessErrorMessage;


    BusinessException(BusinessErrorMessage businessErrorMessage){
        this.businessErrorMessage = businessErrorMessage;
    }
}
