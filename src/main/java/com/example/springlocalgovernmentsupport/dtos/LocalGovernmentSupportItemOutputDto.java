package com.example.springlocalgovernmentsupport.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocalGovernmentSupportItemOutputDto {

    private String region;

    private String target;

    private String usage;

    private String limit;

    private String rate;

    private String institute;

    private String mgmt;

    private String reception;

    @Builder
    public LocalGovernmentSupportItemOutputDto(String region, String target, String usage, String limit, String rate,
                                               String institute, String mgmt, String reception) {
        this.region = region;
        this.target = target;
        this.usage = usage;
        this.limit = limit;
        this.rate = rate;
        this.institute = institute;
        this.mgmt = mgmt;
        this.reception = reception;
    }
}
