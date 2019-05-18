package com.example.springlocalgovernmentsupport.services;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportedItemCsvDto;
import com.example.springlocalgovernmentsupport.enums.UsageCode;
import com.example.springlocalgovernmentsupport.repositories.LocalGovernmentRepository;
import com.example.springlocalgovernmentsupport.repositories.SupportedItemRepository;
import com.example.springlocalgovernmentsupport.utils.CsvUtils;
import com.example.springlocalgovernmentsupport.utils.NumberUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class LocalGovernmentService {

    @Autowired
    private LocalGovernmentRepository localGovernmentRepository;

    @Autowired
    private SupportedItemRepository supportedItemRepository;

    @Transactional
    public int storeFile(InputStream inputStream) throws IOException {
        List<LocalGovernmentSupportedItemCsvDto> supportedItemDtos =
                CsvUtils.read(LocalGovernmentSupportedItemCsvDto.class, inputStream);

        final int[] uploadFileSize = {0};
        supportedItemDtos.stream().forEach(supportedItemDto -> {
            LocalGovernment foundOne = localGovernmentRepository.findByName(supportedItemDto.getLocalGovernmentName());
            if (Objects.isNull(foundOne)) {
                LocalGovernment localGovernment = LocalGovernment.create(supportedItemDto);
                localGovernmentRepository.save(localGovernment);

                List<Float> floats = setRate(supportedItemDto.getSecondaryPreserve());

                SupportedItem supportedItem = SupportedItem.builder()
                        .localGovernment(localGovernment)
                        .target(supportedItemDto.getTarget())
                        .usage(UsageCode.findByValue(supportedItemDto.getUsage()))
                        .limitAmount(NumberUtils.convertStringToNumber(supportedItemDto.getLimitAmount()))
                        .fromRate(floats.get(0))
                        .endRate(floats.get(1))
                        .institute(supportedItemDto.getInstitute())
                        .mgmt(supportedItemDto.getMgmt())
                        .reception(supportedItemDto.getReception())
                        .build();

                supportedItemRepository.save(supportedItem);
                uploadFileSize[0]++;
            }
        });

        return uploadFileSize[0];
    }

    private List<Float> setRate(String secondaryPreserve) {
        Float fromRate = Float.valueOf(-99.99f);
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
