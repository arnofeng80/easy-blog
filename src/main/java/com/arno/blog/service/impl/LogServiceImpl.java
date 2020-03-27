package com.arno.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.mapper.LogMapper;
import com.arno.blog.pojo.Log;
import com.arno.blog.service.LogService;

/**
 * <p>
 * 接口访问日志表Service實現類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 保存
     *
     * @param log
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Log log) {
        logMapper.save(log);
    }

    /**
     * 更新
     *
     * @param log
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Log log) {
        logMapper.update(log);
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @Override
    public Log findById(Integer id) {
        return logMapper.findById(id);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pageable<Log> findAutoByPage(Pageable<Log> page) {
        return logMapper.findAutoByPage(page);
    }

    /**
     * 根據id刪除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(Integer id) {
        logMapper.removeById(id);
    }
}