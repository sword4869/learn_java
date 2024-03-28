package com.sword.enumtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sword.enumtest.User1;
import com.sword.enumtest.User2;
import com.sword.enumtest.mapper.UserMapper1;
import com.sword.enumtest.mapper.UserMapper2;
import com.sword.enumtest.service.IUserService1;
import com.sword.enumtest.service.IUserService2;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author 
 * @since 2024-03-27
 */
@Service
public class UserServiceImpl2 extends ServiceImpl<UserMapper2, User2> implements IUserService2 {

}
