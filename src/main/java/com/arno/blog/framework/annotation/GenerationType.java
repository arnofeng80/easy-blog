package com.arno.blog.framework.annotation;

/**
 * id生成類型
 *
 * @author Arno
 */
public enum GenerationType {
    /**
     * 不維護（由資料庫自己維護，主鍵自增時使用）
     */
    IDENTITY,
    /**
     * 雪花演算法
     */
    SNOWFLAKE,
    /**
     * UUID
     */
    UUID,
    /**
     * 自己輸入
     */
    CUSTOM
}
