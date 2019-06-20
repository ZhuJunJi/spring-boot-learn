package com.spring.learn.common.zookeeper;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 基于curator的zookeeper分布式锁
 * 这里我们开启5个线程，每个线程获取锁的最大等待时间为5秒，为了模拟具体业务场景，方法中设置4秒等待时间。
 * 开始执行main方法，通过ZooInspector监控/curator/lock下的节点如下图：
 * @author Administrator
 */
@Component
public class CuratorUtil {

    private static String address;

	@NacosValue(value = "${zookeeper.address}")
	public void setAddress(String address) {
		CuratorUtil.address = address;
	}

	public static void main(String[] args) {
		//1、重试策略：初试时间为1s 重试3次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		//2、通过工厂创建连接
		CuratorFramework client = CuratorFrameworkFactory.newClient(address, retryPolicy);
        //3、开启连接
        client.start();
        //4 分布式锁
        final InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");
        //读写锁
        //InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/readwriter");

		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
				.setNameFormat("demo-pool-%d").build();

		//Common Thread Pool
		ExecutorService pool = new ThreadPoolExecutor(5, 200,
				0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 5; i++) {
			pool.submit(() -> {
				boolean flag = false;
				try {
					//尝试获取锁，最多等待5秒
					flag = mutex.acquire(5, TimeUnit.SECONDS);
					Thread currentThread = Thread.currentThread();
					if(flag){
						System.out.println("线程"+Thread.currentThread().getName()+"获取锁成功");

					}else{
						System.out.println("线程"+Thread.currentThread().getName()+"获取锁失败");
					}
					//模拟业务逻辑，延时4秒
					Thread.sleep(4000);
				} catch (Exception e) {
					e.printStackTrace();
				} finally{
					if(flag){
						try {
							mutex.release();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
        }
		// gracefully shutdown
		pool.shutdown();
	}
}
