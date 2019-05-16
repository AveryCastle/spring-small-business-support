package com.example.springlocalgovernmentsupport.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalGovernmentSupportedItemDto {

    @JsonProperty("구분")
    private Long id;

    @JsonProperty("지자체명(기관명)")
    private String localGovernmentName;

    @JsonProperty("지원대상")
    private String target;

    @JsonProperty("용도")
    private String usage;

    @JsonProperty("지원한도")
    private String limitAmount;

    @JsonProperty("이차보전")
    private String secondaryPreserve;

    @JsonProperty("추천기관")
    private String institute;

    @JsonProperty("관리점")
    private String mgmt;

    @JsonProperty("취급점")
    private String reception;
}
