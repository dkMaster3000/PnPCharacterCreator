package org.example.models;

public abstract class RPGClassChooseable {

    String name;
    String effect;
    String range;

    public RPGClassChooseable(String name, String effect, String range) {
        this.name = name;
        this.effect = effect;
        this.range = range;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
