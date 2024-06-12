package org.example.loaders;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.mainframe.MainFrame;
import org.example.models.Race;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RaceLoader {

    //to simplify MainFrame and improve its readability
    public static List<Race> getRaces() {
        Sheet raceSheet = MainFrame.workbook.getSheet("Rassen");

        return getRacesFromMap(LoaderUtils.getMap(raceSheet));
    }

    //main function
    public static List<Race> getRacesFromMap(Map<Integer, List<String>> raceData) {
        List<Race> races = new ArrayList<>();
        int activeRace = 0;

        for (List<String> list : raceData.values()) {
            String identifier = list.getFirst();
            switch (identifier) {
                case "Name" -> {
                    Race newRace = new Race(list.get(1));
                    races.add(newRace);
                    activeRace = races.size() - 1;
                }
                case "Größe" -> races.get(activeRace).setHeight(list.get(1));
                case "Gewicht" -> races.get(activeRace).setWeight(list.get(1));
                case "HP" -> races.get(activeRace).setHp(list.get(1));
                case "Bewegungsreichweite" -> races.get(activeRace).setMovement(Integer.parseInt(list.get(1)));
                case "Effekte" -> {
                    for (int i = 1; i < list.size(); i++) {
                        races.get(activeRace).addBuffs(list.get(i));
                    }
                }
                case "Debuffs" -> {
                    for (int i = 1; i < list.size(); i++) {
                        races.get(activeRace).addDebuffs(list.get(i));
                    }
                }
                case "Auswahl" -> {
                    List<String> choices = new ArrayList<>();
                    for (int i = 1; i < list.size(); i++) {
                        choices.add(list.get(i));
                    }
                    races.get(activeRace).addChoices((choices));
                }

            }
        }

        return races;
    }
}
