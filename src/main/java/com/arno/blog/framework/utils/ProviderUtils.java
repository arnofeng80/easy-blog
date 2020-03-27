package com.arno.blog.framework.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.arno.blog.framework.annotation.Column;
import com.arno.blog.framework.annotation.GeneratedValue;
import com.arno.blog.framework.annotation.GenerationType;
import com.arno.blog.framework.annotation.Id;
import com.arno.blog.framework.annotation.LogicDelete;
import com.arno.blog.framework.annotation.Table;
import com.arno.blog.framework.annotation.Version;
import com.arno.blog.framework.model.MybatisColumn;
import com.arno.blog.framework.model.MybatisTable;
import com.google.common.base.CaseFormat;

/**
 * @author Arno
 */
public class ProviderUtils {
    /**
     * 表結構緩存
     */
    private static Map<String, MybatisTable> mybatisTableMap = new ConcurrentHashMap<>();

    /**
     * 根據類獲取mybatis對應的表名
     *
     * @param clazz
     * @return
     */
    public static MybatisTable getMybatisTable(Class<?> clazz) {
        // 表結構緩存裡如果沒有這張表，就獲取表名
        if (!mybatisTableMap.containsKey(clazz.getName())) {
            MybatisTable mybatisTable = new MybatisTable();
            Table table = (Table) clazz.getAnnotation(Table.class);
            if (table != null) {
                //獲取表名
                String tableName = table.name();
                if (StringUtils.isBlank(tableName)) {
                    // 如果表名是空的，類名轉表名（駝峰轉底線）
                    tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
                }
                // 表名set進去
                mybatisTable.setName(tableName);
                // 獲取所有屬性
                List<Field> fields = ReflectionUtils.getFields(clazz);
                for (Field field : fields) {
                    // 獲取屬性類型
                    Class<?> fieldClass = field.getType();
                    // 序列化不算在表裡
                    if (!"serialVersionUID".equals(field.getName())
                            // 這個欄位不對應表
                            && fieldClass.getAnnotation(Table.class) == null
                            // 不是集合
                            && !Collection.class.isAssignableFrom(fieldClass)
                            // 不是map
                            && !Map.class.isAssignableFrom(fieldClass)) {
                        // 獲取欄位
                        MybatisColumn mybatisColumn = getMybatisColumn(field);
                        // 將欄位放入表裡
                        mybatisTable.getMybatisColumnList().add(mybatisColumn);
                        mybatisTable.setColumnName(mybatisColumn.getFieldName(), mybatisColumn.getName());
                        // 檢查是否是ID
                        if (field.getAnnotation(Id.class) != null) {
                            // 設置id
                            mybatisTable.setId(mybatisColumn);
                            // id生成策略為雪花演算法
                            String generationType = GenerationType.SNOWFLAKE.name();
                            if (field.getAnnotation(GeneratedValue.class) != null) {
                                generationType = field.getAnnotation(GeneratedValue.class).strategy().name();
                            }
                            // 設置id生成策略
                            mybatisTable.setGenerationType(generationType);
                        }
                        setBaseField(mybatisTable, field, mybatisColumn);
                        // 處理屬性名和欄位名映射
                    }
                }
            }
            // 類名和表名映射
            mybatisTableMap.put(clazz.getName(), mybatisTable);
        }
        // 返回這個表名
        return mybatisTableMap.get(clazz.getName());
    }

    private static void setBaseField(MybatisTable mybatisTable, Field field, MybatisColumn mybatisColumn) {
        // 是否有樂觀鎖
        if (field.getAnnotation(Version.class) != null) {
            mybatisTable.setVersion(mybatisColumn);
        }
        // 是否為邏輯刪除
        if (field.getAnnotation(LogicDelete.class) != null) {
            LogicDelete logicDelete = field.getAnnotation(LogicDelete.class);
            mybatisColumn.setDeleted(logicDelete.deleted());
            mybatisColumn.setNotDelete(logicDelete.notDelete());
            mybatisTable.setDeleted(mybatisColumn);
        }
    }

    /**
     * 獲取列名
     *
     * @param field
     * @return
     */
    private static MybatisColumn getMybatisColumn(Field field) {
        MybatisColumn mybatisColumn = new MybatisColumn();
        mybatisColumn.setFieldName(field.getName());
        // 列名駝峰轉底線
        String name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
        // 默認插入表時使用
        boolean insertable = true;
        // 默認更新表時使用
        boolean updatable = true;
        // 默認不為空
        boolean nullable = false;
        // 獲取欄位上的Column注解
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            // 判斷列名是否為空，為空就預設是屬性名
            if (StringUtils.isNotBlank(column.name())) {
                name = column.name();
            }
            insertable = column.insertable();
            updatable = column.updatable();
            nullable = column.nullable();
        }
        mybatisColumn.setName(name);
        mybatisColumn.setInsertTable(insertable);
        mybatisColumn.setUpdatable(updatable);
        mybatisColumn.setNullable(nullable);
        mybatisColumn.setJavaType(field.getType());
        return mybatisColumn;
    }
}
