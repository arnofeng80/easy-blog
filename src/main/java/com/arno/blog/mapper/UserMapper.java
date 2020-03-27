package com.arno.blog.mapper;

import org.springframework.stereotype.Component;

import com.arno.blog.framework.mapper.MybatisMapper;
import com.arno.blog.pojo.User;

/**
 * <p>
 * 用戶表Mapper
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Component
public interface UserMapper extends MybatisMapper<User, Integer> {

}
