package com.pan.service.impl;

import com.pan.dao.TypeMapper;
import com.pan.pojo.Type;
import com.pan.pojo.TypeExample;
import com.pan.service.TypeService;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    @Transactional
    @Override
    public Type saveType(Type type) {
        List<Type> list = getTypesByName(type);
        if(list.size() != 0){
            return null;
        }
        int insert = typeMapper.insertSelective(type);
        return type;
    }

    @Transactional
    @Override
    public List<Type> getTypesByName(Type type) {
        //查询类型
        TypeExample example = new TypeExample();
        TypeExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(type.getName());
        return typeMapper.selectByExample(example);
    }

    @Transactional
    @Override
    public Type getTypeById(Integer id) {
        Type type = typeMapper.selectByPrimaryKey(id);
        return type;
    }

    @Transactional
    @Override
    public List<Type> typeList() {
        TypeExample example = new TypeExample();
        TypeExample.Criteria criteria = example.createCriteria();
        List<Type> types = typeMapper.selectByExample(example);
        return types;
    }

    @Transactional
    @Override
    public Type updateType(Type type) {
        Type type1 = typeMapper.selectByPrimaryKey(type.getId());
        if(type1 == null){
            return null;
        }
        List<Type> typesByName = getTypesByName(type);
        //判断是否有相同名字的type
        if(typesByName.size() != 0){
            //判断是否为自己
            if(typesByName.size() == 1 && typesByName.get(0).getId()==type.getId()){
                typeMapper.updateByPrimaryKeySelective(type);
            }else{
                return null;
            }
        }

        typeMapper.updateByPrimaryKeySelective(type);
        return type;
    }

    @Transactional
    @Override
    public void deleteType(Integer id) {
        typeMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void increaseBlogsNum(Integer typeid) {
        Type type = typeMapper.selectTypeForUpdate(typeid);
        type.setBlogs(type.getBlogs()+1);
        typeMapper.updateByPrimaryKey(type);
    }

    @Transactional
    @Override
    public void decreaseBlogNum(Integer typeid) {
        Type type = typeMapper.selectTypeForUpdate(typeid);
        type.setBlogs(type.getBlogs()-1);
        typeMapper.updateByPrimaryKey(type);
    }

    @Override
    public List<Type> listTypeByNumDesc(Type type) {
        List<Type> types = typeMapper.selectTagByNumDesc(type);
        return types;
    }
}
