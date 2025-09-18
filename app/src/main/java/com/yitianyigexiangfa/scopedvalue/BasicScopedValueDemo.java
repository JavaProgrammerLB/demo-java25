package com.yitianyigexiangfa.scopedvalue;

import java.lang.ScopedValue;
import static java.lang.ScopedValue.where;


public class BasicScopedValueDemo {
    // 通常声明为 private static final，作为“能力键”
    private static final ScopedValue<String> REQ_ID = ScopedValue.newInstance();
    private static final ScopedValue<String> LOG_ID = ScopedValue.newInstance();

    public static void main(String[] args) {
        var reqId = "REQ-12345";

        where(REQ_ID, reqId).run(() -> {
            handleRequest();
        });

        // 作用域外访问将失败：NoSuchElementException
        System.out.println("isBound outside? " + REQ_ID.isBound()); // false
        

        where(LOG_ID, "LOG-67890").run(() -> {
            System.out.println("isBound inside? " + LOG_ID.isBound()); // true
            System.out.println("LOG_ID inside: " + LOG_ID.get()); // LOG-67890
        });
    }

    static void handleRequest() {
        // 任意深度都能 get
        log("start");
        doWork();
        log("end");
    }

    static void doWork() {
        log("working...");
    }

    static void log(String msg) {
        System.out.println("[" + REQ_ID.get() + "] " + msg);
    }
}