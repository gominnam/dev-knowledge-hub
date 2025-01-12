package com.hello.concurrency.reentrantlock;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConcurrentSortedListTest {

    @Test
    public void insert_WhenCalledWithMultipleElements_ShouldInsertInSortedOrder (){
        ConcurrentSortedList csl = new ConcurrentSortedList();
        csl.insert(3);
        csl.insert(5);
        csl.insert(1);

        // Verify the order of elements
        List<Integer> elements = csl.toList();
        assertEquals(1, elements.get(0));
        assertEquals(3, elements.get(1));
        assertEquals(5, elements.get(2));
    }
}

/*

: TEST CODE Method 이름 짓기
- methodName_StateUnderTest_ExpectedBehavior.
- methodName: 테스트하려는 메서드의 이름을 명시.
- StateUnderTest: 테스트하려는 메서드의 상태를 명시.
- ExpectedBehavior: 테스트하려는 메서드의 기대 동작을 명시.


 */