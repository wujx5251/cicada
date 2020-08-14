package com.cicada.component.kqpay.enums;


import com.cicada.core.enums.CertificationType;
import com.cicada.core.enums.Valuable;
import com.cicada.core.exception.business.CertificationTypeNotSupportException;

/**
 * 证件类型枚举
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum KqCertificationType implements Valuable {

    IDENTIFICATION("0", CertificationType.IDENTIFICATION),
    MILITARY_OFFICER("2", CertificationType.MILITARY_OFFICER),
    PASSPORT("1", CertificationType.PASSPORT),
    MTP_HMK("15", CertificationType.MTP_HMK),
    MTP_T("18", CertificationType.MTP_T),
    POLICE_OFFICER("9", CertificationType.POLICE_OFFICER),
    SOLDIER("3", CertificationType.SOLDIER),
    FOREIGNER_RESIDENCE("12", CertificationType.FOREIGNER_RESIDENCE);

    private String value;
    private CertificationType desc;

    KqCertificationType(String value, CertificationType desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String desc() {
        return desc.desc();
    }

    public static KqCertificationType from(CertificationType value) {

        for (KqCertificationType type : KqCertificationType.values()) {
            if (type.desc.equals(value)) {
                return type;
            }
        }

        throw new CertificationTypeNotSupportException(value.value());
    }
}
