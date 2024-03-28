package com.sword.crud.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.sword.crud.domain.dto.UserConditionDTO;
import com.sword.crud.domain.dto.UserFormDTO;
import com.sword.crud.domain.po.User;
import com.sword.crud.domain.vo.UserVO;
import com.sword.crud.domain.vo.UserWithAddressVO;
import com.sword.crud.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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

    @ApiOperation("简单增 save")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userDTO){
        // 可以指定id，也可以不指定而自动生成
        User user = BeanUtil.copyProperties(userDTO, User.class);
        userService.save(user);
    }

    @ApiOperation("简单删除 removeById")
    @DeleteMapping("{id}")
    public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        userService.removeById(id);
    }

    @ApiOperation("简单查 getById")
    @GetMapping("{id}")
    public UserVO queryUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        User user = userService.getById(id);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @GetMapping
    @ApiOperation("简单查所有 listByIds")
    public List<UserVO> queryUserByIds(@RequestParam("ids") List<Long> ids){
        List<User> users = userService.listByIds(ids);
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @PutMapping
    @ApiOperation("简单改 ")
    public void updateUser(@RequestBody UserFormDTO userDTO){
        User user = BeanUtil.copyProperties(userDTO, User.class);
        userService.updateById(user);
    }

    /*********************************** lambdaQuery lambdaUpdate *******************************************/

    @ApiOperation("带条件查 lambdaQuery")
    @GetMapping("list")
    public List<UserVO> queryUsersByCondition(UserConditionDTO query){
        List<User> users = userService.queryUsersByCondition(query.getKeyword(), query.getStatus(), query.getMinBalance(), query.getMaxBalance());
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @PutMapping("{id}/deduction/{money}")
    @ApiOperation("扣减用户余额 lambdaUpdate")
    public void deductBalance(@PathVariable("id") Long id, @PathVariable("money")Integer money){
        userService.deductBalance(id, money);
    }

    /********************************* Batch *********************************************/

    @PostMapping("testAddUsers")
    @ApiOperation("批量测试")
    public void addUsers(){
        // jdbc配置：rewriteBatchedStatements=true
        List<User> list = new ArrayList<>(10000);
        Stream.iterate(0, i->i+1).limit(10000).forEach((i)->{
            list.add(new User().setUsername(UUID.randomUUID().toString()).setInfo("1").setBalance(0).setPhone("123").setPassword("123").setStatus(1));
        });
        userService.saveBatch(list);
    }

    /********************************* 循环依赖 *********************************************/

    @GetMapping("userWithAddress/{id}")
    @ApiOperation("循环依赖")
    public UserWithAddressVO queryUserWithAddressById(@PathVariable("id") Long userId){
        // 基于自定义service方法查询
        return userService.queryUserAndAddressById(userId);
    }

    /********************************* 自定义sql *******************************************/
    @GetMapping("self")
    @ApiOperation("自定义sql")
    public List<UserVO> querySelfDefined(){
        // 基于自定义service方法查询
        return userService.querySelfDefined();
    }
}
