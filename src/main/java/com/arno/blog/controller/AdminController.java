package com.arno.blog.controller;

import javax.security.auth.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.pojo.Admin;
import com.arno.blog.pojo.Result;
import com.arno.blog.service.AdminService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 管理員表前端控制器
 * </p>
 *
 * @author Arbi
 * @date 2020-03-27
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

//    /**
//     * 登錄
//     *
//     * @param admin
//     * @return
//     */
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public Result<Object> login(@RequestBody Admin admin) {
//        Subject subject = SecurityUtils.getSubject();
//        AuthenticationToken token = new UsernamePasswordToken(admin.getUsername(), admin.getPassword());
//        try {
//            subject.login(token);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new Result<>(40000, "用戶名或密碼錯誤！");
//        }
//        // 查詢登錄成功的資料，放到redis中
//        Admin loginUser = (Admin) subject.getPrincipal();
//        Serializable sessionId = subject.getSession().getId();
//        Map<String, Object> dataMap = Maps.newHashMap();
//        dataMap.put("token", sessionId);
//        return new Result<>("登錄成功！", dataMap);
//    }

//    /**
//     * 根據token獲取使用者資訊
//     */
//    @RequestMapping(value = "/info", method = RequestMethod.GET)
//    public Result<Admin> info() {
//        Admin admin = ShiroUtils.getLoginAdmin();
//        return new Result<>(admin);
//    }

    /**
     * 保存
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result<Object> save(@RequestBody Admin admin) {
        adminService.save(admin);
        return new Result<>("添加成功！");
    }

    /**
     * 更新
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Result<Object> update(@RequestBody Admin admin) {
        adminService.update(admin);
        return new Result<>("更新成功！");
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public Result<Admin> get(@PathVariable Integer id) {
        Admin admin = adminService.findById(id);
        return new Result<>(admin);
    }

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "/getByPage", method = RequestMethod.POST)
    public Result<Pageable<Admin>> getByPage(@RequestBody Pageable<Admin> page) {
        page = adminService.findAutoByPage(page);
        return new Result<>(page);
    }

    /**
     * 根據id刪除
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.PUT)
    public Result<Object> delete(@RequestBody Admin admin) {
        adminService.removeById(admin.getId());
        return new Result<>("刪除成功！");
    }

}

