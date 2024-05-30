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
        int i = 0;
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
                data.put(i, cellStrings);
                i++;
            }

        }

        System.out.println(data);

        List<Race> races = new ArrayList<>();
        int activeRace = 0;

        for (List<String> list : data.values()) {
            String identifier = list.getFirst();
            if (Objects.equals(identifier, "Name")) {
                Race newRace = new Race(list.get(1));
                races.add(newRace);
                activeRace = races.size() - 1;
            }
        }

        System.out.println(races);
    }
}