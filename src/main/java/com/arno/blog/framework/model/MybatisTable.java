package com.arno.blog.framework.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arno.blog.framework.utils.StringUtils;

import lombok.Data;

/**
 * 存儲資料表
 *
 * @author Arno
 */
@Data
public class MybatisTable {
    private String name;
    private String category;
    private String schema;
    /**
     * 欄位集合
     */
    private List<MybatisColumn> mybatisColumnList = new ArrayList<>(12);
    /**
     * 屬性和欄位的映射
     */
    private Map<String, String> columnMapping = new HashMap<>(32);
    /**
     * id列
     */
    private MybatisColumn id;
    /**
     * 創建者列
     */
    private MybatisColumn createdBy;
    /**
     * 創建者姓名列
     */
    private MybatisColumn createdName;
    /**
     * 創建時間列
     */
    private MybatisColumn createdDate;
    /**
     * 修改者列
     */
    private MybatisColumn updateBy;
    /**
     * 修改者姓名列
     */
    private MybatisColumn updateName;
    /**
     * 修改時間列
     */
    private MybatisColumn updateDate;
    /**
     * 樂觀鎖列
     */
    private MybatisColumn version;
    /**
     * 邏輯刪除列
     */
    private MybatisColumn deleted;
    private String generationType;

    /**
     * 根據屬性名獲取欄位名
     *
     * @param fieldName
     * @return
     */
    public String getColumnName(String fieldName) {
        String columnName = columnMapping.get(fieldName);
        if (StringUtils.isBlank(columnName)) {
            return fieldName;
        }
        return columnName;
    }

    /**
     * 設置欄位名和屬性名映射
     * @param fieldName
     * @param columnName
     */
    public void setColumnName(String fieldName, String columnName) {
        if(fieldName.contains("_")) {
            // 包含底線，是底線風格
            columnMapping.put(fieldName, columnName);
            String underLineName = StringUtils.upperTable(fieldName);
            columnMapping.put(underLineName, columnName);
        }else {
            // 非底線，默認是駝峰
            columnMapping.put(fieldName, columnName);
            String underLineName = StringUtils.upperCharToUnderLine(fieldName);
            columnMapping.put(underLineName, columnName);
        }
    }

    public Map<String, MybatisColumn> getMybatisColumnMap() {
        Map<String, MybatisColumn> map = new HashMap<>();
        for (MybatisColumn mybatisColumn : getMybatisColumnList()) {
            map.put(mybatisColumn.getName(), mybatisColumn);
        }
        return map;
    }

    @Override
    public String toString() {
        return "MybatisTable{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", schema='" + schema + '\'' +
                ", mybatisColumnList=" + mybatisColumnList +
                ", id=" + id +
                ", createdBy=" + createdBy +
                ", createdName=" + createdName +
                ", createdDate=" + createdDate +
                ", updateBy=" + updateBy +
                ", updateName=" + updateName +
                ", updateDate=" + updateDate +
                ", version=" + version +
                ", deleted=" + deleted +
                ", generationType='" + generationType + '\'' +
                '}';
    }
}

