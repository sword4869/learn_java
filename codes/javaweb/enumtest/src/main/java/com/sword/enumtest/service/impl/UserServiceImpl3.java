package com.sword.enumtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sword.enumtest.User1;
import com.sword.enumtest.User3;
import com.sword.enumtest.mapper.UserMapper1;
import com.sword.enumtest.mapper.UserMapper3;
import com.sword.enumtest.service.IUserService1;
import com.sword.enumtest.service.IUserService3;
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
public class UserServiceImpl3 extends ServiceImpl<UserMapper3, User3> implements IUserService3 {

}
