package com.arno.blog.pojo;

import java.io.Serializable;

import com.arno.blog.framework.annotation.Column;
import com.arno.blog.framework.annotation.Id;
import com.arno.blog.framework.annotation.LogicDelete;
import com.arno.blog.framework.annotation.Table;

import lombok.Data;

/**
 * <p>
 * 評論表實體類
 * </p>
 *
 * @author Arno
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
@Data
@Table(name = "bl_comment")
public class Comment implements Serializable {

	private static final long serialVersionUID = 5173851776820392133L;

	/**
     * 評論id
     */
    @Id
    @Column(name = "comment_id")
    private String commentId;

    /**
     * 評論內容
     */
    @Column(name = "comment_content")
    private String commentContent;

    /**
     * 評價人
     */
    @Column(name = "comment_user")
    private Integer commentUser;

    /**
     * 評論帖子id
     */
    @Column(name = "comment_blog")
    private String commentBlog;

    /**
     * 點贊數
     */
    @Column(name = "comment_good")
    private Integer commentGood;

    /**
     * 評論時間
     */
    @Column(name = "created_time")
    private String createdTime;

    /**
     * 是否刪除，0否1是
     */
    @Column(name = "deleted")
    @LogicDelete
    private Integer deleted;

}
