package com.arno.blog.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示這是資料庫的列，並且需要駝峰轉底線
 *
 * @author Arno
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * 列名
     */
    String name() default "";
    /**
     * 是否可為空
     */
    boolean nullable() default true;
    /**
     * 是否需要插入
     */
    boolean insertable() default true;
    /**
     * 是否需要更新
     */
    boolean updatable() default true;
}
