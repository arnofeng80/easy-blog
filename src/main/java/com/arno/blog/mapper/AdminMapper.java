package com.arno.blog.mapper;

import org.springframework.stereotype.Component;

import com.arno.blog.framework.mapper.MybatisMapper;
import com.arno.blog.pojo.Admin;

/**
 * <p>
 * 管理員表Mapper
 * </p>
 *
 * @author Arno
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
@Component
public interface AdminMapper extends MybatisMapper<Admin, Integer> {

    /**
     * 根據用戶名查詢
     * @param username
     * @return
     */
    Admin getByUsername(String username);
}
