package com.spring.learn.mapper;

import com.spring.learn.common.entity.SuccessKilled;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author J.zhu
 * @date 2019/6/14
 */
@Repository
public interface SeckillMapper {

    /**
     * 秒杀ID获取剩余数量
     * @param seckillId 秒杀ID
     * @return Long 剩余数量
     */
    Long getCountBySeckillId(@Param("seckillId") Long seckillId);

    /**
     * 保存秒杀成功信息
     * @param successKilled 秒杀成功信息
     * @return int
     */
    int saveSuccessKilled(SuccessKilled successKilled);

    /**
     * 减少库存
     * @param seckillId
     * @return
     */
    int deductedNumberBySeckillId(@Param("seckillId") Long seckillId);

    /**
     * 删除秒杀成功信息
     * @param seckillId 秒杀ID
     */
    int deleteSuccessSeckilled(@Param("seckillId") Long seckillId);

    /**
     * 重置秒杀库存
     * @param seckillId 秒杀ID
     * @return
     */
    int resetSeckill(@Param("seckillId") Long seckillId);

    /**
     * 查询秒杀售卖商品
     * @param seckillId
     * @return
     */
    Long getSeckillCount(@Param("seckillId") Long seckillId);
}
