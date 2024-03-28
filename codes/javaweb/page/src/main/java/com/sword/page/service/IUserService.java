package com.sword.page.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sword.page.domain.dto.PageDTO;
import com.sword.page.domain.po.User;
import com.sword.page.domain.query.PageQuery;
import com.sword.page.domain.query.UserConditionQuery;
import com.sword.page.domain.vo.UserVO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 
 * @since 2024-03-27
 */
public interface IUserService extends IService<User> {
    PageDTO<UserVO> queryUsersByCondition(UserConditionQuery query);

    PageDTO<UserVO> queryUsersPage(PageQuery pageQuery);

    PageDTO<UserVO> queryUsersByPH(UserConditionQuery query);
}
