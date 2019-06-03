package com.spring.learn.common.dataSource.aop;

import com.spring.learn.common.dataSource.bean.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by J.zhu on 2019/6/3.
 * 数据源AOP
 * {@link com.spring.learn.common.dataSource.annotation.Master Master数据源}
 * {@link com.spring.learn.common.dataSource.annotation.Slave Slaver数据源}
 */
@Aspect
@Component
public class DataSourceAop {

    @Pointcut("@annotation(com.spring.learn.common.dataSource.annotation.Slave)")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.spring.learn.common.dataSource.annotation.Master)")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }
}
