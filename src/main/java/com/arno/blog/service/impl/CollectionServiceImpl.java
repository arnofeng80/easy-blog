package com.arno.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.mapper.CollectionMapper;
import com.arno.blog.pojo.Collection;
import com.arno.blog.service.CollectionService;

/**
 * <p>
 * 收藏时间Service實現類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    /**
     * 保存
     *
     * @param collection
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Collection collection) {
        collectionMapper.save(collection);
    }

    /**
     * 更新
     *
     * @param collection
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Collection collection) {
        collectionMapper.update(collection);
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @Override
    public Collection findById(Integer id) {
        return collectionMapper.findById(id);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pageable<Collection> findAutoByPage(Pageable<Collection> page) {
        return collectionMapper.findAutoByPage(page);
    }

    /**
     * 根據id刪除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Integer id) {
        collectionMapper.removeById(id);
    }

}