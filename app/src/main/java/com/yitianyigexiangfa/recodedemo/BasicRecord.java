package com.yitianyigexiangfa.recodedemo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson2.JSON;

public class BasicRecord {

    public static void main(String[] args) {
        Point point = new Point(10, 20);
        System.out.println("point.x = " + point.x());
        System.out.println("point.y = " + point.y());

        Person person = new Person("Alice");
        System.out.println("person.name = " + person.name() + ", person.age = " + person.age());

        Person person2 = new Person("Bob", 25);
        System.out.println("person2.name = " + person2.name() + ", person2.age = " + person2.age());

        var tenCents = new Money(10);
        var twentyCents = tenCents.add(new Money(10));
        System.out.println("twentyCents.cents = " + twentyCents.cents() + ", ofYuan(1) = " + Money.ofYuan(1));

        var team = new Team("Developers", new ArrayList<>(List.of("Alice", "Bob")));
        System.out.println("team.name = " + team.name() + ", members = " + JSON.toJSONString(team.members()));
        team.members().add("Charlie");
        System.out.println("team.name = " + team.name() + ", members = " + JSON.toJSONString(team.members()));

        var team2 = new CannotChangedTeam("Developers", new ArrayList<>(List.of("Alice", "Bob")));
        System.out.println("team2.name = " + team2.name() + ", members = " + JSON.toJSONString(team2.members()));
        // team2里的memebers是不可变的，如果调用teams2对members的add方法会报错
        // team2.members().add("Charlie");
    }

}
