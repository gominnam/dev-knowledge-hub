package com.hello.concurrency.reentrantlock;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.jupiter.api.Assertions.*;

class ConditionThreadTest {

    @Test
    public void when_conditionSignal_thenShouldWakeUp() throws InterruptedException {
        ConditionThread conditionThread = new ConditionThread();
        conditionThread.start();

        // Let the thread run for a short time
        Thread.sleep(2000);
        assertEquals(0, conditionThread.getCount());
        assertNotEquals(1, conditionThread.getCount());

        // Signal the condition to wake up the thread
        conditionThread.conditionSignal();

        // Let the thread process the signal
        Thread.sleep(1000);

        // Verify that the thread was woken up
        assertEquals(1, conditionThread.getCount());
    }

    @Test
    public void when_ProducerConsumerCondition_thenShouldWakeUp() throws InterruptedException {
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 5;
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        ConditionProducer producer = new ConditionProducer(queue, maxSize, lock, condition);
        ConditionConsumer consumer = new ConditionConsumer(queue, lock, condition);

        producer.start();
        consumer.start();
    }
}