package com.clothesdelivery.web.enums;

public enum OrderStatus {
    New(1),
    Processing(2),
    Processed(3),
    Sent(4),
    Canceled(5),
    Done(6);

    private final int value;

    private OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
