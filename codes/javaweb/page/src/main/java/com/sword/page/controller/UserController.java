package com.sword.page.controller;


import com.sword.page.domain.dto.PageDTO;
import com.sword.page.domain.query.PageQuery;
import com.sword.page.domain.query.UserConditionQuery;
import com.sword.page.domain.vo.UserVO;
import com.sword.page.service.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author
 * @since 2024-03-27
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping
    @ApiOperation("简单查所有 + page")
    public PageDTO<UserVO> queryUserByIds(PageQuery pageQuery) {
        return userService.queryUsersPage(pageQuery);
    }

    @ApiOperation("带条件查  + page")
    @GetMapping("list")
    public PageDTO<UserVO> queryUsersByCondition(UserConditionQuery query) {
        return userService.queryUsersByCondition(query);
    }

}
