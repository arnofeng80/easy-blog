package com.arno.blog.service;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.User;

/**
 * <p>
 * 用户表Service
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
public interface UserService {

    /**
     * 保存
     *
     * @param user
     */
    void save(User user);

    /**
     * 更新
     *
     * @param user
     */
    void update(User user);

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    User findById(Integer id);

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    Pageable<User> findAutoByPage(Pageable<User> page);

    /**
     * 根據id刪除
     *
     * @param id
     */
    void removeById(Integer id);
}
