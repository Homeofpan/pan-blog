package com.pan.service;

import com.pan.pojo.Tag;

import java.util.List;

public interface TagService {

    //根据id查找,需要自行判断是否为null,该方法内不实现
    Tag getTagById(Integer id);

    //根据name查找出list直接返回,需要自己去判断是否有元素
    List<Tag> getTagByName(String name);

    //保存标签,返回null表示失败
    Tag saveTag(Tag tag);

    //分页查找
    List<Tag> listTag();

    //根据主键更新内容,返回null表示失败
    Tag updateTag(Tag tag);

    //默认成功
    void deleteTagById(Integer id);

    //类型对应的博客数量+1,默认成功
    void increaseBlogsNum(Integer tagid);

    //类型对应的博客数量-1,默认成功
    void decreaseBlogNum(Integer tagid);
    //按照数量进行排序返回查询列表
    List<Tag> listTagByNumDesc(Tag tag);
    //根据String类型的ids获取出具体的tag列表,需自己判断是否size() = 0
    List<Tag> listTagByTagIds(String tagids);
}
