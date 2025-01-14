package com.hello.concurrency.reentrantlock;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionConsumer extends Thread {
    private final Queue<Integer> queue;
    private final ReentrantLock lock;
    private final Condition condition;

    public ConditionConsumer(Queue<Integer> queue, ReentrantLock lock, Condition condition) {
        this.queue = queue;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (queue.isEmpty()) {
                    condition.await();
                }
                int item = queue.poll();
                System.out.println("Consumed: " + item);
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}