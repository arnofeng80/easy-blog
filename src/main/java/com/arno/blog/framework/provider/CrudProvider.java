package com.arno.blog.framework.provider;

import java.util.List;

import com.arno.blog.framework.annotation.GenerationType;
import com.arno.blog.framework.model.MybatisColumn;
import com.arno.blog.framework.model.MybatisTable;
import com.arno.blog.framework.utils.ReflectionUtils;
import com.arno.blog.framework.utils.StringUtils;
import com.google.common.collect.Lists;

/**
 * 基本增刪改查方法sql語句
 *
 * @author Arno
 */
public class CrudProvider extends BaseProvider {

    public static final String SAVE = "save";
    public static final String UPDATE = "update";
    public static final String SAVE_BATCH = "saveBatch";
    public static final String UPDATE_BATCH = "updateBatch";
    public static final String FIND_ALL = "findAll";
    public static final String FIND_BY_ID = "findById";
    public static final String FIND_ONE = "findOne";
    public static final String FIND_BY_ENTITY = "findByEntity";
    public static final String FIND_BY_IDS = "findByIds";
    public static final String COUNT = "count";
    public static final String REMOVE_BY_ID = "removeById";
    public static final String REMOVE_BY_IDS = "removeByIds";

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    public String save(Object entity) {
        List<String> columnNames = Lists.newArrayList();
        List<String> fieldNames = Lists.newArrayList();
        // 獲取表名的所有欄位，遍歷
        for (MybatisColumn mybatisColumn : getMybatisTable().getMybatisColumnList()) {
            // 判斷該欄位是否需要插入
            boolean insertTable = getInsertTable(getMybatisTable(), mybatisColumn);
            if (insertTable) {
                // 調用反射工具類，獲取這個欄位的值
                Object value = ReflectionUtils.getFieldValue(entity, mybatisColumn.getFieldName());
                if (value != null) {
                    // 值不為空，分別放入列集合和欄位集合
                    columnNames.add(mybatisColumn.getName());
                    fieldNames.add("#{" + mybatisColumn.getFieldName() + "}");
                }
            }
        }
        // 拼接sql
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(getMybatisTable().getName());
        sb.append(" (");
        sb.append(StringUtils.join(columnNames, ","));
        sb.append(") ");
        sb.append(" VALUES(");
        sb.append(StringUtils.join(fieldNames, ","));
        sb.append(")");
        return sb.toString();
    }

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    public String update(Object entity) {
        List<String> updateColumns = Lists.newArrayList();
        for (MybatisColumn mybatisColumn : getMybatisTable().getMybatisColumnList()) {
            boolean updateTable = mybatisColumn.isUpdatable();
            if (getMybatisTable().getId() != null && getMybatisTable().getId().getName().equals(mybatisColumn.getName())) {
                // id不修改
                updateTable = false;
            } else if (getMybatisTable().getVersion() != null && getMybatisTable().getVersion().getName().equals(mybatisColumn.getName())) {
                continue;
            }
            if (updateTable) {
                // 獲取欄位值
                Object value = ReflectionUtils.getFieldValue(entity, mybatisColumn.getFieldName());
                if (value != null) {
                    updateColumns.add(mybatisColumn.getName() + " = " + "#{" + mybatisColumn.getFieldName() + "}");
                }
            }
        }
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(getMybatisTable().getName());
        sb.append(" SET ");
        sb.append(StringUtils.join(updateColumns, ","));
        sb.append(" WHERE ");
        sb.append(getMybatisTable().getId().getName());
        sb.append(" = ");
        sb.append("#{").append(getMybatisTable().getId().getFieldName()).append("}");
        return sb.toString();
    }

