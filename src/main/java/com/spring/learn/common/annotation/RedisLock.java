package com.spring.learn.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解    分布式redis锁
 * 创建者	    朱俊吉
 * 创建时间	    2019年5月9日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {
}
