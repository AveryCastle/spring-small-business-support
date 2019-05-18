package com.example.springlocalgovernmentsupport.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

class NumberUtilsTest {

    @Test
    public void convertStringToNumber() {
        assertEquals(8L, NumberUtils.convertStringToNumber("8억원 이내"));
        assertEquals(300L, NumberUtils.convertStringToNumber("300억원 이내"));
        assertEquals(Long.MIN_VALUE, NumberUtils.convertStringToNumber("추천금액 이내"));
    }

    @Test
    public void convertStringToFromRateAndEndRate() {
        assertEquals(3f, NumberUtils.convertStringToFromRateAndEndRate("3%").get(0).floatValue());

        List<Float> float1 = NumberUtils.convertStringToFromRateAndEndRate("3%~5%");
        assertEquals(3f, float1.get(0).floatValue());
        assertEquals(5f, float1.get(1).floatValue());

        List<Float> float2 = NumberUtils.convertStringToFromRateAndEndRate("2.5%~5.0%");
        assertEquals(2.5f, float2.get(0).floatValue());
        assertEquals(5.0f, float2.get(1).floatValue());

        List<Float> float3 = NumberUtils.convertStringToFromRateAndEndRate("1.5%~3.86%");
        assertEquals(1.5f, float3.get(0).floatValue());
        assertEquals(3.86f, float3.get(1).floatValue());

        assertEquals(0, NumberUtils.convertStringToFromRateAndEndRate("대출이자 전액").size());
    }
}
