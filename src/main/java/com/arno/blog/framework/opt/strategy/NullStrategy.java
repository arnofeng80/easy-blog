package com.arno.blog.framework.opt.strategy;

import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * 處理null_xxx
 *
 * @author: Arno
 * @date: 2020/03/27
 * @Version 1.0
 */
@Getter
public class NullStrategy implements BaseOptStrategy {

    private static final String IS_NULL = "true";

    public NullStrategy() {}

    @Override
    public void sqlOptHandler(Map<String, Object> params, String asTable, List<String> expresses, String key) {
        String value = params.get(key).toString();
        if (IS_NULL.equals(value)) {
            // 拼接空
            expresses.add(asTable + "." + getExpressColumn(key) + " is null");
        } else {
            // 拼接非空
            expresses.add(asTable + "." + getExpressColumn(key) + " is not null");
        }
    }
}
