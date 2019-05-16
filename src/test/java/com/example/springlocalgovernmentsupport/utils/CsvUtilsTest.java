package com.example.springlocalgovernmentsupport.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportedItemDto;
import org.junit.jupiter.api.Test;

import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

class CsvUtilsTest {

    @Test
    public void convertInputStreamToObject() throws IOException {
        final String filePath = "data/서버개발_사전과제1_지자체협약지원정보_16년12월현재__최종 - 서버개발_사전과제1_지자체협약지원정보_16년12월현재__최종.csv";
        final InputStream fileInputStream = readInputStreamFromFile(filePath);

        List<LocalGovernmentSupportedItemDto> supportedItemDtos = CsvUtils.read(LocalGovernmentSupportedItemDto.class, fileInputStream);

        assertEquals(98, supportedItemDtos.size());
    }

    private InputStream readInputStreamFromFile(String filePath) throws IOException {
        final File resource = new ClassPathResource(filePath).getFile();
        final InputStream fileInputStream = new FileInputStream(resource);
        return fileInputStream;
    }
}
