package com.clothesdelivery.web.enums;

public enum Role {
    Customer(1),
    Admin(2);

    private final int value;

    private Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
