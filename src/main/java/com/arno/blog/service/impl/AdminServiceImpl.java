package com.arno.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.mapper.AdminMapper;
import com.arno.blog.pojo.Admin;
import com.arno.blog.service.AdminService;

/**
 * <p>
 * 管理員表Service實現類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 保存
     *
     * @param admin
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Admin admin) {
        adminMapper.save(admin);
    }

    /**
     * 更新
     *
     * @param admin
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Admin admin) {
        adminMapper.update(admin);
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @Override
    public Admin findById(Integer id) {
        return adminMapper.findById(id);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pageable<Admin> findAutoByPage(Pageable<Admin> page) {
        return adminMapper.findAutoByPage(page);
    }

    /**
     * 根據id刪除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Integer id) {
        adminMapper.removeById(id);
    }

    /**
     * 根據用戶名查詢
     * @param username
     * @return
     */
    @Override
    public Admin getByUsername(String username) {
        return adminMapper.getByUsername(username);
    }

}

