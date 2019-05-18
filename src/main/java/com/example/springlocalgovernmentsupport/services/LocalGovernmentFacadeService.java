package com.example.springlocalgovernmentsupport.services;

import com.example.springlocalgovernmentsupport.converters.LocalGovernmentSupportedItemCsvDtoToLocalGovernmentConverter;
import com.example.springlocalgovernmentsupport.converters.LocalGovernmentToLocalGovernmentSupportedItemOutputDtoConverter;
import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportItemOutputDto;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportedItemCsvDto;
import com.example.springlocalgovernmentsupport.utils.CsvUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalGovernmentFacadeService {

    @Autowired
    private LocalGovernmentService localGovernmentService;

    @Autowired
    private LocalGovernmentToLocalGovernmentSupportedItemOutputDtoConverter dtoConverter;

    @Autowired
    private LocalGovernmentSupportedItemCsvDtoToLocalGovernmentConverter beanConverter;

    public List<LocalGovernmentSupportItemOutputDto> findAll() {
        List<LocalGovernment> localGovernments = localGovernmentService.findAll();

        List<LocalGovernmentSupportItemOutputDto> result =
                localGovernments.stream().map(localGovernment -> dtoConverter.convert(localGovernment))
                        .collect(Collectors.toList());

        return result;
    }

    public int storeFile(InputStream inputStream) throws IOException {
        List<LocalGovernmentSupportedItemCsvDto> supportedItemDtos =
                CsvUtils.read(LocalGovernmentSupportedItemCsvDto.class, inputStream);

        List<LocalGovernment> localGovernments =
                supportedItemDtos.stream().map(supportedItemDto -> beanConverter.convert(supportedItemDto))
                        .collect(Collectors.toList());

        return localGovernmentService.storeFile(localGovernments);
    }

    public LocalGovernmentSupportItemOutputDto findByName(String name) {
        LocalGovernment localGovernment = localGovernmentService.findByName(name);

        return dtoConverter.convert(localGovernment);
    }
}
