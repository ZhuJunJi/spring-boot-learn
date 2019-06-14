package com.spring.learn.service.impl;

import com.spring.learn.common.annotation.LockKey;
import com.spring.learn.common.annotation.RedisLock;
import com.spring.learn.common.entity.Result;
import com.spring.learn.common.entity.SuccessKilled;
import com.spring.learn.common.exception.CommonBizException;
import com.spring.learn.common.exception.ExpCodeEnum;
import com.spring.learn.mapper.SeckillMapper;
import com.spring.learn.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;

/**
 *
 * @author J.zhu
 * @date 2019/6/14
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SeckillMapper seckillMapper;

    @Override
    public void resetSeckill(Long seckillId) {
        seckillMapper.deleteSuccessSeckilled(seckillId);
        seckillMapper.resetSeckill(seckillId);
    }

    @Override
    public Long getSeckillCount(Long seckillId) {
        return seckillMapper.getSeckillCount(seckillId);
    }

    @Override
    @RedisLock
    @Transactional
    public Result startSeckilRedisLock(@LockKey Long seckillId, Long userId) {
        try {
            /**
             * 尝试获取锁，最多等待3秒，上锁以后20秒自动解锁（实际项目中推荐这种，以防出现死锁）、这里根据预估秒杀人数，设定自动释放锁时间.
             * 看过博客的朋友可能会知道(Lcok锁与事物冲突的问题)：https://blog.52itstyle.com/archives/2952/
             * 分布式锁的使用和Lock锁的实现方式是一样的，但是测试了多次分布式锁就是没有问题，当时就留了个坑
             * 闲来咨询了《静儿1986》，推荐下博客：https://www.cnblogs.com/xiexj/p/9119017.html
             * 先说明下之前的配置情况：Mysql在本地，而Redis是在外网。
             * 回复是这样的：
             * 这是因为分布式锁的开销是很大的。要和锁的服务器进行通信，它虽然是先发起了锁释放命令，涉及网络IO，延时肯定会远远大于方法结束后的事务提交。
             * ==========================================================================================
             * 分布式锁内部都是Runtime.exe命令调用外部，肯定是异步的。分布式锁的释放只是发了一个锁释放命令就算完活了。真正其作用的是下次获取锁的时候，要确保上次是释放了的。
             * 就是说获取锁的时候耗时比较长，那时候事务肯定提交了就是说获取锁的时候耗时比较长，那时候事务肯定提交了。
             * ==========================================================================================
             * 周末测试了一下，把redis配置在了本地，果然出现了超卖的情况；或者还是使用外网并发数增加在10000+也是会有问题的，之前自己没有细测，我的锅。
             * 所以这钟实现也是错误的，事物和锁会有冲突，建议AOP实现。
             */
            Long number =  seckillMapper.getCountBySeckillId(seckillId);
            if(number>0){
                SuccessKilled killed = new SuccessKilled();
                killed.setSeckillId(seckillId);
                killed.setUserId(userId);
                killed.setState((short)0);
                killed.setCreateTime(new Timestamp(System.currentTimeMillis()));
                seckillMapper.saveSuccessKilled(killed);
                seckillMapper.deductedNumberBySeckillId(seckillId);
            }else{
                return Result.newFailureResult(new CommonBizException(ExpCodeEnum.SECKILL_END));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.newSuccessResult("秒杀成功！");
    }
}
