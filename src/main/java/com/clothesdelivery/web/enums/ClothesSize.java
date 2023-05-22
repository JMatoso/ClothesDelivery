package com.clothesdelivery.web.enums;

public enum ClothesSize {
    XSS(1),
    XS(2),
    S(3),
    M(4),
    L(5),
    XL(6),
    XXL(7);

    private final int value;

    private ClothesSize(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
