package org.example.models;

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
