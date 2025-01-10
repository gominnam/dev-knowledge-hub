package com.hello.concurrency.threadslocks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadTest {

    @Test
    void testThreadPrint() throws InterruptedException {
        Thread thread = new Thread();
        for (int i = 0; i < 10; i++) {
            thread.runWithYield();
        }
    }

    @Test
    void testThreadPrintNotYield() throws InterruptedException {
        Thread thread = new Thread();
        for (int i = 0; i < 10; i++) {
            thread.run();
        }
    }

    @Test
    void testCounterInconsistency() throws InterruptedException {
        CountingThread thread1 = new CountingThread();
        CountingThread thread2 = new CountingThread();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertNotEquals(20000, Counter.getCount());
        System.out.println(Counter.getCount());
    }

    @Test
    void testAtomicCounterConsistency() throws InterruptedException {
        AtomicCountingThread thread1 = new AtomicCountingThread();
        AtomicCountingThread thread2 = new AtomicCountingThread();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertEquals(20000, Counter.getAtomicCount());
        System.out.println(Counter.getAtomicCount());
    }
}