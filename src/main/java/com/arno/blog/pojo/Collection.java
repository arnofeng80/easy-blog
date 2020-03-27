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
 * 收藏時間實體類
 * </p>
 *
 * @author Arno
 * @date 2020-02-07 14:04:12
 * @Version 1.0
 *
 */
@Data
@Table(name = "bl_collection")
public class Collection implements Serializable {

	private static final long serialVersionUID = -903706613628158713L;

	/**
     * 收藏id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private Integer collectionId;

    /**
     * 帖子id
     */
    @Column(name = "blog_id")
    private String blogId;

    /**
     * 用戶id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 收藏時間
     */
    @Column(name = "collection_time")
    private String collectionTime;
}
