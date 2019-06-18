package com.spring.learn.common.utils;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.*;

/**
 * id自增器（雪花算法）
 * Created by J.zhu on 2019/6/17.
 */
@Slf4j
public class SnowFlake {
    private final static long TWEPOCH = 12888349746579L;
    /**
     * 机器标识位数
     */
    private final static long WORKER_ID_BITS = 5L;
    /**
     * 数据中心标识位数
     */
    private final static long DATA_CENTER_ID_BITS = 5L;
    /**
     * 毫秒内自增位数
     */
    private final static long SEQUENCE_BITS = 12L;
    /**
     * 机器ID偏左移12位
     */
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据中心ID左移17位
     */
    private final static long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * 时间毫秒左移22位
     */
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    /**
     * sequence掩码，确保sequnce不会超出上限
     */
    private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 上次时间戳
     */
    private static long lastTimestamp = -1L;
    /**
     * 序列
     */
    private long sequence = 0L;
    /**
     * 服务器ID
     */
    private long workerId = 1L;
    private static long workerMask = ~(-1L << WORKER_ID_BITS);
    /**
     * 进程编码
     */
    private long processId = 1L;
    private static long processMask = ~(-1L << DATA_CENTER_ID_BITS);

    private static SnowFlake snowFlake = null;

    static {
        snowFlake = new SnowFlake();
    }

    public static synchronized long nextId() {
        return snowFlake.getNextId();
    }

    private SnowFlake() {

        //获取机器编码
        this.workerId = this.getMachineNum();
        //获取进程编码
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        this.processId = Long.valueOf(runtimeMXBean.getName().split("@")[0]);

        //避免编码超出最大值
        this.workerId = workerId & workerMask;
        this.processId = processId & processMask;
    }

    private static void run() {
        System.out.println(SnowFlake.nextId());
    }

    public synchronized long getNextId() {
        //获取时间戳
        long timestamp = timeGen();
        //如果时间戳小于上次时间戳则报错
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //如果时间戳与上次时间戳相同
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1，与sequenceMask确保sequence不会超出上限
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) | (processId << DATA_CENTER_ID_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
        return nextId;
    }

    /**
     * 再次获取时间戳直到获取的时间戳与现有的不同
     *
     * @param lastTimestamp
     * @return 下一个时间戳
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取机器编码
     *
     * @return
     */
    private long getMachineNum() {
        long machinePiece;
        StringBuilder sb = new StringBuilder();
        Enumeration<NetworkInterface> e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }
        assert e != null;
        while (e.hasMoreElements()) {
            NetworkInterface ni = e.nextElement();
            sb.append(ni.toString());
        }
        machinePiece = sb.toString().hashCode();
        return machinePiece;
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("snowflake-pool-%d").build();
        ThreadPoolExecutor singleThreadPool = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        List<Callable<Long>> computeTasks = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            computeTasks.add(new ComputeTask());
        }
        singleThreadPool.invokeAll(computeTasks);


    }
    static class ComputeTask implements Callable<Long> {

        @Override
        public Long call() {
            Long id = SnowFlake.nextId();
            System.out.println(id);
            log.info("生成ID：{}",id);
            return id;
        }
    }

}

