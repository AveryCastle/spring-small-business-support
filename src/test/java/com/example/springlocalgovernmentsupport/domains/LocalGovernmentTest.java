package com.example.springlocalgovernmentsupport.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springlocalgovernmentsupport.enums.UsageCode;
import org.junit.jupiter.api.Test;

class LocalGovernmentTest {

    @Test
    public void saveBidirectional() {
        // Given.
        SupportedItem supportedItem = SupportedItem.builder()
                .target("강릉시 소재 중소기업으로서 강릉시장이 추천한 자")
                .usage(UsageCode.OPERATION)
                .limitAmount(-1L)
                .fromRate(3.00f)
                .institute("강릉시")
                .mgmt("강릉지점")
                .reception("강릉시 소재 영업점")
                .build();

        // When.
        LocalGovernment gangneung = new LocalGovernment("강릉시", supportedItem);

        // Then.
        assertEquals("강릉시", supportedItem.getLocalGovernment().getName());
    }
}
