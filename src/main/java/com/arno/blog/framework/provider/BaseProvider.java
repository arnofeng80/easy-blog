package com.arno.blog.framework.provider;

import org.springframework.stereotype.Component;

import com.arno.blog.framework.model.MybatisTable;
import com.arno.blog.framework.utils.ProviderUtils;
import com.arno.blog.framework.utils.ThreadLocalContext;

/**
 * 基本提供者，用於獲取表和實體
 * @author Arno
 */
@Component
public class BaseProvider {

    /**
     * 獲取表
     * @return
     */
    protected MybatisTable getMybatisTable() {
        return ProviderUtils.getMybatisTable(getEntityClass());
    }

    /**
     * 獲取實體類型
     * @return
     */
    protected Class<?> getEntityClass() {
        return ThreadLocalContext.get().getProviderContext().getEntityClass();
    }
}

