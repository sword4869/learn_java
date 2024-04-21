package com.sword.crud.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.sword.crud.domain.dto.PageDTO;
import com.sword.crud.domain.dto.UserConditionDTO;
import com.sword.crud.domain.dto.UserFormDTO;
import com.sword.crud.domain.po.User;
import com.sword.crud.domain.query.PageQuery;
import com.sword.crud.domain.query.UserConditionQuery;
import com.sword.crud.domain.vo.UserVO;
import com.sword.crud.domain.vo.UserWithAddressVO;
import com.sword.crud.mapper.UserMapper;
import com.sword.crud.service.IUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@ApiOperation("UserController")
public class UserController {

    @Autowired
    UserMapper userMapper;

    private final IUserService userService;

    /*********************************** 单个id *******************************************/

    @ApiOperation("简单增 save")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userDTO) {
        // 可以指定id，也可以不指定而mp自动生成
        User user = BeanUtil.copyProperties(userDTO, User.class);
        userService.save(user);
    }

    @ApiOperation("简单删除 removeById")
    @DeleteMapping("{id}")
    public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
        userService.removeById(id);
    }

    @ApiOperation("简单查 getById")
    @GetMapping("{id}")
    public UserVO queryUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
        User user = userService.getById(id);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @PutMapping
    @ApiOperation("简单改 ")
    public void updateUser(@RequestBody UserFormDTO userDTO) {
        /*
        只更新传递的字段；
        底层是`where id = ?`，所以id没有传递，默认值是null则不更新；id传递了，才更新。
        {
          "balance": 10,
        }
        20:06:01 DEBUG 37868 --- [nio-8080-exec-2] c.s.crud.mapper.UserMapper.updateById    : ==>  Preparing: UPDATE user SET balance=? WHERE id=?
        20:06:01 DEBUG 37868 --- [nio-8080-exec-2] c.s.crud.mapper.UserMapper.updateById    : ==> Parameters: 10(Integer), null
        20:06:01 DEBUG 37868 --- [nio-8080-exec-2] c.s.crud.mapper.UserMapper.updateById    : <==    Updates: 0

        {
          "balance": 10,
          "id": 2
        }
        20:06:14 DEBUG 37868 --- [nio-8080-exec-5] c.s.crud.mapper.UserMapper.updateById    : ==>  Preparing: UPDATE user SET balance=? WHERE id=?
        20:06:14 DEBUG 37868 --- [nio-8080-exec-5] c.s.crud.mapper.UserMapper.updateById    : ==> Parameters: 10(Integer), 2(Long)
        20:06:14 DEBUG 37868 --- [nio-8080-exec-5] c.s.crud.mapper.UserMapper.updateById    : <==    Updates: 1
         */
        User user = BeanUtil.copyProperties(userDTO, User.class);
        userService.updateById(user);
    }

    /*********************************** 多个 *******************************************/

    @GetMapping
    @ApiOperation("简单查所有 listByIds")
    public List<UserVO> queryUserByIds(@RequestParam("ids") List<Long> ids) {
        List<User> users = userService.listByIds(ids);
        return BeanUtil.copyToList(users, UserVO.class);
    }


    /*********************************** lambdaQuery lambdaUpdate *******************************************/

    @ApiOperation("带条件查 lambdaQuery")
    @GetMapping("list")
    public List<UserVO> queryUsersByCondition(UserConditionDTO query) {
        String keyword = query.getKeyword();
        Integer status = query.getStatus();
        Integer minBalance = query.getMinBalance();
        Integer maxBalance = query.getMaxBalance();

        List<User> users = null;

        /* 方式1：QueryWrapper ************************/
        // QueryWrapper的new泛型传参 + 可直接链式编程  + list(wrapper)
        QueryWrapper<User> wrapper2 = new QueryWrapper<User>()
                .like(StrUtil.isNotEmpty(keyword), "username", keyword)
                .eq(status != null, "status", status)
                .ge(minBalance != null, "balance", minBalance)
                .le(maxBalance != null, "balance", maxBalance);
        users = userService.list(wrapper2);

        // 可直接链式编程：意思是也可用分开链式编程
        QueryWrapper<User> wrapper2_show = new QueryWrapper<User>();
        wrapper2_show.like(StrUtil.isNotEmpty(keyword), "username", keyword)
                .eq(status != null, "status", status)
                .ge(minBalance != null, "balance", minBalance)
                .le(maxBalance != null, "balance", maxBalance);
        users = userService.list(wrapper2);

        // QueryWrapper的new泛型不传参 + 不可直接链式编程 + list(wrapper)
        QueryWrapper<User> wrapper3 = new QueryWrapper<>();     // 不能直接链式编程
        wrapper3.like(StrUtil.isNotEmpty(keyword), "username", keyword)
                .eq(status != null, "status", status)
                .ge(minBalance != null, "balance", minBalance)
                .le(maxBalance != null, "balance", maxBalance);
        users = userService.list(wrapper3);

        // LambdaQueryWrapper传
        LambdaQueryWrapper<User> wrapper5 = new LambdaQueryWrapper<User>()
                .like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance);
        users = userService.list(wrapper5);

        LambdaQueryWrapper<User> wrapper5_show = new LambdaQueryWrapper<User>();
        wrapper5_show
                .like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance);
        users = userService.list(wrapper5_show);

        // LambdaQueryWrapper不传
        LambdaQueryWrapper<User> wrapper6 = new LambdaQueryWrapper<>();
        wrapper6.like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance);
        users = userService.list(wrapper6);

        /* 方式2：new QueryWrapper<User>().lambda() 来获取 LambdaQueryWrapper ************************/
        // QueryWrapper的new泛型传参 + 转化为 LambdaQueryWrapper 的可直接链式编程  + list(wrapper)
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda()
                .like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance);
        users = userService.list(wrapper);

        LambdaQueryWrapper<User> wrapper_show = new QueryWrapper<User>().lambda();
        wrapper_show.like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance);
        users = userService.list(wrapper_show);

        // 不传参不行
        // LambdaQueryWrapper<User> wrapper4 = new QueryWrapper<>().lambda();   // 报错，让把User改成Object类型
        // LambdaQueryWrapper<Object> wrapper4 = new QueryWrapper<>().lambda();   // User::getUsername 报错， Non-static method cannot be referenced from a static context
        // wrapper4.like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
        //         .eq(status != null, User::getStatus, status)
        //         .ge(minBalance != null, User::getBalance, minBalance)
        //         .le(maxBalance != null, User::getBalance, maxBalance);
        // users = userService.list(wrapper4);


        /* 方式3：显示构造 QueryChainWrapper ************************/
        // 泛型可以不用传递，因为必须要传递类型、或者此类型的mapper对象作为构造参数
        // new QueryChainWrapper<User>(User.class) 也一样
        // new QueryChainWrapper<User>(userMapper) 也一样
        // new QueryChainWrapper<>(userMapper) 也一样
        // new QueryChainWrapper<>(User.class) 也一样
        users = new QueryChainWrapper<>(User.class)
                .like(StrUtil.isNotEmpty(keyword), "username", keyword)
                .eq(status != null, "status", status)
                .ge(minBalance != null, "balance", minBalance)
                .le(maxBalance != null, "balance", maxBalance)
                .list();

        QueryChainWrapper<User> chainWrapper_show = new QueryChainWrapper<>(User.class);
        users = chainWrapper_show.like(StrUtil.isNotEmpty(keyword), "username", keyword)
                .eq(status != null, "status", status)
                .ge(minBalance != null, "balance", minBalance)
                .le(maxBalance != null, "balance", maxBalance)
                .list();

        users = new LambdaQueryChainWrapper<>(User.class)
                .like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();

        LambdaQueryChainWrapper<User> wrapper7_show = new LambdaQueryChainWrapper<>(User.class);
        users = wrapper7_show.like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();

        /* 方式4：query() 来获得 QueryChainWrapper对象 ************************/
        users = userService.query()
                .like(StrUtil.isNotEmpty(keyword), "username", keyword)
                .eq(status != null, "status", status)
                .ge(minBalance != null, "balance", minBalance)
                .le(maxBalance != null, "balance", maxBalance)
                .list();

        QueryChainWrapper<User> chainWrapper = userService.query();
        users = chainWrapper.like(StrUtil.isNotEmpty(keyword), "username", keyword)
                .eq(status != null, "status", status)
                .ge(minBalance != null, "balance", minBalance)
                .le(maxBalance != null, "balance", maxBalance)
                .list();

        users = userService.lambdaQuery()
                .like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();

        LambdaQueryChainWrapper<User> chainWrapper2 = userService.lambdaQuery();
        users = chainWrapper2.like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @PutMapping("{id}/deduction/{money}")
    @ApiOperation("扣减用户余额 lambdaUpdate")
    public void deductBalance(@PathVariable("id") Long id, @PathVariable("money") Integer money) {
        userService.deductBalance(id, money);
    }

    /********************************* Page *********************************************/

    @GetMapping("mp")
    @ApiOperation("mp 简单查所有 + page")
    public PageDTO<UserVO> queryUsersPage(PageQuery pageQuery) {
        return userService.queryUsersPage(pageQuery);
    }

    @ApiOperation("mp 带条件查  + page")
    @GetMapping("mp/condition")
    public PageDTO<UserVO> queryUsersPageByCondition(UserConditionQuery query) {
        return userService.queryUsersPageByCondition(query);
    }

    @ApiOperation("pagehelper 带条件查  + page")
    @GetMapping("ph/condition")
    public PageDTO<UserVO> queryUsersByPH(UserConditionQuery query) {
        return userService.queryUsersByPH(query);
    }
    /********************************* Batch *********************************************/

    @PostMapping("testAddUsers")
    @ApiOperation("批量测试")
    public void addUsers() {
        // jdbc配置：rewriteBatchedStatements=true
        List<User> list = new ArrayList<>(10000);
        Stream.iterate(0, i -> i + 1).limit(10000).forEach((i) -> {
            list.add(new User().setUsername(UUID.randomUUID().toString()).setInfo("1").setBalance(0).setPhone("123").setPassword("123").setStatus(1));
        });
        userService.saveBatch(list);
    }

    /********************************* 循环依赖 *********************************************/

    @GetMapping("userWithAddress/{id}")
    @ApiOperation("循环依赖")
    public UserWithAddressVO queryUserWithAddressById(@PathVariable("id") Long userId) {
        // 基于自定义service方法查询
        return userService.queryUserAndAddressById(userId);
    }

    /********************************* 自定义sql *******************************************/
    @GetMapping("self")
    @ApiOperation("自定义sql")
    public List<UserVO> querySelfDefined() {
        // 基于自定义service方法查询
        return userService.querySelfDefined();
    }
}
