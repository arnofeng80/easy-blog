package com.arno.blog.mapper;

import org.springframework.stereotype.Component;

import com.arno.blog.framework.mapper.MybatisMapper;
import com.arno.blog.pojo.Comment;

/**
 * <p>
 * 評論表Mapper
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Component
public interface CommentMapper extends MybatisMapper<Comment, String> {

}

