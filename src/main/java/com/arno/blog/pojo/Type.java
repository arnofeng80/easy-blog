package com.arno.blog.pojo;

import java.io.Serializable;

import com.arno.blog.framework.annotation.Column;
import com.arno.blog.framework.annotation.GeneratedValue;
import com.arno.blog.framework.annotation.GenerationType;
import com.arno.blog.framework.annotation.Id;
import com.arno.blog.framework.annotation.LogicDelete;
import com.arno.blog.framework.annotation.Table;

import lombok.Data;

/**
 * <p>
 * 帖子類型表實體類
 * </p>
 *
 * @author Arno
 * @date 2020-03-27
 * @Version 1.0
 *
 */
@Data
@Table(name = "bl_type")
public class Type implements Serializable {

	private static final long serialVersionUID = -2863573388122138904L;

	/**
     * 分類id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Integer typeId;

    /**
     * 分類名稱
     */
    @Column(name = "type_name")
    private String typeName;

    /**
     * 帖子數
     */
    @Column(name = "type_blog_count")
    private Integer typeBlogCount;

    /**
     * 是否啟用，0否1是
     */
    @Column(name = "enable")
    private Integer enable;

    /**
     * 是否刪除，0否1是
     */
    @Column(name = "deleted")
    @LogicDelete
    private Integer deleted;
}
