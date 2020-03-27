package com.arno.blog.pojo;

import java.io.Serializable;

import com.arno.blog.framework.annotation.Column;
import com.arno.blog.framework.annotation.GeneratedValue;
import com.arno.blog.framework.annotation.GenerationType;
import com.arno.blog.framework.annotation.Id;
import com.arno.blog.framework.annotation.LogicDelete;
import com.arno.blog.framework.annotation.Table;
import com.arno.blog.framework.annotation.Version;

import lombok.Data;

/**
 * <p>
 * 使用者表實體類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Data
@Table(name = "bl_user")
public class User implements Serializable {

	private static final long serialVersionUID = 6031961463149230658L;

	/**
     * 用戶id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用戶名
     */
    @Column(name = "username")
    private String username;

    /**
     * 密碼
     */
    @Column(name = "password")
    private String password;

    /**
     * 姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 性別。1男2女
     */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 頭像
     */
    @Column(name = "header")
    private String header;

    /**
     * 昵稱
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 郵箱
     */
    @Column(name = "user_email")
    private String userEmail;

    /**
     * 創建時間
     */
    @Column(name = "created_time")
    private String createdTime;

    /**
     * 更新時間
     */
    @Column(name = "update_time")
    private String updateTime;

    /**
     * 樂觀鎖
     */
    @Column(name = "version")
    @Version
    private Integer version;

    /**
     * 是否刪除。0否1是
     */
    @Column(name = "deleted")
    @LogicDelete
    private Integer deleted;
}
