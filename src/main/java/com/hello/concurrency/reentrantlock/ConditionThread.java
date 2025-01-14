package com.hello.concurrency.reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionThread extends Thread {
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    private static int count = 0;
    private boolean flag = false;

    public void run() {
        lock.lock();
        try {
            while(!flag){
                condition.await();//lock을 해제하고 대기상태로
                count++;
                flag = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() {
        lock.lock();
        try {
            flag = true;
            condition.signal();// 1개의 스레드만 깨움
            // condition.signalAll();// 모든 스레드를 깨움
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}
