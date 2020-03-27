package com.arno.blog.framework.opt.strategy;

import java.util.List;
import java.util.Map;

/**
 * 代理類，用來執行opt
 * @author: Arno
 * @date: 2020/03/27
 * @Version 1.0
 */
public class StrategyProxy {

    private BaseOptStrategy strategy;

    public StrategyProxy(BaseOptStrategy strategy) {
        this.strategy = strategy;
    }

    public void sqlOptHandler(Map<String, Object> params, String asTable, List<String> expresses, String key) {
        strategy.sqlOptHandler(params, asTable, expresses, key);
    }
}
