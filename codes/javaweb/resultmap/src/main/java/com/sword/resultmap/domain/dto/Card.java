package com.sword.resultmap.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Card {
    @ApiModelProperty(value = "卡id")
    private Long cardId;

    @ApiModelProperty(value = "卡名")
    private String name;
}
