package com.yitianyigexiangfa.recodedemo;

public record Person(String name, int age) {

    public Person(String name){
        this(name, 18);
    }
}
