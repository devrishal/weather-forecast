package com.wfs.service.helpers;

public enum Operator {
    GREATER_THAN(">"),
    EQUALS("equals"),
    LESS_THAN("<"),
    GREATER_THAN_EQUALS_TO(">="),
    LESS_THAN_EQUALS_TO("<=");
    private final String name;

    Operator(String s) {
        name = s;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
