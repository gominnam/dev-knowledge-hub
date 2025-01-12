package com.hello.concurrency.reentrantlock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhilosopherTest {

    private ReentrantLock leftChopstick;
    private ReentrantLock rightChopstick;

    @BeforeEach
    public void setUp() {
        leftChopstick = new ReentrantLock();
        rightChopstick = new ReentrantLock();
    }

    @Test
    public void testPhilosopherEating() throws InterruptedException {
        Philosopher philosopher = new Philosopher(leftChopstick, rightChopstick);
        philosopher.start();

        // Let the philosopher run for a short time
        Thread.sleep(2000);

        // Interrupt the philosopher to stop the thread
        philosopher.interrupt();
        philosopher.join();

        // Verify that the philosopher attempted to eat
        assertTrue(leftChopstick.isLocked() || rightChopstick.isLocked());
    }
}