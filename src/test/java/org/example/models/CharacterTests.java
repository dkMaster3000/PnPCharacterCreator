package org.example.models;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.loaders.LoaderUtils;
import org.example.loaders.TalentLoader;
import org.example.mainframe.MainFrame;
import org.example.mainframe.UsedValues;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.function.Consumer;
import java.util.function.Supplier;


public class CharacterTests {

    File file = new File("src/test/resources/PnpCharacterCreator2.xlsx");
    FileInputStream fileInputStream = null;
    XSSFWorkbook workbook = null;

    @Before
    public void loadWorkbook() throws IOException {
        fileInputStream = new FileInputStream(file);
        workbook = new XSSFWorkbook(fileInputStream);
        Sheet talentsSheet = workbook.getSheet(UsedValues.TALENTS_SHEETNAME);
        MainFrame.talentMatrix = TalentLoader.getTalentMatrixFromMap(LoaderUtils.getMap(talentsSheet));
    }

    @Test
    public void testCharacterLvlChange() {
        int START_STATSVALUE = 0;
        Character character = new Character();
        Assert.assertEquals("Level should have an initial value of 0.", character.getLvl(), 0);
        Assert.assertEquals("Statpoints should have an initial value of 2.", character.getStatPoints(), 2);
        Assert.assertEquals("Stats should have an initial value of 0.", character.getAddedHP(), START_STATSVALUE);
        Assert.assertEquals("Stats should have an initial value of 0.", character.getStrength(), START_STATSVALUE);
        Assert.assertEquals("Stats should have an initial value of 0.", character.getIntelligence(), START_STATSVALUE);
        Assert.assertEquals("Stats should have an initial value of 0.", character.getDexterity(), START_STATSVALUE);

        int NEW_ADDEDHP = 1;
        int NEW_STRENGTH = 2;
        int NEW_INTELLIGENCE = 3;
        int NEW_DEXTERITY = 4;
        character.setAddedHP(NEW_ADDEDHP);
        character.setStrength(NEW_STRENGTH);
        character.setIntelligence(NEW_INTELLIGENCE);
        character.setDexterity(NEW_DEXTERITY);
        Assert.assertEquals("Stats should have new statvalue.", character.getAddedHP(), NEW_ADDEDHP);
        Assert.assertEquals("Stats should have new statvalue.", character.getStrength(), NEW_STRENGTH);
        Assert.assertEquals("Stats should have new statvalue.", character.getIntelligence(), NEW_INTELLIGENCE);
        Assert.assertEquals("Stats should have new statvalue.", character.getDexterity(), NEW_DEXTERITY);

        Assert.assertEquals("Chosen Passivs should have initial size of 0.", character.getChosenPassivs().size(), START_STATSVALUE);
        Assert.assertEquals("Chosen Spells should have initial size of 0.", character.getChosenSpells().size(), START_STATSVALUE);
        String UNIMPORTANT_BLANK = "";
        int NEW_PASSIVES_SIZE = 2;
        int NEW_SPELLS_SIZE = 1;
        for (int i = 0; i < NEW_PASSIVES_SIZE; i++) {
            character.getChosenPassivs().add(new Passiv(UNIMPORTANT_BLANK, UNIMPORTANT_BLANK, UNIMPORTANT_BLANK));
        }
        for (int i = 0; i < NEW_SPELLS_SIZE; i++) {
            character.getChosenSpells().add(new Spell(UNIMPORTANT_BLANK, UNIMPORTANT_BLANK, UNIMPORTANT_BLANK, UNIMPORTANT_BLANK, UNIMPORTANT_BLANK));
        }
        Assert.assertEquals("Chosen Passivs should have new size.", character.getChosenPassivs().size(), NEW_PASSIVES_SIZE);
        Assert.assertEquals("Chosen Spells should have new size.", character.getChosenSpells().size(), NEW_SPELLS_SIZE);

        int NEW_LVL = 1;
        character.setLvl(NEW_LVL);
        Assert.assertEquals("Character Level should be the new lvl.", character.getLvl(), NEW_LVL);
        Assert.assertEquals("Character statspoints should be calculated new after level change.", character.getStatPoints(), 5);

        Assert.assertEquals("Stats should be reset and 0 after lvl change.", character.getAddedHP(), START_STATSVALUE);
        Assert.assertEquals("Stats should be reset and 0 after lvl change.", character.getStrength(), START_STATSVALUE);
        Assert.assertEquals("Stats should be reset and 0 after lvl change.", character.getIntelligence(), START_STATSVALUE);
        Assert.assertEquals("Stats should be reset and 0 after lvl change.", character.getDexterity(), START_STATSVALUE);

        Assert.assertEquals("Chosen Passivs should be reset and 0 after lvl change.", character.getChosenPassivs().size(), START_STATSVALUE);
        Assert.assertEquals("Chosen Spells should be reset and 0 after lvl change.", character.getChosenSpells().size(), START_STATSVALUE);

        Assert.assertEquals("Character initial talents size should be 0.", character.getTalents().size(), START_STATSVALUE);
        int NEW_Talents_SIZE = 3;
        for (int i = 0; i < NEW_Talents_SIZE; i++) {
            character.getTalents().add(new Talent(UNIMPORTANT_BLANK, UNIMPORTANT_BLANK, true));
        }
        Assert.assertEquals("Character talents size should be new size.", character.getTalents().size(), NEW_Talents_SIZE);

        int NEW_LVL_2 = 2;
        character.setLvl(NEW_LVL_2);
        Assert.assertEquals("Character talents size should stay the same if the max talent amounts stays the same after lvl change", character.getTalents().size(), NEW_Talents_SIZE);

        int NEW_LVL_3 = 3;
        character.setLvl(NEW_LVL_3);
        Assert.assertEquals("Character talents size should be 0 if the max talent amount changes after lvl change", character.getTalents().size(), START_STATSVALUE);

    }

