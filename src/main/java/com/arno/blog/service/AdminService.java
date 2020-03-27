package com.arno.blog.service;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Admin;

/**
 * <p>
 * 管理員表Service
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
public interface AdminService {

    /**
     * 保存
     *
     * @param admin
     */
    void save(Admin admin);

    /**
     * 更新
     *
     * @param admin
     */
    void update(Admin admin);

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    Admin findById(Integer id);

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    Pageable<Admin> findAutoByPage(Pageable<Admin> page);

    /**
     * 根據id刪除
     *
     * @param id
     */
    void removeById(Integer id);

    /**
     * 根據用戶名查詢
     * @param username
     * @return
     */
    Admin getByUsername(String username);
}
