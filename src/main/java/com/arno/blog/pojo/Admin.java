package com.arno.blog.pojo;

import java.io.Serializable;

import com.arno.blog.framework.annotation.Column;
import com.arno.blog.framework.annotation.GeneratedValue;
import com.arno.blog.framework.annotation.GenerationType;
import com.arno.blog.framework.annotation.Id;
import com.arno.blog.framework.annotation.Table;

import lombok.Data;

/**
 * <p>
 * 管理員表實體類
 * </p>
 *
 * @author Arno
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
@Data
@Table(name = "bl_admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 169915810554522554L;

    /**
     * 管理員id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 頭像
     */
    @Column(name = "header")
    private String header;

    /**
     * 簽名
     */
    @Column(name = "signature")
    private String signature;

    /**
     * 介紹
     */
    @Column(name = "comment")
    private String comment;

    /**
     * 帳號
     */
    @Column(name = "username")
    private String username;

    /**
     * 密碼
     */
    @Column(name = "password")
    private String password;
}
