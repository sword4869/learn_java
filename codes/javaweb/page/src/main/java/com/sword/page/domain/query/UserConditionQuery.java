package com.sword.page.domain.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserConditionQuery extends PageQuery {
    @ApiModelProperty("用户名关键字")
    private String keyword;
    @ApiModelProperty("用户状态：1-正常，2-冻结")
    private Integer status;
    @ApiModelProperty("余额最小值")
    private Integer minBalance;
    @ApiModelProperty("余额最大值")
    private Integer maxBalance;
}
// {
//     "pageNo": 1,
//     "pageSize": 5,
//     "sortBy": "balance",
//     "isAsc": false,
//     "name": "o",
//     "status": 1
// }