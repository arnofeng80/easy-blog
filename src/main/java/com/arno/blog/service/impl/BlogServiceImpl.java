package com.arno.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.mapper.BlogMapper;
import com.arno.blog.mapper.TypeMapper;
import com.arno.blog.pojo.Blog;
import com.arno.blog.pojo.Type;
import com.arno.blog.service.BlogService;

/**
 * <p>
 * 博客表Service實現類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TypeMapper typeMapper;

    /**
     * 保存
     *
     * @param blog
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Blog blog) {
        blogMapper.save(blog);
        Integer blogType = blog.getBlogType();
        Type type = typeMapper.findById(blogType);
        type.setTypeBlogCount(type.getTypeBlogCount() + 1);
        typeMapper.update(type);
    }

    /**
     * 更新
     *
     * @param blog
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Blog blog) {
        blogMapper.update(blog);
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @Override
    public Blog findById(String id) {
        return blogMapper.findById(id);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pageable<Blog> findAutoByPage(Pageable<Blog> page) {
        Pageable<Blog> pageable = blogMapper.findAutoByPage(page);
        List<Blog> blogList = pageable.getList();
        for (Blog blog : blogList) {
            Type type = typeMapper.findById(blog.getBlogType());
            blog.setType(type);
        }
        return pageable;
    }

    /**
     * 根據id刪除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(String id) {
        blogMapper.removeById(id);
    }
}

