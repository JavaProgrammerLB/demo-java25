package com.yitianyigexiangfa.scopedvalue;

import java.lang.ScopedValue;
import static java.lang.ScopedValue.where;

public class ReadPatternsDemo {
    // 用来创建对象的方法
    private static final ScopedValue<String> USER = ScopedValue.newInstance();

    public static void main(String[] args) {
        // 未绑定时提供默认值（注意：默认值不能为 null）
        System.out.println("user or default = " + USER.orElse("anonymous"));

        // 自定义异常
        try {
            String mustHaveUser = USER.orElseThrow(() -> new IllegalStateException("missing user"));
            System.out.println(mustHaveUser);
        } catch (IllegalStateException ex) {
            System.out.println("caught: " + ex.getMessage());
        }

        // 绑定后读取
        where(USER, "alice").run(() -> {
            if (USER.isBound()) {
                System.out.println("bound user = " + USER.get());
            }
        });
    }
}