package com.arno.blog.service;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Blog;

/**
 * <p>
 * 博客表Service
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
public interface BlogService {

    /**
     * 保存
     *
     * @param blog
     */
    void save(Blog blog);

    /**
     * 更新
     *
     * @param blog
     */
    void update(Blog blog);

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    Blog findById(String id);

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    Pageable<Blog> findAutoByPage(Pageable<Blog> page);

    /**
     * 根據id刪除
     *
     * @param id
     */
    void removeById(String id);

}

