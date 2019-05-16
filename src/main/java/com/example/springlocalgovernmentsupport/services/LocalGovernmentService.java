package com.example.springlocalgovernmentsupport.services;

import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportedItemDto;
import com.example.springlocalgovernmentsupport.utils.CsvUtils;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class LocalGovernmentService {

    public String storeFile(InputStream inputStream) throws IOException {
        List<LocalGovernmentSupportedItemDto> supportedItemDtos =
                CsvUtils.read(LocalGovernmentSupportedItemDto.class, inputStream);

        return String.valueOf(supportedItemDtos.size());
    }
}
