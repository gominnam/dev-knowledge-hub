package com.hello.concurrency.reentrantlock;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {
    private ReentrantLock leftChopstick, rightChopstick;
    private Random random;

    public Philosopher(ReentrantLock leftChopstick, ReentrantLock rightChopstick) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        random = new Random();
    }

    public void run() {
        try {
            while(true) {
                Thread.sleep(random.nextInt(1000)); // 잠시 생각한다.
                leftChopstick.lock();
                try {
                    if(rightChopstick.tryLock(1000, TimeUnit.MILLISECONDS)) {
                        try {
                            Thread.sleep(random.nextInt(1000)); // 식사한다.
                        } finally {
                            rightChopstick.unlock();
                        }
                    } else {
                        // 오른쪽 젓가락을 집지 못했을 때 포기하고 다시 생각하는 상태로 돌아간다.
                        System.out.println("Philosopher gave up on right chopstick");
                    }
                } finally {
                    leftChopstick.unlock();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

/*

tryLock()은 무한히 지속되는 데드락은 피할 수 있지만, 그렇다고 해서 좋은 해법은 아니다.
- 데드락을 완전히 피하는게 아니라 데드락 상태에서 빠져나올 수 있는 방법만 제공.
- 모든 스레드가 동시에 타임아웃을 발생시키면 곧바로 데드락 상태에서 빠지는 것도 가능하다.
    -> 스레드가 서로 다른 타임아웃을 갖도록 하면 어느정도 완화할 수 있다.

 */