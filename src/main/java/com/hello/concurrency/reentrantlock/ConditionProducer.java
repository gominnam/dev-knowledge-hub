package com.hello.concurrency.reentrantlock;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionProducer extends Thread {
    private final Queue<Integer> queue;
    private final int maxSize;
    private final ReentrantLock lock;
    private final Condition condition;

    public ConditionProducer(Queue<Integer> queue, int maxSize, ReentrantLock lock, Condition condition){
        this.queue = queue;
        this.maxSize = maxSize;
        this.lock = lock;
        this.condition = condition;
    }

    @Override
    public void run() {
        int i = 0;
        while(true){
            lock.lock();
            try {
                while(queue.size() == maxSize){
                    condition.await();
                }
                queue.add(i++);
                System.out.println("Produced: " + i);
                condition.signalAll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
    }
}
