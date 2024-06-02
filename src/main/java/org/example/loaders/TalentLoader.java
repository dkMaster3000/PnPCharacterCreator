package org.example.loaders;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.mainframe.MainFrame;
import org.example.models.Talent;

import java.util.List;
import java.util.Map;

public class TalentLoader {

    //to simplify MainFrame and improve its readability
    public static List<Talent> getTalents() {
        Sheet raceSheet = MainFrame.workbook.getSheet("Talente");

        return getTalentsFromMap(LoaderUtils.getMap(raceSheet));
    }

    public static List<Talent> getTalentsFromMap(Map<Integer, List<String>> data) {
        return null;
    }
}
