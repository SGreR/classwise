package com.classwise.classwisegradesservice.enums;

import lombok.Getter;

@Getter
public enum TestNumber {
    FIRST(1),
    SECOND(2);

    private final int value;

    TestNumber(int value) {
        this.value = value;
    }

}
