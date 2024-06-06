package org.example.models;

import java.util.Objects;

public class Spell extends RPGClassChooseable {
    String tempo;
    String difficulty;

    public Spell(String name, String tempo, String difficulty, String effect, String range) {
        super(name, effect, range);
        this.tempo = tempo;
        this.difficulty = difficulty;
    }

    public String getTempo() {
        return tempo;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getDifficultyValue() {
        return switch (difficulty) {
            case "Basis" -> 1;
            case "Einfach" -> 2;
            case "Fortgeschritten" -> 3;
            case "Experte" -> 4;
            case "Legendär" -> 5;
            default -> 6;
        };
    }

    @Override
    public String toString() {
        return "Spell{" +
                "tempo='" + tempo + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", name='" + name + '\'' +
                ", effect='" + effect + '\'' +
                ", range='" + range + '\'' +
                '}';
    }
}
