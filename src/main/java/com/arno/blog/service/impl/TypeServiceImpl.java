package com.arno.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.mapper.TypeMapper;
import com.arno.blog.pojo.Type;
import com.arno.blog.service.TypeService;

/**
 * <p>
 * 帖子类型表Service實現類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 保存
     *
     * @param type
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Type type) {
        typeMapper.save(type);
    }

    /**
     * 更新
     *
     * @param type
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Type type) {
        typeMapper.update(type);
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @Override
    public Type findById(Integer id) {
        return typeMapper.findById(id);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pageable<Type> findAutoByPage(Pageable<Type> page) {
        return typeMapper.findAutoByPage(page);
    }

    /**
     * 根據id刪除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Integer id) {
        typeMapper.removeById(id);
    }

    /**
     * 查詢所有
     * @return
     */
    @Override
    public List<Type> getAll() {
        return typeMapper.findAll();
    }
}