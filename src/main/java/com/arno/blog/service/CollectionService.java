package com.arno.blog.service;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Collection;

/**
 * <p>
 * 收藏时间Service
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
public interface CollectionService {

    /**
     * 保存
     *
     * @param collection
     */
    void save(Collection collection);

    /**
     * 更新
     *
     * @param collection
     */
    void update(Collection collection);

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    Collection findById(Integer id);

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    Pageable<Collection> findAutoByPage(Pageable<Collection> page);

    /**
     * 根據id刪除
     *
     * @param id
     */
    void removeById(Integer id);

}
