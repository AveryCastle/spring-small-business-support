package com.example.springlocalgovernmentsupport.enums;

import java.util.Arrays;

public enum UsageCode {

    OPERATION("운전"),
    FACILITIES("시설"),
    OPERATION_FACILITIES("운전 및 시설"),
    EMPTY("미존재");

    private String value;

    UsageCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static UsageCode findByValue(String value) {
        return Arrays.stream(UsageCode.values())
                .filter(usageCode -> usageCode.getValue().equals(value))
                .findAny()
                .orElse(EMPTY);
    }
}
