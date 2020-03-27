package com.arno.blog.pojo;

import java.io.Serializable;

import lombok.Data;

/**
 * <p>
 * 博客表實體類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Data
@Table(name = "bl_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = -559415810554538278L;

    /**
     * 帖子id
     */
    @Id
    @Column(name = "blog_id")
    private String blogId;

    /**
     * 標題
     */
    @Column(name = "blog_title")
    private String blogTitle;

    /**
     * 封面
     */
    @Column(name = "blog_image")
    private String blogImage;

    /**
     * 帖子內容
     */
    @Column(name = "blog_content")
    private String blogContent;

    /**
     * 點贊數
     */
    @Column(name = "blog_goods")
    private Integer blogGoods;

    /**
     * 閱讀數
     */
    @Column(name = "blog_read")
    private Integer blogRead;

    /**
     * 收藏數
     */
    @Column(name = "blog_collection")
    private Integer blogCollection;

    /**
     * 博客分類
     */
    @Column(name = "blog_type")
    private Integer blogType;

    @Column(insertAble = false, updatable = false)
    private Type type;

    /**
     * 簡介
     */
    @Column(name = "blog_remark")
    private String blogRemark;

    /**
     * 評論數
     */
    @Column(name = "blog_comment")
    private Integer blogComment;

    /**
     * 文章來源（爬蟲時使用）
     */
    @Column(name = "blog_source")
    private String blogSource;

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
     * 是否刪除，0否1是
     */
    @Column(name = "deleted")
    @LogicDelete
    private Integer deleted;

}

