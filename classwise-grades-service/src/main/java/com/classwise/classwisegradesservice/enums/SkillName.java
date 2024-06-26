package com.classwise.classwisegradesservice.enums;

import lombok.Getter;

@Getter
public enum SkillName {
    READING(1),
    WRITING(2),
    LISTENING(3),
    GRAMMAR(4);

    private final int value;

    SkillName(int value) {
        this.value = value;
    }
}
