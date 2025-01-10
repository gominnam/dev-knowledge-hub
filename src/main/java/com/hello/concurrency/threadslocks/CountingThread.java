package com.hello.concurrency.threadslocks;

public class CountingThread extends java.lang.Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            Counter.increment();
        }
    }
}
