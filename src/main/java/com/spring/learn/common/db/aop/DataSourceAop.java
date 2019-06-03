package com.spring.learn.common.db.aop;

import com.spring.learn.common.db.bean.DataSourceContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 * @author J.zhu
 * @date 2019/6/3
 * 数据源AOP
 * {@link com.spring.learn.common.db.annotation.Master Master数据源}
 * {@link com.spring.learn.common.db.annotation.Slave Slaver数据源}
 */
@Aspect
@Component
public class DataSourceAop {

    @Pointcut("@annotation(com.spring.learn.common.db.annotation.Slave)")
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.spring.learn.common.db.annotation.Master)")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DataSourceContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DataSourceContextHolder.master();
    }
}
