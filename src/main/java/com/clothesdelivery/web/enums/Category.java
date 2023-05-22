package com.clothesdelivery.web.enums;

public enum Category {
    Tops(1),
    Bottoms(2),
    Shoes(3),
    Accessories(4);

    private final int value;

    private Category(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
