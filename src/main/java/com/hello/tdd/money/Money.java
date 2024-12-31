package com.hello.tdd.money;

/*
: abstract 키워드 (클래스 레벨에서는 추상클래스)
1. 직접 인스턴스 생성할 수 없으며, 다른 클래스가 상속받아야 한다.
2. 메서드에서 사용되면 추상메서드라 하며 구현부가 없으며, 상속받는 클래스에서 반드시 구현해야 합니다.
3. 공통적인 기능을 제공하면서, 구현은 하위 클래스에 위임(재사용성, 유지보수 용이)
 */
public abstract class Money {
    protected int amount;
    protected String currency;

    /*
    : 생성자
    부모 클래스에 매개변수가 있는 생성자가 있는 경우
    자식 클래스의 생성자에서 반드시 부모 클래스의 생성자를 명시적으로 호출해야 함.
    그렇지 않으면 컴파일 오류가 발생.
     */
    Money(int amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    abstract Money times(int multiplier);

    public String currency(){
        return currency;
    }

    /*
    : static 키워드
    1. 클래스 레벨에서 호출할 수 있기 때문에, 객체를 생성하지 않고 메서드 호출 가능.
    2. 특정 객체에 종속되지 않는 유틸리티 메서드를 작성할 때 유용
    3. 공통으로 사용되는 기능에 코드 중복을 줄이고 유지보수성 용이해짐
     */
    static Money dollar(int amount){
        return new Dollar(amount, "USD");
    }

    static Franc franc(int amount){
        return new Franc(amount, "CHF");
    }

    public boolean equals(Object object) {
        Money money = (Money) object;
        return amount == money.amount
                && getClass().equals(money.getClass());
    }
}
