package com.arno.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arno.blog.enums.StateEnums;
import com.arno.blog.exception.BlogException;
import com.arno.blog.mapper.TypeMapper;
import com.arno.blog.pojo.Type;
import com.arno.blog.service.TypeService;

/**
 * <p>
 * 帖子类型表服务实现类
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public void save(Type type) {
        // 查询分类是否存在
        Type t = typeMapper.getByName(type.getTypeName());
        if (t != null) {
            throw new BlogException("该分类已存在！");
        }
        typeMapper.insert(type);
    }

    @Override
    public List<Type> getAll() {
        return typeMapper.getAll();
    }

    @Override
    public List<Type> getTypeList() {
        return typeMapper.getTypeList();
    }

    @Override
    public void update(Type type) {
        typeMapper.update(type);
    }

    @Override
    public void enableById(Integer id) {
        // 先查再启用
        Type type = typeMapper.getById(id);
        type.setEnable(StateEnums.ENABLED.getCode());
        typeMapper.update(type);
    }

    @Override
    public void disableById(Integer id) {
        // 先查再弃用
        Type type = typeMapper.getById(id);
        type.setEnable(StateEnums.NOT_ENABLE.getCode());
        typeMapper.update(type);
    }

    @Override
    public void deleteById(Integer id) {
        typeMapper.deleteById(id);
    }

    @Override
    public Type getById(Integer id) {
        return typeMapper.getById(id);
    }
}
