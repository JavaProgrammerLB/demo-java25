package com.yitianyigexiangfa.recodedemo;

public record Money(long cents) {
    public Money add(Money other) {
        return new Money(this.cents + other.cents);
    }

    public static Money ofYuan(long yuan){
        return new Money(yuan * 100);
    }

}
