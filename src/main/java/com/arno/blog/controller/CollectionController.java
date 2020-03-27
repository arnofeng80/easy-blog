package com.arno.blog.controller;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Collection;
import com.arno.blog.pojo.Result;
import com.arno.blog.service.CollectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 收藏時間前端控制器
 * </p>
 *
 * @author 稽哥
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 */
@RestController
@RequestMapping("/collection")
@Slf4j
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    /**
     * 保存
     *
     * @param collection
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Collection collection) {
        collectionService.save(collection);
        return new Result<>("添加成功！");
    }

    /**
     * 更新
     *
     * @param collection
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Collection collection) {
        collectionService.update(collection);
        return new Result<>("更新成功！");
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<Collection> get(@PathVariable Integer id) {
        Collection collection = collectionService.findById(id);
        return new Result<>(collection);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Pageable<Collection>> getByPage(@RequestBody Pageable<Collection> page) {
        page = collectionService.findAutoByPage(page);
        return new Result<>(page);
    }

    /**
     * 根據id刪除
     *
     * @param collection
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public Result<Object> delete(@RequestBody Collection collection) {
        collectionService.removeById(collection.getCollectionId());
        return new Result<>("刪除成功！");
    }

}

