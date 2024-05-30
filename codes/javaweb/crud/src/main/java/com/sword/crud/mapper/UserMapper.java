package com.sword.crud.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sword.crud.domain.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sword.crud.domain.vo.UserWithAddressVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2024-03-27
 */
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT u.* FROM user u INNER JOIN address a ON u.id = a.user_id ${ew.customSqlSegment}")
    List<User> querySelfDefined(@Param("ew") QueryWrapper<User> wrapper); // @Param(Constants.WRAPPER)

    List<User> querySelfDefined2(@Param("ew") QueryWrapper<User> wrapper); // @Param(Constants.WRAPPER)
}