    /**
     * 批量更新
     * TODO 樂觀鎖有問題
     *
     * @param list
     * @return
     */
    public String updateBatch(List<Object> list) {
        List<String> updateColumns = Lists.newArrayList();
        // 遍歷這個集合的每一列，拼接case
        for (MybatisColumn mybatisColumn : getMybatisTable().getMybatisColumnList()) {
            // 判斷集合中某個欄位是否都為null
            boolean nullFlag = false;
            boolean updateTable = mybatisColumn.isUpdatable();
            for (Object o : list) {
                Object value = ReflectionUtils.getFieldValue(o, mybatisColumn.getFieldName());
                if (value != null) {
                    // 有值不為null，就更新
                    nullFlag = true;
                    break;
                }
            }
            updateTable = nullFlag;
            if (getMybatisTable().getId() != null && getMybatisTable().getId().getName().equals(mybatisColumn.getName())) {
                // id不修改
                updateTable = false;
            } else if (getMybatisTable().getVersion() != null && getMybatisTable().getVersion().getName().equals(mybatisColumn.getName())) {
                // 樂觀鎖，更新
                StringBuilder sb = new StringBuilder();
                sb.append(mybatisColumn.getName()).append(" = ").append(mybatisColumn.getName()).append(" + 1 ");
                updateColumns.add(sb.toString());
                continue;
            }
            if (updateTable) {
                // 如果可以修改，每個欄位都判斷這個list的case
                StringBuilder sb = new StringBuilder();
                sb.append(mybatisColumn.getName()).append(" = CASE id ");
                for (int i = 0; i < list.size(); i++) {
                    Object value = ReflectionUtils.getFieldValue(list.get(i), mybatisColumn.getFieldName());
                    // 獲取欄位值，空的不更新
                    if (value != null) {
                        sb.append(" WHEN ");
                        sb.append("#{list[").append(i).append("].id}");
                        sb.append(" THEN ");
                        sb.append("#{list[").append(i).append("].").append(mybatisColumn.getFieldName()).append("}");
                    }
                }
                sb.append(" ELSE ");
                sb.append(mybatisColumn.getName());
                sb.append(" END ");
                updateColumns.add(sb.toString());
            }
        }
        StringBuilder sb = new StringBuilder("UPDATE ");
        sb.append(getMybatisTable().getName());
        sb.append(" SET ");
        sb.append(StringUtils.join(updateColumns, ","));
        sb.append(" WHERE ");
        sb.append(getMybatisTable().getId().getName());
        sb.append(" IN (");
        List<String> ids = Lists.newArrayList();
        for (int i = 0; i < list.size(); i++) {
            // 獲取id集合
            ids.add("#{list[" + i + "].id}");
        }
        sb.append(StringUtils.join(ids, ","));
        sb.append(" ) ");
        // 拼接樂觀鎖
        return sb.toString();
    }

    /**
     * 批量保存
     *
     * @param list
     * @return
     */
    public String saveBatch(List<Object> list) {
        List<String> columnNames = Lists.newArrayList();
        List<String> fieldNames = Lists.newArrayList();
        List<String> insertValues = Lists.newArrayList();
        MybatisTable mybatisTable = getMybatisTable();
        for (MybatisColumn mybatisColumn : mybatisTable.getMybatisColumnList()) {
            boolean insertTable = getInsertTable(getMybatisTable(), mybatisColumn);
            if (insertTable) {
                columnNames.add(mybatisColumn.getName());
                fieldNames.add(mybatisColumn.getFieldName());
            }
        }
        StringBuilder sb = new StringBuilder("INSERT INTO ");
        sb.append(getMybatisTable().getName());
        sb.append(" (");
        sb.append(StringUtils.join(columnNames, ","));
        sb.append(") ");
        sb.append(" VALUES");
        for (int i = 0; i < list.size(); i++) {
            List<String> insertItem = Lists.newArrayList();
            StringBuilder insertValue = new StringBuilder("(");
            for (String fieldName : fieldNames) {
                insertItem.add("#{list[" + i + "]." + fieldName + "}");
            }
            insertValue.append(StringUtils.join(insertItem, ","));
            insertValue.append(")");
            insertValues.add(insertValue.toString());
        }
        sb.append(StringUtils.join(insertValues, ","));
        return sb.toString();
    }

    /**
     * 查詢所有
     *
     * @return
     */
    public String findAll() {
        MybatisColumn deleted = getMybatisTable().getDeleted();
        String sql;
        if (deleted != null) {
            // 刪除列不為空，說明有注解
            sql = "SELECT " + getMybatisTable().getName() + "0.* FROM " + getMybatisTable().getName() +
                    " AS " + getMybatisTable().getName() + "0 WHERE " + getMybatisTable().getName() + "0." + deleted.getName() + "=" + deleted.getNotDelete();
        } else {
            sql = "SELECT " + getMybatisTable().getName() + "0.* FROM " + getMybatisTable().getName() + " AS " + getMybatisTable().getName() + "0";
        }
        return sql;
    }

