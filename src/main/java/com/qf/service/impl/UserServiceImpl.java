package com.qf.service.impl;

import com.qf.mapper.UserMapper;
import com.qf.pojo.User;
import com.qf.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @program: ssm
 * @description:
 * @author: 狗十三
 * @create: 2019-07-15 14:37
 **/
@Service
public class UserServiceImpl implements UserService {
   @Autowired
   private UserMapper userMapper;


    @Override
    public Integer checkUsername(String username) {
        //健壮性代码
        if (!StringUtils.isEmpty(username)){
            username = username.trim();
        }
        Integer count = userMapper.findCountByUsername(username);
        return count;
    }

    @Override
    public Integer register(User user) {
//      对密码进行加密
        String newPwd= new Md5Hash(user.getPassword(),null,1024).toString();
        user.setPassword(newPwd);
        //调用mapper保存数据
        Integer count = userMapper.save(user);

        //返回信息
        return count;
    }

    @Override
    public User login(String username, String password) {
      //调用mapper根据用户名查询用户信息
        User user = userMapper.findByUsername(username);
        //判断查询出的用户信息是否为null.
        if (user != null){
            //2.1 如果不为null -> 判断密码.
            String newPwd = new Md5Hash(password, null, 1024).toString();
            //如果密码正确,返回查询到的user对象
            if (user.getPassword().equals(newPwd)){
               //登录成功,返回user对象
                return user;
            }
        }
        //统一返回null；
        return null;
    }
}
