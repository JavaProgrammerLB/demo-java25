package com.yitianyigexiangfa.scopedvalue;

import java.lang.ScopedValue;
import static java.lang.ScopedValue.where;

public class RebindDemo {
    private static final ScopedValue<String> X = ScopedValue.newInstance();

    public static void main(String[] args) {
        where(X, "hello").run(RebindDemo::foo);
    }

    static void foo() {
        System.out.println(X.get()); // hello
        where(X, "goodbye").run(RebindDemo::bar);
        System.out.println(X.get()); // hello（重绑定只作用在 bar 及其被调栈中）
    }

    static void bar() {
        System.out.println(X.get()); // goodbye
        where(X, "custom method").run(RebindDemo::customMethod);
        System.out.println(X.get()); // goodbye
    }

    static void customMethod(){
        System.out.println(X.get());
    }
}