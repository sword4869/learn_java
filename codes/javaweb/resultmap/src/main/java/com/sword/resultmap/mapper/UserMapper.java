package com.sword.resultmap.mapper;

import com.sword.resultmap.domain.dto.CardDis;
import com.sword.resultmap.domain.dto.CardDis1;
import com.sword.resultmap.domain.dto.UserCardAssociation;
import com.sword.resultmap.domain.dto.UserCardCollection;
import com.sword.resultmap.domain.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-05-30
 */
public interface UserMapper extends BaseMapper<User> {
    List<UserCardAssociation> getUsersCardAssociation();

    List<UserCardCollection> getUsersCardCollection();

    List<User> getUsers();

    List<CardDis> getCardDiscriminator();
}
