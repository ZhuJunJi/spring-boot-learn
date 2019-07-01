package com.spring.learn.service.impl;

import com.spring.learn.common.entity.Coffee;
import com.spring.learn.mapper.CoffeeMapper;
import com.spring.learn.service.CoffeeService;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 *
 * @author J.zhu
 * @date 2019/7/1
 */
@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Override
    public Coffee getCoffeeById(Long id) {
        return coffeeMapper.getById(id);
    }

    @Override
    public int saveCoffee(String name, Money price) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Coffee coffee = new Coffee(name,price);
        coffee.setCreateTime(now);
        coffee.setUpdateTime(now);
        return coffeeMapper.insertCoffee(coffee);
    }
}
