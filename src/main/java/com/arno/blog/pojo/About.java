package com.arno.blog.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Date: 2020/2/9 14:41
 * @Version 1.0
 */
@Data
public class About implements Serializable {
	private static final long serialVersionUID = -3141907263194459940L;
	private Integer aboutId;
    private String aboutTitle;
    private String aboutContent;
    private Integer aboutRead;
    private String createdTime;
    private String updateTime;
    private Integer version;
    private Integer enable;
    private Integer deleted;

}
