package com.arno.blog.framework.opt.strategy;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.arno.blog.framework.utils.StringUtils;

import lombok.Getter;

/**
 * 處理btw_xxx
 *
 * @author: Arno
 * @date: 2020/03/27
 * @Version 1.0
 */
@Getter
public class BtwStrategy implements BaseOptStrategy {

    /**
     * 時間格式陣列
     */
    private static final String[] DATE_PATTERNS = new String[]{"yyyy-MM-dd HH:mm", "yyyy-MM", "yyyyMM", "yyyy/MM",
            "yyyy-MM-dd", "yyyyMMdd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyyMMddHHmmss",
            "yyyyMMddHHmm", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm"};

    private static final String BTW_CONCAT = "~";

    public BtwStrategy() {}

    @Override
    public void sqlOptHandler(Map<String, Object> params, String asTable, List<String> expresses, String key) {
        // 是between
        String value = params.get(key).toString();
        // 默認只允許用這三種連接子
        if (value.contains(BTW_CONCAT) ||
                value.contains("--") ||
                value.contains("/")) {
            // 前面過濾了其他連接子，這裡全部處理成~
            if (!value.contains(BTW_CONCAT)) {
                value = value.replace("--", "~")
                        .replace("/", "~");
            }
            // 拆分連接子
            String startStr = value.split("~")[0];
            String endStr = value.split("~")[1];
            Object start;
            Object end;
            try {
                // 時間處理
                start = DateUtils.parseDate(startStr, DATE_PATTERNS);
                end = DateUtils.parseDate(endStr, DATE_PATTERNS);
            } catch (Exception e) {
                start = startStr;
                end = endStr;
            }
            params.put(key + "_start", start);
            params.put(key + "_end", end);
            if (!StringUtils.isBlank(startStr) && !StringUtils.isBlank(endStr)) {
                // 拼接時間區間
                expresses.add(asTable + "." + getExpressColumn(key) + " between #{params." + key + "_start"
                        + "} and #{params." + key + "_end" + "}");
            }
        }
    }
}
