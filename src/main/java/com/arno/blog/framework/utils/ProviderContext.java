package com.arno.blog.framework.utils;

import lombok.Data;

/**
 * 上下文。用來存儲id類型和實體類型
 * @author Arno
 */
@Data
public class ProviderContext {
    private Class<?> entityClass;
    private Class<?> idClass;

    @Override
    public String toString() {
        return "ProviderContext{" +
                "entityClass=" + entityClass +
                ", idClass=" + idClass +
                '}';
    }
}

