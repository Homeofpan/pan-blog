package com.pan.service;

import com.pan.pojo.Type;

import java.util.List;

public interface TypeService {

    //返回null表示type存在,不为null则成功
    Type saveType(Type type);

    //返回null表示id不存在,不为null则成功
    Type getTypeById(Integer id);

    //返回list,按名字查找
    List<Type> getTypesByName(Type type);

    //查找所有分页
    List<Type> typeList();

    //返回null则表示失败,否则成功
    Type updateType(Type type);

    //默认成功
    void deleteType(Integer id);

    //类型对应的博客数量+1,默认成功
    void increaseBlogsNum(Integer typeid);

    //类型对应的博客数量-1,默认成功
    void decreaseBlogNum(Integer typeid);

    //以类型的对应的博客数量进行降序查询
    List<Type> listTypeByNumDesc(Type type);
}
