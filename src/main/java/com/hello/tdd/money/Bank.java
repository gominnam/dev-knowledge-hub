package com.hello.tdd.money;

public class Bank {

    Money reduce(Expression source, String to) {
        return source.reduce(to);
    }
}
