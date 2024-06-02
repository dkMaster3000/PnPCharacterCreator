package org.example.loaders;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.mainframe.MainFrame;
import org.example.models.Talent;
import org.example.models.TalentMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TalentLoader {

    //to simplify MainFrame and improve its readability
    public static TalentMatrix getTalentMatrix() {
        Sheet raceSheet = MainFrame.workbook.getSheet("Talente");

        return getTalentMatrixFromMap(LoaderUtils.getMap(raceSheet));
    }

    //main function
    public static TalentMatrix getTalentMatrixFromMap(Map<Integer, List<String>> data) {
        List<Talent> talents = new ArrayList<>();
        List<Integer> unlockLvls = new ArrayList<>();


        for (List<String> list : data.values()) {
            String identifier = list.getFirst();
            if (Objects.equals(identifier, "Freischaltung auf den Stufen")) {
                for (int i = 1; i < list.size(); i++) {
                    unlockLvls.add(Integer.parseInt(list.get(i)));
                }
            } else {
                if (!Objects.equals(identifier, "Name")) {
                    boolean unique = list.size() == 3;
                    talents.add(new Talent(list.get(0), list.get(1), unique));
                }
            }
        }

        return new TalentMatrix(talents, unlockLvls);
    }
}
