package com.yitianyigexiangfa.recodedemo;

import java.time.Instant;

public record User(long id, String name, Instant createdAt) {

}
