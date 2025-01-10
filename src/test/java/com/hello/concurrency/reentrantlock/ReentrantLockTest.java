package com.hello.concurrency.reentrantlock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ReentrantLockTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        //System.out 출력을 ByteArrayOutputStream으로 리다이렉트하여 테스트 중에 출력된 내용을 캡처
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testThreadInterruptionHandling() throws InterruptedException {
        ReentrantThread reentrantThread = new ReentrantThread();
        reentrantThread.t1.start();
        reentrantThread.t2.start();

        reentrantThread.t1.interrupt();

        reentrantThread.t2.join();

        assertTrue(outContent.toString().contains("Thread t1 was interrupted"));
    }
}
