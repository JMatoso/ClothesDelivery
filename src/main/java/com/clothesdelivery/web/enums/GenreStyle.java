package com.clothesdelivery.web.enums;

public enum GenreStyle {
    Men(1),
    Woman(2),
    Unisex(3);

    private final int value;

    private GenreStyle(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
