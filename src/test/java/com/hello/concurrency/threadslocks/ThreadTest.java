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
}