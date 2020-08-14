package com.cicada.core.exception.business;

import com.cicada.core.exception.basic.PayException;

public class CertificationTypeNotSupportException extends PayException {

    private static final long serialVersionUID = 6957839097849598241L;

    public CertificationTypeNotSupportException(String v) {
        super("Certification type ：'"+ v +"' not supported!");
    }

    @Override
    public String getCode() {
        return code + "." + "INVALID_CERT_TYPE";
    }
}
