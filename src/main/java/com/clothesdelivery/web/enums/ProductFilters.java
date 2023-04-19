package com.clothesdelivery.web.enums;

public enum ProductFilters {
    New(1),
    Hot(2),
    Best(3);

    private final int value;

    private ProductFilters(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
