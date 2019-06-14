package com.spring.learn.service;

import com.spring.learn.common.entity.Result;

/**
 *
 * @author J.zhu
 * @date 2019/6/14
 */
public interface SeckillService {

    /**
     * 秒杀 一  单个商品
     * @param seckillId 秒杀商品ID
     * @param userId 用户ID
     * @return
     */
    Result startSeckilRedisLock(Long seckillId, Long userId);

    /**
     * 重置秒杀
     * @param seckillId
     * @return
     */
    void resetSeckill(Long seckillId);

    /**
     * 查询秒杀售卖商品
     * @param seckillId
     * @return
     */
    Long getSeckillCount(Long seckillId);
}
