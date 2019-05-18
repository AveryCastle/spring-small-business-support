package com.example.springlocalgovernmentsupport.converters;

import com.example.springlocalgovernmentsupport.domains.LocalGovernment;
import com.example.springlocalgovernmentsupport.domains.SupportedItem;
import com.example.springlocalgovernmentsupport.dtos.LocalGovernmentSupportItemOutputDto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LocalGovernmentToLocalGovernmentSupportedItemOutputDtoConverter implements Converter<LocalGovernment, LocalGovernmentSupportItemOutputDto> {

    public static final String SECONDARY_RATE_ALL = "대출이자 전액";
    public static final String UNDER_RECOMMENDED_AMOUNT = "추천금액 이내";

    @Override
    public LocalGovernmentSupportItemOutputDto convert(LocalGovernment localGovernment) {
        if (Objects.isNull(localGovernment)) {
            return LocalGovernmentSupportItemOutputDto.builder().build();
        }

        SupportedItem supportedItem = localGovernment.getSupportedItem();

        return LocalGovernmentSupportItemOutputDto.builder()
                .region(localGovernment.getName())
                .target(supportedItem.getTarget())
                .usage(supportedItem.getUsage().getValue())
                .limit(convertLimitAmountToString(supportedItem.getLimitAmount()))
                .rate(convertRateToConditionRate(supportedItem.getFromRate(), supportedItem.getEndRate()))
                .institute(supportedItem.getInstitute())
                .mgmt(supportedItem.getMgmt())
                .reception(supportedItem.getReception())
                .build();
    }

    private String convertLimitAmountToString(long limitAmount) {
        if (limitAmount == -1L) {
            return UNDER_RECOMMENDED_AMOUNT;
        }
        return limitAmount + "억원 이내";
    }

    private String convertRateToConditionRate(float fromRate, float endRate) {
        if (fromRate == -1.00f) {
            return SECONDARY_RATE_ALL;
        }

        if (fromRate == endRate) {
            return fromRate + "%";
        }

        return fromRate + "% ~ " + endRate + "%";
    }
}
