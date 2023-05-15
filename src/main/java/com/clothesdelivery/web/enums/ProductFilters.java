package com.clothesdelivery.web.enums;

public enum ProductFilters {
    New(1),
    Hot(2),
    Best(3),
    Default(4);

    private final int value;

    ProductFilters(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getStyle() {
        return switch (getValue()) {
            case 1 -> "text-white bg-success";
            case 2 -> "text-white bg-danger";
            case 3 -> "text-black bg-warning";
            default -> "text-white bg-default";
        };
    }
}
