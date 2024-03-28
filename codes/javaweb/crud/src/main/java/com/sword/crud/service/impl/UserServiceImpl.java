package com.sword.crud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.sword.crud.domain.po.Address;
import com.sword.crud.domain.po.User;
import com.sword.crud.domain.vo.AddressVO;
import com.sword.crud.domain.vo.UserVO;
import com.sword.crud.domain.vo.UserWithAddressVO;
import com.sword.crud.mapper.UserMapper;
import com.sword.crud.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-03-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> queryUsersByCondition(String keyword, Integer status, Integer minBalance, Integer maxBalance) {
        // LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda()
        //         .like(username != null, User::getUsername, username)
        //         .eq(status != null, User::getStatus, status)
        //         .ge(minBalance != null, User::getBalance, minBalance)
        //         .le(maxBalance != null, User::getBalance, maxBalance);
        // List<User> users = list(wrapper);

        return lambdaQuery()
                .like(StrUtil.isNotEmpty(keyword), User::getUsername, keyword)
                .eq(status!=null, User::getStatus, status)
                .ge(minBalance!=null, User::getBalance, minBalance)
                .le(maxBalance!=null, User::getBalance, maxBalance)
                .list();
    }

    @Override
    @Transactional
    public void deductBalance(Long id, Integer money) {
        // 1.查询用户
        User user = getById(id);
        // 2.校验用户状态
        if (user == null || user.getStatus() == 2) {
            throw new RuntimeException("用户状态异常！");
        }
        // 3.校验余额是否充足
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足！");
        }
        // 4.扣减余额 update tb_user set balance = balance - ?
        int remainBalance = user.getBalance() - money;
        lambdaUpdate()
                .set(User::getBalance, remainBalance)
                .set(remainBalance == 0, User::getStatus, 2) // 动态判断，是否更新status
                .eq(User::getId, id)
                .eq(User::getBalance, user.getBalance()) // 乐观锁
                .update();
    }

    @Override
    public UserWithAddressVO queryUserAndAddressById(Long userId) {
        // 1.查询用户
        User user = getById(userId);
        if (user == null) {
            return null;
        }
        // 2.查询收货地址
        List<Address> addresses = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, userId)
                .list();
        // 3.处理vo
        UserWithAddressVO userWithAddressVO = BeanUtil.copyProperties(user, UserWithAddressVO.class);
        userWithAddressVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
        return userWithAddressVO;
    }

    @Override
    public List<UserVO> querySelfDefined() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("a.city", "北京")
                .in("u.id", List.of(1L, 2L, 4L));
        List<User> users = userMapper.querySelfDefined(wrapper);
        return BeanUtil.copyToList(users, UserVO.class);
    }
}
