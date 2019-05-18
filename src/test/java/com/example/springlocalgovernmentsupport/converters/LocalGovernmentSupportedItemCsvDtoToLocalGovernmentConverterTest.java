package com.example.springlocalgovernmentsupport.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportedItemCsvDto;
import org.junit.jupiter.api.Test;

class LocalGovernmentSupportedItemCsvDtoToLocalGovernmentConverterTest {

    private final LocalGovernmentSupportedItemCsvDtoToLocalGovernmentConverter converter =
            new LocalGovernmentSupportedItemCsvDtoToLocalGovernmentConverter();

    @Test
    public void convertSecondaryPreserveToFromRateAndEndRate() {
        LocalGovernmentSupportedItemCsvDto dto =
                LocalGovernmentSupportedItemCsvDto.builder()
                        .limitAmount("추천금액 이내")
                        .secondaryPreserve("대출이자 전액")
                        .build();
        LocalGovernment allSupportLocalGovernment = converter.convert(dto);
        assertEquals(-1.0f, allSupportLocalGovernment.getSupportedItem().getFromRate());
        assertEquals(-1.0f, allSupportLocalGovernment.getSupportedItem().getEndRate());

        dto.setSecondaryPreserve("3%");
        LocalGovernment sameRateLocalGovernment = converter.convert(dto);
        assertEquals(3f, sameRateLocalGovernment.getSupportedItem().getFromRate());
        assertEquals(3f, sameRateLocalGovernment.getSupportedItem().getEndRate());

        dto.setSecondaryPreserve("4.15% ~ 5.15%");
        LocalGovernment differentRateLocalGovernment = converter.convert(dto);
        assertEquals(4.15f, differentRateLocalGovernment.getSupportedItem().getFromRate());
        assertEquals(5.15f, differentRateLocalGovernment.getSupportedItem().getEndRate());
    }
}
