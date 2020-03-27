package com.arno.blog.framework.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * 分頁類
 * 只要參數中帶有Pageable，則視為分頁
 * 框架專用。手寫SQL語句時禁止使用
 *
 * @author Arno
 */
@Data
public class Pageable<T> implements Serializable {

	private static final long serialVersionUID = -7328147293016760288L;

	/**
     * 起始索引
     */
    private Integer index = 0;

    /**
     * 當前頁
     */
    private Integer currentPage = 1;

    /**
     * 每頁顯示條數
     */
    private Integer pageSize = 10;

    /**
     * 總條數
     */
    private Integer totalCount;

    /**
     * 總頁數
     */
    private Integer totalPage;

    /**
     * 其他參數
     */
    private Map<String, Object> params = new HashMap<>();

    /**
     * 排序參數
     */
    private Map<String, String> sorted = new HashMap<>();

    /**
     * 每頁顯示資料
     */
    private List<T> list = new ArrayList<>();

    /**
     * 自訂查詢列（駝峰規則）
     */
    private List<String> columns = new ArrayList<>();

    @Override
    public String toString() {
        return "Pageable{" +
                "index=" + index +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", params=" + params +
                ", sorted=" + sorted +
                ", list=" + list +
                ", columns=" + columns +
                '}';
    }

    public void setSorted(Map<String, String> sorted) {
        // 獲取排序列，如果不為空，駝峰轉底線
        if (!CollectionUtils.isEmpty(sorted)) {
            // 存儲新的排序列
            Map<String, String> newSort = Maps.newConcurrentMap();
            for (Map.Entry<String, String> entry : sorted.entrySet()) {
                String key = entry.getKey();
                // 獲取排序方式，處理排序方式為asc或者desc
                String sortOrder = entry.getValue();
                if (StringUtils.isBlank(sortOrder)) {
                    // 有排序列就一定有排序方式，如果沒有就設置預設值
                    newSort.put(key, "asc");
                } else {
                    // 判斷排序方式
                    if (sortOrder.length() >= 3 && sortOrder.trim().startsWith("asc")) {
                        // 如果長度大於3並且以asc開頭，就視為正序
                        newSort.put(key, "asc");
                    }
                    if (sortOrder.length() >= 4 && sortOrder.trim().startsWith("desc")) {
                        // 如果長度大於4並且以desc開頭，就視為倒序
                        newSort.put(key, "desc");
                    }
                }
            }
            this.sorted = newSort;
        }
    }
}
