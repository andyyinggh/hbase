package cn.edu.cuit.concurrency;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private static LockTest instance;
	
	
	public static LockTest getInstance() {
		if(instance == null) {
			synchronized (LockTest.class) {
				if(instance == null) {
					instance = new LockTest();
				}
				
			}
		}
		return instance;
	}
	
	
	public void wait1() {
		lock.lock();
		try {
			System.out.println("加锁，等待");
			condition.await();
			System.out.println("执行结束");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void singalAll1() {
		lock.lock();
		try {
			System.out.println("加锁，唤醒");
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		new Thread(new Runnable() {

			@Override
			public void run() {
				LockTest lt = LockTest.getInstance();
				lt.wait1();
			}
			
		}).start();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				LockTest lt = LockTest.getInstance();
				lt.singalAll1();
			}
			
		}).start();
		
	}


}
