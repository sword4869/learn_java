package com.sword.resultmap.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CardDis1 extends CardDis{
    @ApiModelProperty(value = "卡名")
    private String dis1Name;
}
