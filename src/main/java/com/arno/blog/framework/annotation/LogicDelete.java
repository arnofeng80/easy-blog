package com.arno.blog.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示這是邏輯刪除欄位
 *
 * @author Arno
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface LogicDelete {
    /**
     * 邏輯刪除-已刪除
     */
    int deleted() default 1;
    /**
     * 邏輯刪除-未刪除
     */
    int notDelete() default 0;
}
