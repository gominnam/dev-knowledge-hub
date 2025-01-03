package com.hello.tdd.study;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudyTest {

    /*
     list
     -
     */

    @Test
    public void testClassInfo(){
        Class pClass = new Class();
        pClass.subject = new Subject("수학");
        assertEquals(pClass.subject.name, "수학");
    }
}
