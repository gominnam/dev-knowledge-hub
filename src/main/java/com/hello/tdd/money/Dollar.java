package com.hello.tdd.money;

public class Dollar extends Money {

    Dollar(int amount, String currency){
        super(amount, currency);
    }

    /*
    : 반환형을 Money로 수정하면 얻는 이점
    1. 다형성을 활용 가능하게 하여 코드 유연성과 확장성 높임.
    2. 메서드가 하위 클래스와 함께 작동할 수 있게 하여 코드 재사용성을 촉진한다.
    3. 주상화를 통해 하위 클래스의 구체적인 구현 세부사항을 숨기고, 추상화 원칙 준수
     */
    @Override
    Money times(int multiplier){
       return Money.dollar(amount * multiplier);
    }
}
