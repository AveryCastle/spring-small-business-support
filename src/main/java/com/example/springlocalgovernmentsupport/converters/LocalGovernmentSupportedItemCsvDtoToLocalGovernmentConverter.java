package com.example.springlocalgovernmentsupport.converters;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportedItemCsvDto;
import com.example.springlocalgovernmentsupport.enums.UsageCode;
import com.example.springlocalgovernmentsupport.utils.NumberUtils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LocalGovernmentSupportedItemCsvDtoToLocalGovernmentConverter implements Converter<LocalGovernmentSupportedItemCsvDto, LocalGovernment> {

    @Override
    public LocalGovernment convert(LocalGovernmentSupportedItemCsvDto localGovernmentSupportedItemCsvDto) {
        LocalGovernment localGovernment = LocalGovernment.create(localGovernmentSupportedItemCsvDto);

        List<Float> floats = setRate(localGovernmentSupportedItemCsvDto.getSecondaryPreserve());

        SupportedItem supportedItem = SupportedItem.builder()
                .localGovernment(localGovernment)
                .target(localGovernmentSupportedItemCsvDto.getTarget())
                .usage(UsageCode.findByValue(localGovernmentSupportedItemCsvDto.getUsage()))
                .limitAmount(NumberUtils.convertStringToNumber(localGovernmentSupportedItemCsvDto.getLimitAmount()))
                .fromRate(floats.get(0))
                .endRate(floats.get(1))
                .institute(localGovernmentSupportedItemCsvDto.getInstitute())
                .mgmt(localGovernmentSupportedItemCsvDto.getMgmt())
                .reception(localGovernmentSupportedItemCsvDto.getReception())
                .build();

        return localGovernment;
    }

    private List<Float> setRate(String secondaryPreserve) {
        Float fromRate = Float.valueOf(-1.00f);
        Float endRate = fromRate;

        List<Float> rates = NumberUtils.convertStringToFromRateAndEndRate(secondaryPreserve);

        if (rates.size() == 1) {
            fromRate = rates.get(0);
            endRate = fromRate;
        } else if (rates.size() >= 2) {
            fromRate = rates.get(0);
            endRate = rates.get(1);
        }

        return Arrays.asList(fromRate, endRate);
    }
}
