package com.cicada.core.enums;


import com.cicada.core.exception.business.CertificationTypeNotSupportException;
import com.dotin.common.utils.StringUtils;

/**
 * 证件类型枚举,银联为蓝本
 *
 * @author: WUJXIAO
 * @create: 2018-12-13 08:58
 * @version: 1.0
 */
public enum CertificationType implements Valuable {

    IDENTIFICATION("01", "身份证"),
    MILITARY_OFFICER("02", "军官证"),
    PASSPORT("03", "护照"),
    MTP_HMK("04", "回乡证"),
    MTP_T("05", "台胞证"),
    POLICE_OFFICER("06", "警官证"),
    SOLDIER("07", "士兵证"),
    FOREIGNER_RESIDENCE("08", "外国人居留证"),
    OTHER("99", "其他证件");

    private String value;
    private String desc;

    CertificationType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String desc() {
        return desc;
    }

    public static CertificationType from(String value) {

        if (StringUtils.isEmpty(value)) {
            return null;
        }

        for (CertificationType type : CertificationType.values()) {
            if (type.value().equals(value)) {
                return type;
            }
        }

        throw new CertificationTypeNotSupportException(value);
    }
}
