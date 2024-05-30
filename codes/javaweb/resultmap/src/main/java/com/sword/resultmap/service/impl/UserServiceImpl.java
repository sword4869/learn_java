package com.sword.resultmap.service.impl;

import com.sword.resultmap.domain.dto.CardDis;
import com.sword.resultmap.domain.dto.CardDis1;
import com.sword.resultmap.domain.dto.UserCardAssociation;
import com.sword.resultmap.domain.dto.UserCardCollection;
import com.sword.resultmap.domain.po.User;
import com.sword.resultmap.mapper.UserMapper;
import com.sword.resultmap.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2024-05-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserCardAssociation> getUsersCardAssociation(){
        return userMapper.getUsersCardAssociation();
    }

    @Override
    public List<UserCardCollection> getUsersCardCollection() {
        return userMapper.getUsersCardCollection();
    }

    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public List<CardDis> getCardDiscriminator() {
        return userMapper.getCardDiscriminator();
    }
}
