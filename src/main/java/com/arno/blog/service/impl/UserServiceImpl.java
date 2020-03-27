package com.arno.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.mapper.UserMapper;
import com.arno.blog.pojo.User;
import com.arno.blog.service.UserService;

/**
 * <p>
 * 用户表Service實現類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 保存
     *
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user) {
        userMapper.save(user);
    }

    /**
     * 更新
     *
     * @param user
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User user) {
        userMapper.update(user);
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pageable<User> findAutoByPage(Pageable<User> page) {
        return userMapper.findAutoByPage(page);
    }

    /**
     * 根據id刪除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Integer id) {
        userMapper.removeById(id);
    }
}