package com.arno.blog.framework.provider;

import java.util.List;

import com.arno.blog.framework.model.MybatisColumn;
import com.arno.blog.framework.utils.StringUtils;
import com.google.common.collect.Lists;

/**
 * 高效CRUD介面（主要針對查詢，不全表查詢。代寫）
 * @author: Arno
 * @date: 2020/03/27
 * @Version 1.0
 */
public class EfficientProvider extends CrudProvider {

    public static final String FIND_ALL_NO_BASE = "findAllNoBase";

    /**
     * 查詢所有非基本欄位
     *
     * @return
     */
    public String findAllNoBase() {
        List<String> columnNames = Lists.newArrayList();
        // 獲取表名的所有欄位，遍歷
        for (MybatisColumn mybatisColumn : getMybatisTable().getMybatisColumnList()) {
            boolean selectFlag = true;
            if (getMybatisTable().getCreatedDate() != null && getMybatisTable().getCreatedDate().getName().equals(mybatisColumn.getName())) {
                selectFlag = false;
            } else if (getMybatisTable().getCreatedBy() != null && getMybatisTable().getCreatedBy().getName().equals(mybatisColumn.getName())) {
                selectFlag = false;
            } else if (getMybatisTable().getUpdateDate() != null && getMybatisTable().getUpdateDate().getName().equals(mybatisColumn.getName())) {
                selectFlag = false;
            } else if (getMybatisTable().getUpdateBy() != null && getMybatisTable().getUpdateBy().getName().equals(mybatisColumn.getName())) {
                selectFlag = false;
            } else if (getMybatisTable().getVersion() != null && getMybatisTable().getVersion().getName().equals(mybatisColumn.getName())) {
                selectFlag = false;
            } else if (getMybatisTable().getDeleted() != null && getMybatisTable().getDeleted().getName().equals(mybatisColumn.getName())) {
                selectFlag = false;
            }
            if (selectFlag) {
                columnNames.add(mybatisColumn.getName());
            }
        }
        MybatisColumn deleted = getMybatisTable().getDeleted();
        String sql;
        if (deleted != null) {
            // 刪除列不為空，說明有注解
            sql = "SELECT " + StringUtils.join(columnNames, ",") + " FROM " + getMybatisTable().getName() + " WHERE " + deleted.getName() + "=" + deleted.getNotDelete();
        } else {
            sql = "SELECT " + StringUtils.join(columnNames, ",") + " FROM " + getMybatisTable().getName();
        }
        return sql;
    }
}