    /**
     * 根據id查詢
     *
     * @param id
     * @return
     */
    public String findById(Object id) {
        MybatisColumn deleted = getMybatisTable().getDeleted();
        String sql;
        if (deleted != null) {
            // 刪除列不為空，說明有注解
            int notDeleteValue = deleted.getNotDelete();
            sql = "SELECT " + getMybatisTable().getName() + "0.* FROM " + getMybatisTable().getName() + " AS " + getMybatisTable().getName() + "0 WHERE "
                    + getMybatisTable().getName() + "0." + getMybatisTable().getId().getName() + "=#{id} AND " +
                    getMybatisTable().getName() + "0." + deleted.getName() + "=" + notDeleteValue;
        } else {
            sql = "SELECT " + getMybatisTable().getName() + "0.* FROM " + getMybatisTable().getName() +
                    " AS " + getMybatisTable().getName() + "0 WHERE " +
                    getMybatisTable().getName() + "0." + getMybatisTable().getId().getName() + "=#{id}";
        }
        return sql;
    }

    /**
     * 使用實體類屬性作為參數查詢，只返回一個
     *
     * @return
     */
    public String findOne(Object entity) {
        String sql = findByEntity(entity);
        sql += " LIMIT 1 ";
        return sql;
    }

    /**
     * 使用實體類屬性作為參數查詢，返回全部
     *
     * @return
     */
    public String findByEntity(Object entity) {
        MybatisColumn deleted = getMybatisTable().getDeleted();
        StringBuilder sql;
        if (deleted != null) {
            // 刪除列不為空，說明有注解
            sql = new StringBuilder("SELECT " + getMybatisTable().getName() + "0.* FROM " + getMybatisTable().getName() + " AS " + getMybatisTable().getName() + "0 WHERE " +
                    getMybatisTable().getName() + "0." + deleted.getName() + "=" + deleted.getNotDelete());
        } else {
            sql = new StringBuilder("SELECT " + getMybatisTable().getName() + "0.* FROM " + getMybatisTable().getName() + " AS " + getMybatisTable().getName() + "0 WHERE 1 = 1 ");
        }
        List<String> columnNames = Lists.newArrayList();
        List<String> fieldNames = Lists.newArrayList();
        int index = 0;
        // 獲取表名的所有欄位，遍歷
        for (MybatisColumn mybatisColumn : getMybatisTable().getMybatisColumnList()) {
            // 調用反射工具類，獲取這個欄位的值
            Object value = ReflectionUtils.getFieldValue(entity, mybatisColumn.getFieldName());
            if (value != null) {
                // 值不為空，表示有這個參數
                columnNames.add(getMybatisTable().getName() + "0." + mybatisColumn.getName());
                fieldNames.add("#{" + mybatisColumn.getFieldName() + "}");
                index++;
            }
        }

        for (int i = 0; i < index; i++) {
            sql.append(" AND ").append(columnNames.get(i)).append(" = ").append(fieldNames.get(i));
        }
        return sql.toString();
    }

    /**
     * 根據id集合查詢
     *
     * @param list
     * @return
     */
    public String findByIds(List<Object> list) {
        if (list != null && list.size() > 0) {
            List<String> values = Lists.newArrayList();
            for (int i = 0; i < list.size(); i++) {
                values.add("#{list[" + i + "]}");
            }
            MybatisColumn deleted = getMybatisTable().getDeleted();
            String sql;
            if (deleted != null) {
                // 刪除列不為空，說明有注解
                sql = "SELECT " + getMybatisTable().getName() + "0.* FROM " + getMybatisTable().getName() + " AS " + getMybatisTable().getName() + "0 WHERE " +
                        getMybatisTable().getName() + "0." + getMybatisTable().getId().getName() + " IN (" + StringUtils.join(values, ",") + ") AND " + deleted.getName() + "=" + deleted.getNotDelete();
            } else {
                sql = "SELECT " + getMybatisTable().getName() + "0.* FROM " + getMybatisTable().getName() + " AS " + getMybatisTable().getName() + "0 WHERE " +
                        getMybatisTable().getName() + "0." + getMybatisTable().getId().getName() + " IN (" + StringUtils.join(values, ",") + ")";
            }
            return sql;

        } else {
            return "SELECT " + getMybatisTable().getId().getName() + " FROM " + getMybatisTable().getName() + " WHERE 1=2 ";
        }
    }

