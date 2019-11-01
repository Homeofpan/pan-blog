package com.pan.service;

import com.pan.pojo.User;

public interface UserService {

    //根据name查询user,若返回null表示不存在
    User findUserByUsername(String username);
    //根据Id查询user,若返回null表示不存在
    User findUserById(Integer id);
}
