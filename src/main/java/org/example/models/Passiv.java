package org.example.models;

public class Passiv extends RPGClassChooseable {

    public Passiv(String name, String effect, String range) {
        super(name, effect, range);
    }

    @Override
    public String toString() {
        return "Passiv{" +
                "name='" + name + '\'' +
                ", effect='" + effect + '\'' +
                ", range='" + range + '\'' +
                '}';
    }
}
