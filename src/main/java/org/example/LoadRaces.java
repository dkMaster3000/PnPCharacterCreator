package org.example;

import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadRaces {

    public static Map<Integer, List<String>> getMap(Sheet raceSheet) {
        Map<Integer, List<String>> data = new HashMap<>();
        int hashRow = 0;
        for (
                Row row : raceSheet) {
            List<String> cellStrings = new ArrayList<String>();
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        cellStrings.add(cell.getRichStringCellValue().getString());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            cellStrings.add(cell.getDateCellValue() + "");
                        } else {
                            int doubleValueToInt = (int) cell.getNumericCellValue();
                            cellStrings.add(doubleValueToInt + "");
                        }
                        break;
                    case BOOLEAN:
                        cellStrings.add(cell.getBooleanCellValue() + "");
                        break;
                    case FORMULA:
                        cellStrings.add(cell.getCellFormula() + "");
                        break;
                }
            }

            if (!cellStrings.isEmpty()) {
                data.put(hashRow, cellStrings);
                hashRow++;
            }

        }
        return data;
    }

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
                case "Bewegungsreichweite" -> races.get(activeRace).setMovement(list.get(1));
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
