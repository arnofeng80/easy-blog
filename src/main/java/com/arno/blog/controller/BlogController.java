package com.arno.blog.controller;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Blog;
import com.arno.blog.pojo.Result;
import com.arno.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 博客表前端控制器
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    /**
     * 保存
     *
     * @param blog
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Blog blog) {
        blogService.save(blog);
        return new Result<>("添加成功！");
    }

    /**
     * 更新
     *
     * @param blog
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Blog blog) {
        blogService.update(blog);
        return new Result<>("更新成功！");
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<Blog> get(@PathVariable String id) {
        Blog blog = blogService.findById(id);
        return new Result<>(blog);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Pageable<Blog>> getByPage(@RequestBody Pageable<Blog> page) {
        page = blogService.findAutoByPage(page);
        return new Result<>(page);
    }

    /**
     * 根據id刪除
     *
     * @param blog
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public Result<Object> delete(@RequestBody Blog blog) {
        blogService.removeById(blog.getBlogId());
        return new Result<>("刪除成功！");
    }

}

