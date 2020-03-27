package com.arno.blog.framework.opt.enums;

import com.arno.blog.framework.opt.strategy.BaseOptStrategy;
import com.arno.blog.framework.opt.strategy.BtwStrategy;
import com.arno.blog.framework.opt.strategy.EndStrategy;
import com.arno.blog.framework.opt.strategy.GtStrategy;
import com.arno.blog.framework.opt.strategy.GteStrategy;
import com.arno.blog.framework.opt.strategy.LikeStrategy;
import com.arno.blog.framework.opt.strategy.LtStrategy;
import com.arno.blog.framework.opt.strategy.LteStrategy;
import com.arno.blog.framework.opt.strategy.NeqStrategy;
import com.arno.blog.framework.opt.strategy.NullStrategy;
import com.arno.blog.framework.opt.strategy.StartStrategy;

import lombok.Getter;

/**
 * opt枚舉
 *
 * @author: Arno
 * @date: 2020/03/27
 * @Version 1.0
 */
@Getter
public enum OptEnums {
    /**
     * 通過key去new對象
     */
    GT("gt", new GtStrategy()),
    LT("lt", new LtStrategy()),
    GTE("gte", new GteStrategy()),
    LTE("lte", new LteStrategy()),
    BTW("btw", new BtwStrategy()),
    LIKE("like", new LikeStrategy()),
    NULL("null", new NullStrategy()),
    NOT("not", new NeqStrategy()),
    START("start", new StartStrategy()),
    END("end", new EndStrategy()),
    ;

    private String opt;
    private BaseOptStrategy strategy;

    OptEnums(String opt, BaseOptStrategy strategy) {
        this.opt = opt;
        this.strategy = strategy;
    }
}

