package com.sword.crud.service;

import com.sword.crud.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
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
    List<User> queryUsersByCondition(String keyword, Integer status, Integer minBalance, Integer maxBalance);

    void deductBalance(Long id, Integer money);

    UserWithAddressVO queryUserAndAddressById(Long userId);

    List<UserVO> querySelfDefined();
}
