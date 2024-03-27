package com.sword.crud.domain.vo;

import cn.hutool.system.UserInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "用户VO实体")
@AllArgsConstructor(staticName = "of")
public class UserWithAddressVO {
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("详细信息")
    private String info;

    @ApiModelProperty("使用状态（1正常 2冻结）")
    private Integer status;

    @ApiModelProperty("账户余额")
    private Integer balance;

    @ApiModelProperty("用户的收获地址")
    private List<AddressVO> addresses;
}
