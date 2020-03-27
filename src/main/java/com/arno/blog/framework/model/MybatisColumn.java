package com.arno.blog.framework.model;

import com.arno.blog.framework.model.MybatisColumn;

import lombok.Data;

/**
 * @author Arno
 */
@Data
public class MybatisColumn {

    /**
     * 列名（對應表）
     */
    private String name;

    /**
     * 屬性名
     */
    private String fieldName;

    /**
     * java類型
     */
    private Class<?> javaType;

    /**
     * 是否插入
     */
    private boolean insertTable;

    /**
     * 是否更新
     */
    private boolean updatable;

    /**
     * 是否可空
     */
    private boolean nullable;

    /**
     * 邏輯刪除值
     */
    private int deleted;

    /**
     * 未刪除
     */
    private int notDelete;

    @Override
    public String toString() {
        return "MybatisColumn{" +
                "name='" + name + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", insertTable=" + insertTable +
                ", updatable=" + updatable +
                ", nullable=" + nullable +
                ", deleted=" + deleted +
                ", notDelete=" + notDelete +
                '}';
    }
}

