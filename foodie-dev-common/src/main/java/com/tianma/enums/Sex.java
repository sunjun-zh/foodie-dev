package com.tianma.enums;

public enum Sex {

    wonam(0, "女"),
    man(1, "男"),
    secret(2, "保密");


    public final Integer type;
    public final String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }


}
