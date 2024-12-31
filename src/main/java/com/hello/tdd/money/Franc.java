package com.hello.tdd.money;

public class Franc extends Money {

    Franc(int amount, String currency){
        super(amount, currency);
    }

    @Override
    Money times(int multiplier){
        return Money.franc(amount * multiplier);
    }
}
