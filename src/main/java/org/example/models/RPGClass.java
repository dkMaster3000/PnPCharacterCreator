package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class RPGClass {

    String Name;
    private final List<RPGClassLevel> classLvls = new ArrayList<>();

    public RPGClass(String name) {
        Name = name;
    }

    public List<RPGClassLevel> getClassLvls() {
        return classLvls;
    }

    @Override
    public String toString() {
        return "\nName=" + Name + "\n" +
                ", classLvls=" + classLvls +
                '}';
    }
}
