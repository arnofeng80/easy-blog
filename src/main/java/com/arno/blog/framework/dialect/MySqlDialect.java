package com.arno.blog.framework.dialect;

import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.arno.blog.framework.utils.Pageable;

/**
 * Mysql方言
 *
 * @author Arno
 */
public class MySqlDialect implements Dialect {
    @Override
    public String getPageableSql(String sql, Pageable<?> pageable) {
        StringBuilder sb = new StringBuilder(sql);
        // 排序
        Map<String, String> sorted = pageable.getSorted();
        if (!CollectionUtils.isEmpty(sorted)) {
            // 排序列不空，排序
            sb.append(" ORDER BY ");
            for (Map.Entry<String, String> entry : sorted.entrySet()) {
                String sortName = getColumnName(entry.getKey());
                String sortOrder = entry.getValue();
                sb.append(sortName);
                sb.append(" ");
                sb.append(sortOrder);
                sb.append(",");
            }
            // 去除最後一個,
            sb.deleteCharAt(sb.length() - 1);
        }
        if (pageable.getCurrentPage() >= 0) {
            sb.append(" LIMIT ").append(" ").append(pageable.getIndex()).append(",").append(pageable.getPageSize());
        }
        return sb.toString();
    }

}

