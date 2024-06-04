package org.example.models;

public class Spell extends RPGClassChoosable {
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

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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
