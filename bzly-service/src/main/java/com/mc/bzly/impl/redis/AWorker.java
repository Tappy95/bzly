package com.mc.bzly.impl.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public abstract class AWorker<T> {
	
	public static RedisLockServiceImpl redisLockService = new RedisLockServiceImpl();
	
	final long timeOut = 2 * 60 * 1000;// 分钟
	final long startTime = System.currentTimeMillis();
	final BlockingQueue<Object> queue = new LinkedBlockingQueue<Object>(10);
	private static final Logger log = LoggerFactory.getLogger(AWorker.class);
	final List<String> message = new ArrayList<String>();

	public abstract String call(T request) throws Exception;

	public String execute(Object obj, String lockKey) {
		String lockValue = lockKey +UUID.randomUUID();
		if (obj instanceof List) {
			queue.addAll((List) obj);
		} else {
			queue.add(obj);
		}
		String retObj = null;
		while (!queue.isEmpty()) {
			if (timeOut < System.currentTimeMillis() - startTime)
				break;
			if (redisLockService.lock(lockKey,lockValue)) {
				try {
					retObj = call((T) queue.take());
				} catch (Exception e) {
					log.error("队列锁异常："+e.getMessage());
					e.printStackTrace();
				} finally {
					redisLockService.unlock(lockKey,lockValue);
				}
			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					log.error("队列锁等待500ms异常："+e.getMessage());
					e.printStackTrace();
				}
			}
		}
		if (!message.isEmpty()) {
		}
		return retObj;
	}
}
