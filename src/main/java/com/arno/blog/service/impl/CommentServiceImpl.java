package com.arno.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.mapper.CommentMapper;
import com.arno.blog.pojo.Comment;
import com.arno.blog.service.CommentService;

/**
 * <p>
 * 评论表Service實現類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 保存
     *
     * @param comment
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Comment comment) {
        commentMapper.save(comment);
    }

    /**
     * 更新
     *
     * @param comment
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Comment comment) {
        commentMapper.update(comment);
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @Override
    public Comment findById(String id) {
        return commentMapper.findById(id);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Pageable<Comment> findAutoByPage(Pageable<Comment> page) {
        return commentMapper.findAutoByPage(page);
    }

    /**
     * 根據id刪除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeById(String id) {
        commentMapper.removeById(id);
    }
}