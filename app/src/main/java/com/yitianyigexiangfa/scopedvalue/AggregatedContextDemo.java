package com.yitianyigexiangfa.scopedvalue;

import java.lang.ScopedValue;
import static java.lang.ScopedValue.where;

public class AggregatedContextDemo {
    record Ctx(String reqId, String user) {}
    private static final ScopedValue<Ctx> CTX = ScopedValue.newInstance();

    public static void main(String[] args) {
        where(CTX, new Ctx("REQ-888", "carol")).run(() -> {
            var ctx = CTX.get();
            System.out.println("req=" + ctx.reqId() + ", user=" + ctx.user());
        });
    }
}