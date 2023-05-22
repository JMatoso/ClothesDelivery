package com.clothesdelivery.web.extensions;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component("extensionsBean")
public final class Extensions {
    private Extensions() {}

    public static @NotNull String toFormattedCurrency(BigDecimal value) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        return decimalFormat.format(value);
    }
}
