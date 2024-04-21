package com.sword.crud.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.github.pagehelper.PageHelper;
import com.sword.crud.domain.dto.PageDTO;
import com.sword.crud.domain.po.Address;
import com.sword.crud.domain.po.User;
import com.sword.crud.domain.query.PageQuery;
import com.sword.crud.domain.query.UserConditionQuery;
import com.sword.crud.domain.vo.AddressVO;
import com.sword.crud.domain.vo.UserVO;
import com.sword.crud.domain.vo.UserWithAddressVO;
import com.sword.crud.mapper.UserMapper;
import com.sword.crud.service.IUserService;
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
        QueryWrapper<User> q1 = new QueryWrapper<>();
        q1.eq("username", "北京");


        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("a.city", "北京")
                .in("u.id", List.of(1L, 2L, 4L));
        List<User> users = userMapper.querySelfDefined(wrapper);
        return BeanUtil.copyToList(users, UserVO.class);
    }


    @Override
    public PageDTO<UserVO> queryUsersPage(PageQuery query) {
        // 或者 Page<User> page = new Page<>(query.getPageNo(), query.getPageSize());
        Page<User> page = Page.of(query.getPageNo(), query.getPageSize());
        // 排序
        if (query.getSortBy() != null) {
            page.addOrder(new OrderItem(query.getSortBy(), query.getIsAsc()));
        }else{
            // 默认按照更新时间排序
            page.addOrder(new OrderItem("update_time", false));
        }
        // 去db查
        page(page); // IService方法内部就是 getBaseMapper().selectPage(page, emptyWrapper)
        // 获取结果并转化为DTO类型
        PageDTO<UserVO> pageDTO = PageDTO.of(page, UserVO.class);
        return pageDTO;
    }

    @Override
    public PageDTO<UserVO> queryUsersPageByCondition(UserConditionQuery query) {
        Page<User> page = new Page<>(query.getPageNo(), query.getPageSize());
        if (query.getSortBy() != null) {
            page.addOrder(new OrderItem(query.getSortBy(), query.getIsAsc()));
        }else{
            // 默认按照更新时间排序
            page.addOrder(new OrderItem("update_time", false));
        }

        // 这里就简单演示四种 wrapper 中的两种

        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda()
                .like(query.getKeyword() != null, User::getUsername, query.getKeyword())
                .eq(query.getStatus() != null, User::getStatus, query.getStatus())
                .ge(query.getMinBalance() != null, User::getBalance, query.getMinBalance())
                .le(query.getMaxBalance() != null, User::getBalance, query.getMaxBalance());
        page(page, wrapper);        // 就是 userMapper.selectPage(page, wrapper);


        lambdaQuery()
                .like(query.getKeyword() != null, User::getUsername, query.getKeyword())
                .eq(query.getStatus() != null, User::getStatus, query.getStatus())
                .ge(query.getMinBalance() != null, User::getBalance, query.getMinBalance())
                .le(query.getMaxBalance() != null, User::getBalance, query.getMaxBalance())
                .page(page);

        return PageDTO.of(page, UserVO.class);
    }

    @Override
    public PageDTO<UserVO> queryUsersByPH(UserConditionQuery query) {
        // 当前页，页大小
        PageHelper.startPage(query.getPageNo().intValue(), query.getPageSize().intValue());
        List<User> users = lambdaQuery()
                .like(query.getKeyword() != null, User::getUsername, query.getKeyword())
                .eq(query.getStatus() != null, User::getStatus, query.getStatus())
                .ge(query.getMinBalance() != null, User::getBalance, query.getMinBalance())
                .le(query.getMaxBalance() != null, User::getBalance, query.getMaxBalance())
                .list();
        // 转化正常查询集合→Page<T>：p,getResult()获取List<T>，p.getTotal()总条数, p.getPages()总页
        com.github.pagehelper.Page<User> p = (com.github.pagehelper.Page<User>) users;
        List<UserVO> vos = BeanUtil.copyToList(p.getResult(), UserVO.class);
        PageDTO<UserVO> pageDTO = new PageDTO<UserVO>(p.getTotal(), (long) p.getPages(), vos);
        return pageDTO;
    }
}
