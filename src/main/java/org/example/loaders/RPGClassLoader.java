package org.example.loaders;

import org.apache.poi.ss.usermodel.Sheet;
import org.example.mainframe.MainFrame;
import org.example.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class RPGClassLoader {

    //to simplify MainFrame and improve its readability
    public static List<RPGClass> getRPGClass() {
        Sheet rpgClassSheet = MainFrame.workbook.getSheet("Klassen");
        
        return getRPGClassFromMap(LoaderUtils.getMap(rpgClassSheet));
    }

    //main function
    public static List<RPGClass> getRPGClassFromMap(Map<Integer, List<String>> rpgClassData) {
        List<RPGClass> rpgClasses = new ArrayList<>();
        int activeRPGClasses = 0;

        RPGClassLevel rpgClassLevel = null;

        for (List<String> list : rpgClassData.values()) {
            String identifier = list.getFirst();
            switch (identifier) {
                case "Name" -> {
                    RPGClass newRPGClass = new RPGClass(list.get(1));
                    rpgClasses.add(newRPGClass);
                    activeRPGClasses = rpgClasses.size() - 1;
                }
                case "Stuffe" -> {
                    rpgClassLevel = new RPGClassLevel(Integer.parseInt(list.get(1)));
                    rpgClasses.get(activeRPGClasses).getClassLvls().add(rpgClassLevel);
                }
                case "Schnelligkeit" -> {
                }
                default -> {
                    assert rpgClassLevel != null;
                    if (list.size() > 5) {
                        List<RPGClassChoosable> newChoosables = new ArrayList<>();
                        newChoosables.add(createRPGClassChoosable(list, true));
                        newChoosables.add(createRPGClassChoosable(list, false));
                        rpgClassLevel.getChoosables().add(newChoosables);
                    } else {
                        if (Objects.equals(list.get(1), "Passiv")) {
                            Passiv newPassiv = createPassivFromList(list, true);
                            rpgClassLevel.getPassivList().add(newPassiv);
                        } else {
                            Spell newSpell = createSpellFromList(list, true);
                            rpgClassLevel.getSpellList().add(newSpell);
                        }
                    }

                }
            }

        }
        return rpgClasses;
    }

    private static RPGClassChoosable createRPGClassChoosable(List<String> list, boolean first) {
        int shift = first ? 0 : 6;
        if (Objects.equals(list.get(1 + shift), "Passiv")) {
            return createPassivFromList(list, first);
        } else {
            return createSpellFromList(list, first);
        }
    }

    private static Passiv createPassivFromList(List<String> list, boolean first) {
        int shift = first ? 0 : 6;
        return new Passiv(list.get(2 + shift), list.get(3 + shift), list.get(4 + shift));
    }

    private static Spell createSpellFromList(List<String> list, boolean first) {
        int shift = first ? 0 : 6;
        return new Spell(list.get(2 + shift), list.get(0 + shift), list.get(1 + shift), list.get(3 + shift), list.get(4 + shift));
    }
}
