package com.pan.service.impl;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.pan.dao.TagMapper;
import com.pan.pojo.Tag;
import com.pan.pojo.TagExample;
import com.pan.service.TagService;
import com.sun.prism.shader.Texture_LinearGradient_PAD_AlphaTest_Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public Tag getTagById(Integer id) {
        Tag tag = tagMapper.selectByPrimaryKey(id);
        return tag;
    }

    @Transactional
    @Override
    public List<Tag> getTagByName(String name) {
        TagExample example = new TagExample();
        TagExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        List<Tag> tags = tagMapper.selectByExample(example);
        return tags;
    }


    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        List<Tag> tags = getTagByName(tag.getName());
        if(tags.size() != 0){
            return null;
        }
        tagMapper.insertSelective(tag);
        return tag;
    }

    @Transactional
    @Override
    public List<Tag> listTag() {
        TagExample example = new TagExample();
        TagExample.Criteria criteria = example.createCriteria();
        List<Tag> tags = tagMapper.selectByExample(example);
        return tags;
    }

    @Transactional
    @Override
    public Tag updateTag(Tag tag) {
        Tag tag1 = getTagById(tag.getId());
        if(tag1 == null){
            return null;
        }
        List<Tag> tagByName = getTagByName(tag.getName());
        //判断是否有相同名字的tag
        if(tagByName.size() != 0){
            //判断是否为自己
            if(tagByName.size() == 1 && tagByName.get(0).getId() == tag.getId()){
                tagMapper.updateByPrimaryKeySelective(tag);
            }
            return null;
        }
        tagMapper.updateByPrimaryKeySelective(tag);
        return tag;
    }

    @Transactional
    @Override
    public void deleteTagById(Integer id) {
        tagMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void increaseBlogsNum(Integer tagid) {
        Tag tag = tagMapper.selectTagForUpdate(tagid);
        tag.setBlogs(tag.getBlogs()+1);
        tagMapper.updateByPrimaryKey(tag);
    }

    @Override
    public void decreaseBlogNum(Integer tagid) {
        Tag tag = tagMapper.selectTagForUpdate(tagid);
        tag.setBlogs(tag.getBlogs()-1);
        tagMapper.updateByPrimaryKey(tag);
    }

    @Override
    public List<Tag> listTagByNumDesc(Tag tag) {
        return tagMapper.selectTagByNumDesc(tag);
    }

    @Override
    public List<Tag> listTagByTagIds(String tagids) {
        List<Tag> tags = new ArrayList<>();
        for(String id : tagids.split(",")){
            tags.add(tagMapper.selectByPrimaryKey(Integer.parseInt(id)));
        }
        return tags;
    }
}
