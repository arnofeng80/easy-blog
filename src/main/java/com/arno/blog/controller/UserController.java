package com.arno.blog.controller;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Result;
import com.arno.blog.pojo.User;
import com.arno.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用戶表前端控制器
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 保存
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Object> save(@RequestBody User user) {
        userService.save(user);
        return new Result<>("添加成功！");
    }

    /**
     * 更新
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody User user) {
        userService.update(user);
        return new Result<>("更新成功！");
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<User> get(@PathVariable Integer id) {
        User user = userService.findById(id);
        return new Result<>(user);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Pageable<User>> getByPage(@RequestBody Pageable<User> page) {
        page = userService.findAutoByPage(page);
        return new Result<>(page);
    }

    /**
     * 根據id刪除
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public Result<Object> delete(@RequestBody User user) {
        userService.removeById(user.getUserId());
        return new Result<>("刪除成功！");
    }

}

