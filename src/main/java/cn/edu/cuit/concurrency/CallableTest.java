package cn.edu.cuit.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CallableTest implements Callable<String> {
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private static CallableTest instance;
	
	public static CallableTest getInstance() {
		if(instance == null) {
			synchronized(CallableTest.class) {
				if(instance == null) {
					instance = new CallableTest();
				}
			}
		}
		return instance;
	}

	@Override
	public String call() throws Exception {
		lock.lock();
		try {
			System.out.println("开始等待");
			condition.await();
			System.out.println("等待结束");
		} finally {
			lock.unlock();
		}
		return "结果";
	}
	
	public void wake() {
		lock.lock();
		try {
			System.out.println("加锁，唤醒");
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String args[]) throws InterruptedException, ExecutionException {
		ScheduledExecutorService executors = Executors.newScheduledThreadPool(5);
		CallableTest ct = CallableTest.getInstance();
		Future<String> future = executors.submit(ct);
		executors.execute(new Runnable() {
			
			@Override
			public void run() {
				CallableTest ct = CallableTest.getInstance();
				ct.wake();
			}
		});
		System.out.println(future.get());
		
		
	}

}
