package com.arno.blog.service;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Log;

/**
 * <p>
 * 接口访问日志表Service
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
public interface LogService {

    /**
     * 保存
     *
     * @param log
     */
    void save(Log log);

    /**
     * 更新
     *
     * @param log
     */
    void update(Log log);

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    Log findById(Integer id);

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    Pageable<Log> findAutoByPage(Pageable<Log> page);

    /**
     * 根據id刪除
     *
     * @param id
     */
    void removeById(Integer id);

}

