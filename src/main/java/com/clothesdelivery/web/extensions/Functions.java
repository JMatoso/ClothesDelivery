package com.clothesdelivery.web.extensions;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Functions {
    public static double calculatePercentage(double value, double total) {
        return (value / total) * 100;
    }

    public static double calculatePercentage(double value, double total, boolean rounded) {
        var result = calculatePercentage(value, total);
        return rounded ? formatNumber(result) : result;
    }

    public static double formatNumber(double number) {
        var bigDecimal = new BigDecimal(number);
        return bigDecimal.setScale(2, RoundingMode.CEILING).doubleValue();
    }
}
