package com.qf.mapper;

import com.qf.pojo.User;
import org.apache.ibatis.annotations.Param;

/**
 * 功能描述: <user用户表的描述>
 * @return:
 * @since: 1.0.0
 * @Author:
 * @Date:
 */

public interface UserMapper {
     //根据用户名查询数据条数
    Integer  findCountByUsername (@Param("username") String username);

    //根据用户名查询信息
    User findByUsername(@Param("username")String username);
    //添加用户信息
    Integer save(User user);
}
