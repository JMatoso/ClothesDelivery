package com.clothesdelivery.web.enums;

public enum Role {
    ROLE_CUSTOMER(1),
    ROLE_ADMIN(2);

    private final int value;

    private Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
