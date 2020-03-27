package com.arno.blog.service;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Type;

import java.util.List;

/**
 * <p>
 * 帖子类型表Service
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
public interface TypeService {

    /**
     * 保存
     *
     * @param type
     */
    void save(Type type);

    /**
     * 更新
     *
     * @param type
     */
    void update(Type type);

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    Type findById(Integer id);

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    Pageable<Type> findAutoByPage(Pageable<Type> page);

    /**
     * 根據id刪除
     *
     * @param id
     */
    void removeById(Integer id);

    /**
     * 查詢所有
     * @return
     */
    List<Type> getAll();
}

