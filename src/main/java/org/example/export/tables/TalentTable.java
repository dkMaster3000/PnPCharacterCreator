package org.example.export.tables;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.example.export.CharacterSheet;
import org.example.mainframe.MainFrame;
import org.example.models.Talent;

import java.util.List;

public class TalentTable extends PaintedTable {

    public TalentTable(CharacterSheet sheet, IndexedColors indexedColors) {
        super(sheet, indexedColors);
    }

    @Override
    public void fillTable() {

        List<Talent> talents = MainFrame.character.getTalents();

        String[] talentsHeader = new String[]{"Name", "Effekt"};
        if (!talents.isEmpty()) {
            createRow("Talente");
            createRow(talentsHeader, true);
            for (Talent talent : talents) {
                createRow(new String[]{
                        talent.name(),
                        talent.description(),
                }, false);
            }
        }

    }
}
