package com.sword.resultmap.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CardDis {
    @ApiModelProperty(value = "卡id")
    private Long cardId;

    @ApiModelProperty(value = "卡类型")
    private Long type;
}
