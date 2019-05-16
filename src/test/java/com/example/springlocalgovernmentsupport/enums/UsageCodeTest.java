package com.example.springlocalgovernmentsupport.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UsageCodeTest {

    @Test
    public void findByValue() {
        UsageCode usage = UsageCode.findByValue("시설");
        assertEquals(UsageCode.FACILITIES.getValue(), usage.getValue());

        UsageCode notFound = UsageCode.findByValue("NOT-FOUND");
        assertEquals(UsageCode.EMPTY.getValue(), notFound.getValue());
    }
}
