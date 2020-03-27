package com.arno.blog.framework.provider;

import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.arno.blog.framework.opt.enums.OptEnums;
import com.arno.blog.framework.opt.strategy.StrategyProxy;
import com.arno.blog.framework.utils.Pageable;
import com.arno.blog.framework.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 分頁查詢處理邏輯（代碼比較多所有單獨列出來）
 * 前端傳值params標準：
 *
 * @author Arno
 */
@Slf4j
public class MybatisProvider extends EfficientProvider {

    public static final String FIND_AUTO_BY_PAGE = "findAutoByPage";
    public static final String FIND_BY_PARAM = "findByParam";

    /**
     * 分頁查詢
     *
     * @param page
     * @return
     * @throws ParseException
     */
    public String findAutoByPage(Pageable<?> page) throws ParseException {
        // 獲取page裡的參數
        Map<String, Object> params = page.getParams();
        StringBuilder sb = new StringBuilder();
        Integer tableKey = 0;
        // 獲取表名+key生成別名
        String asTable = getMybatisTable().getName() + tableKey++;

        sb.append("SELECT ");
        // 拼接要查詢的列名
        sb.append(getColumns(page, asTable));
        sb.append(" FROM ");
        // 獲取表名
        sb.append(getMybatisTable().getName());
        sb.append(" ");
        //添加別名
        sb.append(asTable);
        if (!params.isEmpty()) {
            // 參數不為空，拼接查詢準則和join等
            // 存儲連表的集合
            Map<String, Map<String, Object>> joinTables = Maps.newConcurrentMap();
            // 獲取where條件
            String temp = getWhere(page.getParams(), asTable, joinTables);
            // joinTables為空就直接拼接查詢準則
            if (StringUtils.isNotBlank(temp)) {
                sb.append(" WHERE ");
                if (getMybatisTable().getDeleted() != null) {
                    sb.append(asTable + "." + getMybatisTable().getDeleted().getName());
                    sb.append(" = ");
                    sb.append(getMybatisTable().getDeleted().getNotDelete());
                    if (StringUtils.isNotBlank(temp)) {
                        sb.append(" AND ");
                    }
                }
                sb.append(temp);
            }
        } else {
            if (getMybatisTable().getDeleted() != null) {
                sb.append(" WHERE ");
                sb.append(asTable + "." + getMybatisTable().getDeleted().getName());
                sb.append(" = ");
                sb.append(getMybatisTable().getDeleted().getNotDelete());
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 獲取page中泛型對應的表的列
     *
     * @param page
     * @param asTable
     * @return
     */
    private String getColumns(Pageable<?> page, String asTable) {
        // 如果前端沒有傳要查詢的列
        if (page.getColumns().isEmpty()) {
            // 拼接表名.列名，這裡就查詢所有列
            return asTable + ".*";
        } else {
            // 獲取所有前端傳進來的列（駝峰），轉成底線。
            List<String> columns = page.getColumns().stream()
                    .map(n -> asTable + "." + getMybatisTable().getColumnName(n)).collect(Collectors.toList());
            return StringUtils.join(columns, ",");
        }
    }

    /**
     * 獲取查詢準則
     *
     * @param params     參數
     * @param asTable    表別名
     * @param joinTables 連表
     * @return
     * @throws ParseException
     */
    private String getWhere(Map<String, Object> params, String asTable,
                            Map<String, Map<String, Object>> joinTables) throws ParseException {
        List<String> expresses = Lists.newArrayList();
        // 參數的key全部取出來封裝成set集合
        Set<String> cloneSet = new HashSet<>(params.keySet());
        for (String key : cloneSet) {
            // 遍歷key
            Object value = params.get(key);
            if (value != null && !"".equals(value)) {
                String optName = getOpt(key).toUpperCase();
                try {
                    String[] optAndExpressColumn = key.split("_");
                    if (optAndExpressColumn.length == 2) {
                    	OptEnums optEnums = Enum.valueOf(OptEnums.class, optName);
                        StrategyProxy factory = new StrategyProxy(optEnums.getStrategy());
                        factory.sqlOptHandler(params, asTable, expresses, key);
                    }
                } catch (Exception e) {
                    log.error("參數傳遞錯誤！", e);
                }
            }
        }
        return StringUtils.join(expresses, " and ");
    }

    /**
     * 獲取符號
     *
     * @param temp
     * @return
     */
    private String getOpt(String temp) {
        //  拆分條件和符號，取符號
        return temp.split("_")[0];
    }

}
