package com.clothesdelivery.web.extensions;

import java.text.DecimalFormat;

public final class Extensions {
    private Extensions() {}

    public static String toFormattedCurrency(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        return decimalFormat.format(value);
    }
}
