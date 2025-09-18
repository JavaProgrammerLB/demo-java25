package com.yitianyigexiangfa.scopedvalue;

import static java.lang.ScopedValue.where;
import java.lang.ScopedValue;

public class MultiBindDemo {

    private static final ScopedValue<String> KEY1 = ScopedValue.newInstance();
    private static final ScopedValue<String> KEY2 = ScopedValue.newInstance();

    public static void main(String[] args) {
        where(KEY1, "value1").where(KEY2, "value2").run(() -> {
            System.out.println("KEY1: " + KEY1.get());
            System.out.println("KEY2: " + KEY2.get());
        });
    }
}
