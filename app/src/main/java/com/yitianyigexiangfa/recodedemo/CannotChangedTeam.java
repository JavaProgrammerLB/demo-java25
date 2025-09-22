package com.yitianyigexiangfa.recodedemo;

import java.util.List;

public record CannotChangedTeam(String name, List<String> members) {
    public CannotChangedTeam {
        members = List.copyOf(members);
    }
}
