package com.sword.page.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sword.page.domain.dto.PageDTO;
import com.sword.page.domain.po.User;
import com.sword.page.domain.query.PageQuery;
import com.sword.page.domain.query.UserConditionQuery;
import com.sword.page.domain.vo.UserVO;
import com.sword.page.mapper.UserMapper;
import com.sword.page.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageDTO<UserVO> queryUsersByCondition(UserConditionQuery query) {
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda()
                .like(query.getKeyword() != null, User::getUsername, query.getKeyword())
                .eq(query.getStatus() != null, User::getStatus, query.getStatus())
                .ge(query.getMinBalance() != null, User::getBalance, query.getMinBalance())
                .le(query.getMaxBalance() != null, User::getBalance, query.getMaxBalance());
        Page<User> page = new Page<>(query.getPageNo(), query.getPageSize());
        userMapper.selectPage(page, wrapper);
        return PageDTO.of(page, UserVO.class);
    }

    @Override
    public PageDTO<UserVO> queryUsersPage(PageQuery query) {
        Page<User> page = Page.of(query.getPageNo(), query.getPageSize());
        // if (query.getSortBy() != null) {
        //     page.addOrder(new OrderItem(query.getSortBy(), query.getIsAsc()));
        // }else{
        //     // 默认按照更新时间排序
        //     page.addOrder(new OrderItem("update_time", false));
        // }
        // page(page);
        // List<User> users = page.getRecords();
        // PageDTO<UserVO> pageDTO = new PageDTO<>();
        // pageDTO.setTotal(page.getTotal());
        // pageDTO.setPages(page.getPages());
        // if(users == null || users.size() == 0){
        //     pageDTO.setList(Collections.emptyList());
        //     return pageDTO;
        // }
        // List<UserVO> userVOS = BeanUtil.copyToList(users, UserVO.class);
        // pageDTO.setList(userVOS);
        // return pageDTO;

        Page<User> page = null;
        if (query.getSortBy() != null) {
            page = query.toMpPage(query.getSortBy(), query.getIsAsc());
        } else {
            // 默认按照 User的更新时间排序
            page = query.toMpPage("update_time", false);
        }
        PageDTO<UserVO> pageDTO = PageDTO.of(page, UserVO.class);
        return pageDTO;
    }

}
