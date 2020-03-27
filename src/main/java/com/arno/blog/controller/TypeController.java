package com.arno.blog.controller;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Result;
import com.arno.blog.pojo.Type;
import com.arno.blog.service.TypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 帖子類型表前端控制器
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/type")
@Slf4j
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 保存
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Type type) {
        typeService.save(type);
        return new Result<>("添加成功！");
    }

    /**
     * 查詢所有
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result<List<Type>> all() {
        List<Type> list = typeService.getAll();
        return new Result<>(list);
    }

    /**
     * 更新
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Type type) {
        typeService.update(type);
        return new Result<>("更新成功！");
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<Type> get(@PathVariable Integer id) {
        Type type = typeService.findById(id);
        return new Result<>(type);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Pageable<Type>> getByPage(@RequestBody Pageable<Type> page) {
        page = typeService.findAutoByPage(page);
        return new Result<>(page);
    }

    /**
     * 根據id刪除
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public Result<Object> delete(@RequestBody Type type) {
        typeService.removeById(type.getTypeId());
        return new Result<>("刪除成功！");
    }

}

