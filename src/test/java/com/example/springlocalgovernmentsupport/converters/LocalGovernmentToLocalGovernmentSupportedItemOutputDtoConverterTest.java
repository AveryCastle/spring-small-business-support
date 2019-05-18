package com.example.springlocalgovernmentsupport.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportItemOutputDto;
import com.example.springlocalgovernmentsupport.enums.UsageCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocalGovernmentToLocalGovernmentSupportedItemOutputDtoConverterTest {

    private final LocalGovernmentToLocalGovernmentSupportedItemOutputDtoConverter converter =
            new LocalGovernmentToLocalGovernmentSupportedItemOutputDtoConverter();

    private LocalGovernment gangneung;

    @BeforeEach
    public void setUp() {
        gangneung = new LocalGovernment("강릉시");
    }

    @Test
    public void convertStringLimit() {
        SupportedItem supportedItem = SupportedItem.builder()
                .target("강릉시 소재 중소기업으로서 강릉시장이 추천한 자")
                .usage(UsageCode.findByValue("운전"))
                .limitAmount(-1L)
                .build();
        gangneung.setSupportedItem(supportedItem);

        LocalGovernmentSupportItemOutputDto underRecommendedAmountSupportedItemDto = converter.convert(gangneung);
        assertEquals(LocalGovernmentToLocalGovernmentSupportedItemOutputDtoConverter.UNDER_RECOMMENDED_AMOUNT, underRecommendedAmountSupportedItemDto.getLimit());

        supportedItem.setLimitAmount(8L);
        LocalGovernmentSupportItemOutputDto supportedItemDto = converter.convert(gangneung);
        assertEquals("8억원 이내", supportedItemDto.getLimit());
    }

    @Test
    public void convertStringRate() {
        SupportedItem supportedItem = SupportedItem.builder()
                .target("강릉시 소재 중소기업으로서 강릉시장이 추천한 자")
                .usage(UsageCode.findByValue("운전"))
                .fromRate(-1.00f)
                .build();
        gangneung.setSupportedItem(supportedItem);

        LocalGovernmentSupportItemOutputDto secondaryRateDto = converter.convert(gangneung);
        assertEquals(LocalGovernmentToLocalGovernmentSupportedItemOutputDtoConverter.SECONDARY_RATE_ALL, secondaryRateDto.getRate());

        supportedItem.setFromRate(3.0f);
        supportedItem.setEndRate(3.0f);
        LocalGovernmentSupportItemOutputDto sameRateDto = converter.convert(gangneung);
        assertEquals("3.0%", sameRateDto.getRate());

        supportedItem.setFromRate(3.0f);
        supportedItem.setEndRate(5.15f);
        LocalGovernmentSupportItemOutputDto diffrentRateDto = converter.convert(gangneung);
        assertEquals("3.0% ~ 5.15%", diffrentRateDto.getRate());
    }
}
