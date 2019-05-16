package com.example.springlocalgovernmentsupport.domains;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springlocalgovernmentsupport.enums.UsageCode;
import org.junit.jupiter.api.Test;

class SupportedItemTest {

    @Test
    public void saveBidirectional() {
        // Given.
        LocalGovernment gangneung = new LocalGovernment("강릉시");

        // When.
        SupportedItem supportedItem = SupportedItem.builder()
                .localGovernment(gangneung)
                .target("강릉시 소재 중소기업으로서 강릉시장이 추천한 자")
                .usage(UsageCode.OPERATION)
                .limitAmount(-1L)
                .fromRate(3.00f)
                .institute("강릉시")
                .mgmt("강릉지점")
                .reception("강릉시 소재 영업점")
                .build();
        // Then.
        assertEquals("강릉시 소재 중소기업으로서 강릉시장이 추천한 자", gangneung.getSupportedItem().getTarget());
    }
}
