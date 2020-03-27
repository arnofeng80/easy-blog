package com.arno.blog.framework.opt.strategy;

import com.arno.blog.framework.utils.StringUtils;
import lombok.Getter;

import java.util.List;
import java.util.Map;
/**
 * 處理gte_xxx
 *
 * @author: Arno
 * @date: 2020/03/27
 * @Version 1.0
 */
@Getter
public class GteStrategy implements BaseOptStrategy {

    public GteStrategy() {}

    @Override
    public void sqlOptHandler(Map<String, Object> params, String asTable, List<String> expresses, String key) {
        String value = params.get(key).toString();
        if (!StringUtils.isBlank(value)) {
            expresses.add(asTable + "." + getExpressColumn(key) + " >= #{params." + key + "}");
        }
    }
}
