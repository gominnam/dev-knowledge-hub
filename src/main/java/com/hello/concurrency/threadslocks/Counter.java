package com.hello.concurrency.threadslocks;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    private static int count = 0;

    // synchronized 키워드를 사용해야 멀티스레드에서 안전하다.
    public static void increment() {
        // count는 static 변수이므로 공유 데이터
        count++;
    }

    // getCount() 또한 동기화를 해야한다. 오래된 값을 볼 수 있기 때문이다.
    public static int getCount() {
        return count;
    }

    private static final AtomicInteger atomicCount = new AtomicInteger(0);

    // AtomicInteger의 incrementAndGet() 사용
    public static void atomicIncrement() {
        atomicCount.incrementAndGet();
    }

    // AtomicInteger의 get() 사용
    public static int getAtomicCount() {
        return atomicCount.get();
    }
}

/*

1) AtomicInteger 클래스를 사용
- AtomicInteger 클래스는 멀티스레드 환경에서 안전하게 사용할 수 있는 클래스이다.
- CAS(Compare-And-Swap)를 사용해 동기화를 제공
- CAS는 하드웨어 수준에서 지원되며, 메모리의 값을 조건부로 변경하는 원자적 연산을 제공한다.

 */
