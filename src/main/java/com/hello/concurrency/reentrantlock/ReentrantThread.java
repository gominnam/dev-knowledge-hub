package com.hello.concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantThread {
    // ReentrantLock은 'AbstractQueuedSynchronizer (AQS)'를 기반으로 구현
    // AQS는 스레드가 락을 획득하기 위해 대기열(queue)에 진입하고, 락 상태를 관리합니다.
    final ReentrantLock lock1 = new ReentrantLock();
    final ReentrantLock lock2 = new ReentrantLock();

    Thread t1 = new Thread(() -> {
        try {
            lock1.lockInterruptibly();
            Thread.sleep(1000);
            lock2.lockInterruptibly();
        } catch (InterruptedException e) {
            System.out.println("Thread t1 was interrupted");
        } finally {
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
        }
    });

    Thread t2 = new Thread(() -> {
        try {
            lock2.lockInterruptibly();
            Thread.sleep(1000);
            lock1.lockInterruptibly();
        } catch (InterruptedException e) {
            System.out.println("Thread t2 was interrupted");
        } finally {
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
        }
    });
}
