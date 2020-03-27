package com.arno.blog.framework.opt.strategy;

import java.util.List;
import java.util.Map;

import com.arno.blog.framework.model.MybatisTable;
import com.arno.blog.framework.utils.ProviderUtils;
import com.arno.blog.framework.utils.ThreadLocalContext;

/**
 * 條件前綴策略
 *
 * @author: Arno
 * @date: 2020/03/27
 * @Version 1.0
 */
public interface BaseOptStrategy {

    /**
     * 處理option
     *
     * @param params    參數
     * @param asTable   表別名
     * @param expresses 拆分後的數據
     * @param key       #{param.key}
     */
    void sqlOptHandler(Map<String, Object> params, String asTable, List<String> expresses, String key);

    /**
     * 參數轉列名。取出符號後的參數，轉底線
     *
     * @param temp
     * @return
     */
    default String getExpressColumn(String temp) {
        String fieldName = temp.split("_")[1];
        return getMybatisTable().getColumnName(fieldName);
    }

    /**
     * 獲取表
     *
     * @return
     */
    default MybatisTable getMybatisTable() {
        return ProviderUtils.getMybatisTable(getEntityClass());
    }

    /**
     * 獲取實體類型
     *
     * @return
     */
    default Class<?> getEntityClass() {
        return ThreadLocalContext.get().getProviderContext().getEntityClass();
    }
}


