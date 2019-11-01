package com.pan.service.impl;

import com.pan.dao.UserMapper;
import com.pan.pojo.User;
import com.pan.pojo.UserExample;
import com.pan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public User findUserByUsername(String username) {
        //设置查询条件
        UserExample example = new UserExample();
        UserExample.Criteria criterira = example.createCriteria();
        criterira.andUsernameEqualTo(username);
        //执行查询
        List<User> users = userMapper.selectByExample(example);
        if (users == null){
            return null;
        }
        if(users.size() == 0){
            return null;
        }
        return users.get(0);
    }

    @Transactional
    @Override
    public User findUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
