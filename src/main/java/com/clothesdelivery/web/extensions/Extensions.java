package com.clothesdelivery.web.extensions;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Component("extensionsBean")
public final class Extensions {
    @Value("${clothes.whatsapp.number}")
    private String shoppingWhatsappNumber;

    @Value("${clothes.email.address}")
    private String shoppingEmailAddress;

    public String getShoppingEmailAddress() {
        return shoppingEmailAddress;
    }

    public String getShoppingWhatsappNumber() {
        return shoppingWhatsappNumber;
    }

    public static @NotNull String toFormattedCurrency(BigDecimal value) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        return decimalFormat.format(value);
    }
}