    /**
     * 查詢總數
     *
     * @return
     */
    public String count() {
        MybatisColumn deleted = getMybatisTable().getDeleted();
        String sql;
        if (deleted != null) {
            // 刪除列不為空，說明有注解
            sql = "SELECT COUNT(" + getMybatisTable().getName() + "0." + getMybatisTable().getId().getName() + ") FROM " + getMybatisTable().getName() + " WHERE " + getMybatisTable().getName() + "0." + deleted.getName() + "=" + deleted.getNotDelete();
        } else {
            sql = "SELECT COUNT(" + getMybatisTable().getName() + "0." + getMybatisTable().getId().getName() + ") FROM " + getMybatisTable().getName() + " AS " + getMybatisTable().getName() + "0";
        }
        return sql;
    }

    /**
     * 根據id刪除
     *
     * @param id
     * @return
     */
    public String removeById(Object id) {
        MybatisColumn deleted = getMybatisTable().getDeleted();
        String sql;
        if (deleted != null) {
            // 刪除列不為空，說明有注解
            sql = "UPDATE " + getMybatisTable().getName() + " SET " + deleted.getName() + "= " + deleted.getDeleted() + " WHERE " + getMybatisTable().getId().getName() + "=#{id}";
        } else {
            sql = "DELETE FROM " + getMybatisTable().getName() + " WHERE " + getMybatisTable().getId().getName() + "=#{id}";
        }
        return sql;
    }

    /**
     * 批量刪除
     *
     * @param list
     * @return
     */
    public String removeByIds(List<Object> list) {
        if (list != null && list.size() > 0) {

            List<String> values = Lists.newArrayList();
            for (int i = 0; i < list.size(); i++) {
                values.add("#{list[" + i + "]}");
            }
            MybatisColumn deleted = getMybatisTable().getDeleted();
            String sql;
            if (deleted != null) {
                // 刪除列不為空，說明有注解
                sql = "UPDATE " + getMybatisTable().getName() + " SET " + deleted.getName() + "= " + deleted.getDeleted() + " WHERE " + getMybatisTable().getId().getName() + " IN (" + StringUtils.join(values, ",") + ")";
            } else {
                sql = "DELETE FROM " + getMybatisTable().getName() + " WHERE " + getMybatisTable().getId().getName() + " IN (" + StringUtils.join(values, ",") + ")";
            }
            return sql;
        } else {
            return "DELETE FROM " + getMybatisTable().getName() + " WHERE 1=2 ";
        }
    }

    /**
     * 獲取要插入的列
     *
     * @param mybatisTable
     * @param mybatisColumn
     * @return
     */
    private boolean getInsertTable(MybatisTable mybatisTable, MybatisColumn mybatisColumn) {
        // 獲取這個列的插入標識
        boolean insertTable = mybatisColumn.isInsertTable();
        if (getMybatisTable().getId() != null && getMybatisTable().getId().getName().equals(mybatisColumn.getName())) {
            // 如果表有id，並且當前欄位是id
            insertTable = !GenerationType.IDENTITY.name().equals(getMybatisTable().getGenerationType());
        } else if (getMybatisTable().getVersion() != null && getMybatisTable().getVersion().getName().equals(mybatisColumn.getName())) {
            // 如果該表有樂觀鎖，並且該欄位為樂觀鎖，插入
            insertTable = true;
        } else if (getMybatisTable().getCreatedBy() != null && getMybatisTable().getCreatedBy().getName().equals(mybatisColumn.getName())) {
            // 如果該表有創建人，並且該欄位為創建人，插入
            insertTable = true;
        } else if (getMybatisTable().getCreatedDate() != null && getMybatisTable().getCreatedDate().getName().equals(mybatisColumn.getName())) {
            // 如果該表有創建時間，並且該欄位為創建時間，插入
            insertTable = true;
        } else if (getMybatisTable().getUpdateBy() != null && getMybatisTable().getUpdateBy().getName().equals(mybatisColumn.getName())) {
            // 如果該表有修改人，並且該欄位為修改人，插入
            insertTable = true;
        } else if (getMybatisTable().getUpdateDate() != null && getMybatisTable().getUpdateDate().getName().equals(mybatisColumn.getName())) {
            // 如果該表有修改時間，並且該欄位為修改時間，插入
            insertTable = true;
        }
        // 返回是否插入的狀態
        return insertTable;
    }

}
