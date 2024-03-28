package com.sword.enumtest;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus3 {
    NORMAL(1, "正常"),
    FROZEN(2, "冻结"),
    ;
    @EnumValue
    private final int value;
    @JsonValue
    private final String desc;

    UserStatus3(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
