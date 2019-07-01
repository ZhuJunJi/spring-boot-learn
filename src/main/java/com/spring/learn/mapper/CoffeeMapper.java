package com.spring.learn.mapper;

import com.spring.learn.common.entity.Coffee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author J.zhu
 * @date 2019/7/1
 */
@Repository
public interface CoffeeMapper {

    /**
     * ID查询Coffee
     * @param id
     * @return
     */
    Coffee getById(@Param("id") Long id);

    /**
     * 插入Coffee
     * @param coffee 
     * @return
     */
    int insertCoffee(Coffee coffee);
}
