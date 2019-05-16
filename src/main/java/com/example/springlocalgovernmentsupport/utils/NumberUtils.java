package com.example.springlocalgovernmentsupport.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtils {

    public static long convertStringToNumber(String string) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(string);

        if (matcher.find()) {
            return Long.valueOf(matcher.group(0));
        }
        return 0;
    }

    public static List<Float> convertStringToFromRateAndEndRate(String string) {
        List<Float> result = new LinkedList<>();
        Pattern pattern = Pattern.compile("(\\d+(\\.)?)+");
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            result.add(Float.valueOf(matcher.group()));
        }

        return result;
    }
}
