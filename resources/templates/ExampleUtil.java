package com.atmo.atmo.util;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

/**
 * 用于Jpa Example查询的工具
 */
public class ExampleUtil {


    public static ExampleMatcher getIgnoreNullMatcher() {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreNullValues();
        return exampleMatcher;
    }

    /**
     * 返回简单的Example匹配,默认策略是只匹配非空的字段
     *
     * @param t
     * @return
     */
    public static <T> Example<T> getSimpleExample(T t) {
        Example<T> example = Example.of(t, getIgnoreNullMatcher());
        return example;
    }
}
