package com.spring.learn.service;

import com.spring.learn.common.db.annotation.Master;
import com.spring.learn.common.db.annotation.Slave;
import com.spring.learn.common.entity.Coffee;
import org.joda.money.Money;

/**
 *
 * @author J.zhu
 * @date 2019/7/1
 */
public interface CoffeeService {

    /**
     * ID查询Coffee
     * @param id
     * @return
     */
    @Slave
    Coffee getCoffeeById(Long id);

    /**
     * 保存Coffee
     * @param name 名称
     * @param price 价格
     * @return
     */
    @Master
    int saveCoffee(String name, Money price);
}
