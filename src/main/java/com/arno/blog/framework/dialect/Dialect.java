package com.arno.blog.framework.dialect;

import com.arno.blog.framework.model.MybatisTable;
import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.framework.utils.ProviderUtils;
import com.arno.blog.framework.utils.ThreadLocalContext;

/**
 * 資料庫方言
 * 定義每一種資料庫分頁的邏輯
 * 暫時只支援MySql
 *
 * @author Arno
 */
public interface Dialect {

    /**
     * 返回分頁sql
     *
     * @param sql
     * @param page
     * @return
     */
    String getPageableSql(String sql, Pageable<?> page);

    /**
     * 將sql轉換為總記錄數SQL
     *
     * @param sql sql語句
     * @return 總記錄數的sql
     */
    default String getCountSql(String sql) {
        return "select count(1) from (" + sql + ") tmp_count";
    }

	/**
	 * 根據欄位名獲取列名
	 * @param fieldName
	 * @return
	 */
	default String getColumnName(String fieldName) {
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

