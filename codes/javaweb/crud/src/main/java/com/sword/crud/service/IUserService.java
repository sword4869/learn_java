package com.sword.crud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sword.crud.domain.dto.PageDTO;
import com.sword.crud.domain.po.User;
import com.sword.crud.domain.query.PageQuery;
import com.sword.crud.domain.query.UserConditionQuery;
import com.sword.crud.domain.vo.UserVO;
import com.sword.crud.domain.vo.UserWithAddressVO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-27
 */
public interface IUserService extends IService<User> {

    void deductBalance(Long id, Integer money);

    UserWithAddressVO queryUserAndAddressById(Long userId);

    List<UserVO> querySelfDefined();

    PageDTO<UserVO> queryUsersPage(PageQuery pageQuery);

    PageDTO<UserVO> queryUsersPageByCondition(UserConditionQuery query);

    PageDTO<UserVO> queryUsersByPH(UserConditionQuery query);
}
