package org.example.loaders;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.mainframe.MainFrame;
import org.example.mainframe.UsedValues;
import org.example.models.SpellslotsMatrix;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellslotsLoader {

    //to simplify MainFrame and improve its readability
    public static SpellslotsMatrix getSpellslotsMatrix() {
        Sheet raceSheet = MainFrame.workbook.getSheet(UsedValues.SPELLSLOTS_SHEETNAME);

        return getSpellslotsMatrixFromMap(LoaderUtils.getMap(raceSheet));
    }

    //main function
    public static SpellslotsMatrix getSpellslotsMatrixFromMap(Map<Integer, List<String>> spellslotsData) {
        HashMap<Integer, HashMap<String, Integer>> spellslotsMap = new HashMap<>();
        String simpleKey = "";
        String advancedkey = "";
        String expertKey = "";
        String legendaryKey = "";

        int simpleDivider = 0;
        int advancedDivider = 0;
        int expertDivider = 0;
        int legendaryDivider = 0;

        for (List<String> list : spellslotsData.values()) {
            String identifier = list.getFirst();

            if (identifier.equals("Lvl")) {
                simpleKey = list.get(1);
                advancedkey = list.get(2);
                expertKey = list.get(3);
                legendaryKey = list.get(4);
            } else if (identifier.equals("Divider")) {
                simpleDivider = Integer.parseInt(list.get(1));
                advancedDivider = Integer.parseInt(list.get(2));
                expertDivider = Integer.parseInt(list.get(3));
                legendaryDivider = Integer.parseInt(list.get(4));


            } else {
                String finalSimpleKey = simpleKey;
                String finalAdvancedkey = advancedkey;
                String finalExpertKey = expertKey;
                String finalLegendaryKey = legendaryKey;
                spellslotsMap.put(Integer.valueOf(identifier), new HashMap<>() {{
                    put(finalSimpleKey, Integer.valueOf(list.get(1)));
                    put(finalAdvancedkey, Integer.valueOf(list.get(2)));
                    put(finalExpertKey, Integer.valueOf(list.get(3)));
                    put(finalLegendaryKey, Integer.valueOf(list.get(4)));
                }});
            }
        }

        return new SpellslotsMatrix(spellslotsMap,
                simpleKey, advancedkey, expertKey, legendaryKey,
                simpleDivider, advancedDivider, expertDivider, legendaryDivider);
    }
}