    @Test
    public void testGetModifyValueByEnum() {
        Character character = new Character();
        Assert.assertEquals("Stat modify value is not matched", UsedValues.ADDEDHP_MODIFIER, character.getModifyValueByEnum(Character.STATNAMES.HP));
        Assert.assertEquals("Stat modify value is not matched", UsedValues.NOT_HP_STAT_MODIFIER, character.getModifyValueByEnum(Character.STATNAMES.STRENGTH));
        Assert.assertEquals("Stat modify value is not matched", UsedValues.NOT_HP_STAT_MODIFIER, character.getModifyValueByEnum(Character.STATNAMES.INTELLIGENCE));
        Assert.assertEquals("Stat modify value is not matched", UsedValues.NOT_HP_STAT_MODIFIER, character.getModifyValueByEnum(Character.STATNAMES.DEXTERITY));

    }

    @Test
    public void testModifyStatValue() {
        Character character = new Character();
        int NEW_LVL = 1;
        character.setLvl(NEW_LVL);
        int START_STATPOINTS = character.getStatPoints();
        int NEW_ADDEDHP = 10;
        int NEW_STRENGTH = 2;
        int NEW_INTELLIGENCE = 3;
        int NEW_DEXTERITY = 4;
        character.setAddedHP(NEW_ADDEDHP);
        character.setStrength(NEW_STRENGTH);
        character.setIntelligence(NEW_INTELLIGENCE);
        character.setDexterity(NEW_DEXTERITY);

        String pointsSpentErr = "Character spendable statpoints should be reduced after spent";
        String statIncreasedErr = "Character stat should be increased after spending statpoints";
        String pointsUnspentErr = "Character spendable statpoints should be increased after unspent";
        String statDecreaseErr = "Character stat should be reduced after unspent";

        @FunctionalInterface
        interface ModifySpecificStatvalueTest {
            void test(Consumer<Integer> modifier, Supplier<Integer> getter, Integer startValue, Integer modifyValue);
        }

        ModifySpecificStatvalueTest modifySpecificStatvalue = (modifier, getter, startValue, modifyValue) -> {
            modifier.accept(modifyValue);
            Assert.assertEquals(pointsSpentErr, START_STATPOINTS - UsedValues.STATPOINT, character.getStatPoints());
            Assert.assertEquals(statIncreasedErr, Integer.valueOf(startValue + modifyValue), getter.get());
            int CURRENT_STATPOINTS_1 = character.getStatPoints();
            int CURRENT_DEXTERITY = getter.get();
            modifier.accept(-modifyValue);
            Assert.assertEquals(pointsUnspentErr, CURRENT_STATPOINTS_1 + UsedValues.STATPOINT, character.getStatPoints());
            Assert.assertEquals(statDecreaseErr, Integer.valueOf(CURRENT_DEXTERITY - modifyValue), getter.get());
        };

        record ModifyStatValueSample(Consumer<Integer> modifier, Supplier<Integer> getter, Integer startValue,
                                     Integer modifyValue) {
        }

        List<ModifyStatValueSample> modifyStatValueSamples = new ArrayList<>();

        ModifyStatValueSample modifyHP = new ModifyStatValueSample(character::modifyAddedHPValue, character::getAddedHP, NEW_ADDEDHP, UsedValues.ADDEDHP_MODIFIER);
        ModifyStatValueSample modifyStrength = new ModifyStatValueSample(character::modifyStrengthValue, character::getStrength, NEW_STRENGTH, UsedValues.NOT_HP_STAT_MODIFIER);
        ModifyStatValueSample modifyIntelligence = new ModifyStatValueSample(character::modifyIntelligenceValue, character::getIntelligence, NEW_INTELLIGENCE, UsedValues.NOT_HP_STAT_MODIFIER);
        ModifyStatValueSample modifyDexterity = new ModifyStatValueSample(character::modifyDexterityValue, character::getDexterity, NEW_DEXTERITY, UsedValues.NOT_HP_STAT_MODIFIER);
        modifyStatValueSamples.add(modifyHP);
        modifyStatValueSamples.add(modifyStrength);
        modifyStatValueSamples.add(modifyIntelligence);
        modifyStatValueSamples.add(modifyDexterity);

        for (ModifyStatValueSample modifyStatValueSample : modifyStatValueSamples) {
            modifySpecificStatvalue.test(modifyStatValueSample.modifier(), modifyStatValueSample.getter(), modifyStatValueSample.startValue(), modifyStatValueSample.modifyValue());
        }

    }
}
