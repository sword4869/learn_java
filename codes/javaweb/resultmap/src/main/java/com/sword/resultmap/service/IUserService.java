package com.sword.resultmap.service;

import com.sword.resultmap.domain.dto.CardDis;
import com.sword.resultmap.domain.dto.CardDis1;
import com.sword.resultmap.domain.dto.UserCardAssociation;
import com.sword.resultmap.domain.dto.UserCardCollection;
import com.sword.resultmap.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2024-05-30
 */
public interface IUserService extends IService<User> {
    List<UserCardAssociation> getUsersCardAssociation();

    List<UserCardCollection> getUsersCardCollection();

    List<User> getUsers();

    List<CardDis> getCardDiscriminator();
}
