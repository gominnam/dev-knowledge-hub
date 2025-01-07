package com.hello.concurrency.threadslocks;

public class Thread {

    public void runWithYield() throws InterruptedException {
        java.lang.Thread myThread = new java.lang.Thread() {
            public void run() {
                System.out.println("Hello this new thread");
            }
        };

        myThread.start(); // start() 메서드를 호출하면 새로운 스레드가 생성 run() 메서드가 실행된다.
        java.lang.Thread.yield(); // 현재 실행 중인 스레드가 사용 중인 프로세서를 양보할 용의가 있음을 스케줄러에게 알려주는 힌트.
        System.out.println("Hello this main thread");
        myThread.join(); // join() 메서드를 호출하면 해당 스레드가 종료될 때까지 기다린다.
    }

    public void run() throws InterruptedException {
        java.lang.Thread myThread = new java.lang.Thread() {
            public void run() {
                System.out.println("Hello this new thread");
            }
        };

        myThread.start();
        System.out.println("Hello this main thread");
        myThread.join();
    }
}

/*

출력 결과는
> Task :com.hello.concurrency.threadslocks.ThreadTest.main()
Hello this main thread
Hello this new thread

> Task :com.hello.concurrency.threadslocks.ThreadTest.main()
Hello this new thread
Hello this main thread

이 두 가지 경우가 있다.

yield()를 호출하지 않으면 새로운 스레드를 생성하는 데 따르는 오버헤드 때문에 메인 스레드의 println()가 먼저 출력될 가능성이 크다.
- 테스트 코드에서는 맨처음 호출을 제외하고 main thread가 먼저 출력되는 경우가 많다.



 */