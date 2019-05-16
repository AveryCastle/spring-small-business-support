package com.example.springlocalgovernmentsupport.enums;

public enum UsageCode {

    OPERATION("001", "운전"), OPERATION_FACILITIES("002", "운전 및 시설");

    private String code;

    private String value;

    UsageCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
