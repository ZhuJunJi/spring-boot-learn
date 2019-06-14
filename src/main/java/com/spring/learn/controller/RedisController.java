package com.spring.learn.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.spring.learn.common.entity.Result;
import com.spring.learn.common.entity.User;
import com.spring.learn.common.redis.util.RedisUtil;
import com.spring.learn.common.redis.util.RedissLockUtil;
import com.spring.learn.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;

/**
 * @author J.zhu
 * @date 2019/6/1
 */
@RequestMapping("/redis")
@RestController
@Slf4j
public class RedisController {

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();

    /**
     * 调整队列数 拒绝服务
     */
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("seckill-pool-%d").build();

    private static ExecutorService executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1,
            10L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10000), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    @Autowired
    private SeckillService seckillService;

    @GetMapping("/saveUser/{userName}/{age}")
    @ResponseBody
    public User set(@PathVariable String userName, @PathVariable Integer age) {
        RedisUtil.set(userName, new User(userName, age));
        return RedisUtil.get(userName);
    }

    @GetMapping("/getUser/{userName}")
    @ResponseBody
    public User get(@PathVariable String userName) {
        return RedisUtil.get(userName);
    }

    @GetMapping("/lock/{lockKey}")
    @ResponseBody
    public String lock(@PathVariable String lockKey) {
        boolean b = RedissLockUtil.tryLock(lockKey, TimeUnit.SECONDS, 3, 20);
        RedissLockUtil.unlock(lockKey);
        System.out.println(b);
        return "success";
    }

    @GetMapping("/startRedisLock/{seckillId}")
    public Result startRedisLock(@PathVariable Long seckillId) {
        seckillService.resetSeckill(seckillId);
        final long killId = seckillId;
        log.info("开始秒杀一");
        for (int i = 0; i < 1000; i++) {
            final long userId = i;
            Runnable task = () -> {
                Result result = seckillService.startSeckilRedisLock(killId, userId);
                log.info("用户:{}{}", userId, result.getMessage());
            };
            executor.execute(task);
        }
        Long seckillCount = 0L;
        try {
            Thread.sleep(15000);
            seckillCount = seckillService.getSeckillCount(seckillId);
            log.info("一共秒杀出{}件商品", seckillCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Result.newSuccessResult(String.format("秒杀成功！一共秒杀出 %d 件商品",seckillCount));
    }
}
