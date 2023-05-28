package com.clothesdelivery.web.enums;

public enum OrderStatus {
    Pendent(1),
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

    public String getStyle() {
        return switch (getValue()) {
            case 1 -> "text-white bg-secondary";
            case 2 -> "text-black bg-info";
            case 3 -> "text-white bg-primary";
            case 4 -> "text-black bg-warning";
            case 5 -> "text-white bg-dark";
            case 6 -> "text-white bg-success";
            default -> "text-white bg-default";
        };
    }
}
