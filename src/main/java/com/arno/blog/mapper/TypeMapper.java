package com.arno.blog.mapper;

import org.springframework.stereotype.Component;

import com.arno.blog.framework.mapper.MybatisMapper;
import com.arno.blog.pojo.Type;

/**
 * <p>
 * 帖子類型表Mapper
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Component
public interface TypeMapper extends MybatisMapper<Type, Integer> {

}

