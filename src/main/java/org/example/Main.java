package org.example;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

        FileInputStream file = new FileInputStream(new File("C:\\Users\\kunzd\\IdeaProjects\\PnPCharacterCreator\\src\\main\\resources\\PnPCharacterCreator.xlsx"));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet("Rassen");

        Map<Integer, List<String>> data = new HashMap<>();
        int hashRow = 0;
        for (Row row : sheet) {
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
                            cellStrings.add(cell.getNumericCellValue() + "");
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

        System.out.println(data);

        List<Race> races = new ArrayList<>();
        int activeRace = 0;

        for (List<String> list : data.values()) {
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

        System.out.println(races);
    }
}