package com.arno.blog.service;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Comment;

/**
 * <p>
 * 评论表Service
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
public interface CommentService {

    /**
     * 保存
     *
     * @param comment
     */
    void save(Comment comment);

    /**
     * 更新
     *
     * @param comment
     */
    void update(Comment comment);

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    Comment findById(String id);

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    Pageable<Comment> findAutoByPage(Pageable<Comment> page);

    /**
     * 根據id刪除
     *
     * @param id
     */
    void removeById(String id);

}
