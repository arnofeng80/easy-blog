package com.arno.blog.controller;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Comment;
import com.arno.blog.pojo.Result;
import com.arno.blog.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 評論表前端控制器
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 保存
     *
     * @param comment
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Comment comment) {
        commentService.save(comment);
        return new Result<>("添加成功！");
    }

    /**
     * 更新
     *
     * @param comment
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Comment comment) {
        commentService.update(comment);
        return new Result<>("更新成功！");
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<Comment> get(@PathVariable String id) {
        Comment comment = commentService.findById(id);
        return new Result<>(comment);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Pageable<Comment>> getByPage(@RequestBody Pageable<Comment> page) {
        page = commentService.findAutoByPage(page);
        return new Result<>(page);
    }

    /**
     * 根據id刪除
     *
     * @param comment
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public Result<Object> delete(@RequestBody Comment comment) {
        commentService.removeById(comment.getCommentId());
        return new Result<>("刪除成功！");
    }

}

