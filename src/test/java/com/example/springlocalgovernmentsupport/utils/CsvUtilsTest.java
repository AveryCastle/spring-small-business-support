package com.example.springlocalgovernmentsupport.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springlocalgovernmentsupport.commons.BaseTest;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportedItemCsvDto;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class CsvUtilsTest extends BaseTest {

    @Test
    public void convertInputStreamToObject() throws IOException {
        final String filePath = "data/서버개발_사전과제1_지자체협약지원정보_16년12월현재__최종 - 서버개발_사전과제1_지자체협약지원정보_16년12월현재__최종.csv";
        final InputStream fileInputStream = readInputStreamFromFile(filePath);

        List<LocalGovernmentSupportedItemCsvDto> supportedItemDtos = CsvUtils.read(LocalGovernmentSupportedItemCsvDto.class, fileInputStream);

        assertEquals(98, supportedItemDtos.size());
    }
}
