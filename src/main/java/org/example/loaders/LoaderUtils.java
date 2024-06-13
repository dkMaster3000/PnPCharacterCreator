package org.example.loaders;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoaderUtils {

    public static Map<Integer, List<String>> getMap(Sheet sheet) {
        Map<Integer, List<String>> data = new HashMap<>();
        int hashRow = 0;
        for (Row row : sheet) {
            List<String> cellStrings = new ArrayList<>();
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
                    case BOOLEAN: //should never happen
                        cellStrings.add(cell.getBooleanCellValue() + "");
                        break;
                    case FORMULA: //should never happen
                        cellStrings.add(cell.getCellFormula());
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
}
