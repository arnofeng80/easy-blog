package com.arno.blog.framework.utils;

import com.arno.blog.framework.dialect.Dialect;
import com.arno.blog.framework.dialect.MySqlDialect;

import lombok.Data;

/**
 * 本地執行緒上下文，單例模式
 * 用來存儲在同一個執行緒中可能會用到的全域變數
 * @author Arno
 */
@Data
public class ThreadLocalContext {

    /**
     * 用來存儲實體的類型和id類型。與BaseMapper的兩個泛型對應
     */
    private ProviderContext providerContext;

    /**
     * 是否記錄日誌
     */
    private boolean isLog = false;

    /**
     * 執行緒本地記憶體中的變數
     */
    private static ThreadLocal<ThreadLocalContext> threadLocal = new ThreadLocal<>();

    public static ThreadLocalContext get() {
        if (threadLocal.get() == null) {
            ThreadLocalContext threadLocalContext = new ThreadLocalContext();
            threadLocal.set(threadLocalContext);
        }
        ThreadLocalContext threadLocalContext = threadLocal.get();
        return threadLocalContext;
    }

    public void remove() {
        this.providerContext = null;
        this.isLog = false;
        threadLocal.remove();
    }

    public Dialect getDialect() {
        return new MySqlDialect();
    }
}
