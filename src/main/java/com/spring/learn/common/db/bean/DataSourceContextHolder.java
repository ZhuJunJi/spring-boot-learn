package com.spring.learn.common.db.bean;

import com.spring.learn.common.db.enums.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author J.zhu
 * @date 2019/6/3
 */
@Slf4j
public class DataSourceContextHolder {

    private static final ThreadLocal<DBTypeEnum> CONTEXTHOLDER = new ThreadLocal<>();

    private static final AtomicInteger COUNTER = new AtomicInteger(-1);

    private static final Integer REPEAT_VALUE = 999;

    public static void set(DBTypeEnum dbType) {
        try {
            CONTEXTHOLDER.set(dbType);
        }catch (Exception e){
            log.error("设置数据源错误：",e);
        }finally {
            CONTEXTHOLDER.remove();
        }

    }

    public static DBTypeEnum get() {
        try {
            return CONTEXTHOLDER.get();
        }catch (Exception e){
            log.error("获取数据源错误：",e);
        }finally {
            CONTEXTHOLDER.remove();
        }
        return null;
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        System.out.println("切换到master");
        log.debug("切换到master");
    }

    public static void slave() {
        //  轮询
        int index = COUNTER.getAndIncrement() % 2;
        if (COUNTER.get() > REPEAT_VALUE) {
            COUNTER.set(-1);
        }
        if (index == 0) {
            set(DBTypeEnum.SLAVE1);
            System.out.println("切换到slave1");
            log.debug("切换到slave1");
        }else {
            set(DBTypeEnum.SLAVE2);
            System.out.println("切换到slave2");
            log.debug("切换到slave2");
        }
    }
}
