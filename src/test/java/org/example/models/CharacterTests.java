package org.example.models;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.loaders.LoaderUtils;
import org.example.loaders.TalentLoader;
import org.example.mainframe.MainFrame;
import org.example.mainframe.UsedValues;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.mockito.Mockito.mock;


public class CharacterTests {

    Character character;

    @BeforeClass
    public static void setMatrix() throws IOException {
        File file = new File("src/test/resources/PnpCharacterCreator2.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet talentsSheet = workbook.getSheet(UsedValues.TALENTS_SHEETNAME);
        MainFrame.talentMatrix = TalentLoader.getTalentMatrixFromMap(LoaderUtils.getMap(talentsSheet));
    }

    @Before
    public void loadWorkbook() {

        character = new Character();
    }

    @Test
    public void testCharacterLvlChange() {
        int START_STATSVALUE = 0;

        Assert.assertEquals("Level should have an initial value of 0.", character.getLvl(), 0);
        Assert.assertEquals("Statpoints should have an initial value of 2.", character.getStatPoints(), Character.STATPOINT_START);
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
        Assert.assertEquals("Character statspoints should be calculated new after level change.", character.getStatPoints(), Character.STATPOINT_START + Character.STATPOINT_PER_LEVEL * NEW_LVL);

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
        Assert.assertEquals("Stat modify value is not matched", Character.ADDEDHP_MODIFIER, character.getModifyValueByEnum(Character.STATNAMES.HP));
        Assert.assertEquals("Stat modify value is not matched", Character.NOT_HP_STAT_MODIFIER, character.getModifyValueByEnum(Character.STATNAMES.STRENGTH));
        Assert.assertEquals("Stat modify value is not matched", Character.NOT_HP_STAT_MODIFIER, character.getModifyValueByEnum(Character.STATNAMES.INTELLIGENCE));
        Assert.assertEquals("Stat modify value is not matched", Character.NOT_HP_STAT_MODIFIER, character.getModifyValueByEnum(Character.STATNAMES.DEXTERITY));

    }

    @Test
    public void testModifyStatValue() {
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
            Assert.assertEquals(pointsSpentErr, START_STATPOINTS - Character.STATPOINT_SPENT, character.getStatPoints());
            Assert.assertEquals(statIncreasedErr, Integer.valueOf(startValue + modifyValue), getter.get());
            int CURRENT_STATPOINTS_1 = character.getStatPoints();
            int CURRENT_DEXTERITY = getter.get();
            modifier.accept(-modifyValue);
            Assert.assertEquals(pointsUnspentErr, CURRENT_STATPOINTS_1 + Character.STATPOINT_SPENT, character.getStatPoints());
            Assert.assertEquals(statDecreaseErr, Integer.valueOf(CURRENT_DEXTERITY - modifyValue), getter.get());
        };

        record ModifyStatValueSample(Consumer<Integer> modifier, Supplier<Integer> getter, Integer startValue,
                                     Integer modifyValue) {
        }

        List<ModifyStatValueSample> modifyStatValueSamples = new ArrayList<>();

        ModifyStatValueSample modifyHP = new ModifyStatValueSample(character::modifyAddedHPValue, character::getAddedHP, NEW_ADDEDHP, Character.ADDEDHP_MODIFIER);
        ModifyStatValueSample modifyStrength = new ModifyStatValueSample(character::modifyStrengthValue, character::getStrength, NEW_STRENGTH, Character.NOT_HP_STAT_MODIFIER);
        ModifyStatValueSample modifyIntelligence = new ModifyStatValueSample(character::modifyIntelligenceValue, character::getIntelligence, NEW_INTELLIGENCE, Character.NOT_HP_STAT_MODIFIER);
        ModifyStatValueSample modifyDexterity = new ModifyStatValueSample(character::modifyDexterityValue, character::getDexterity, NEW_DEXTERITY, Character.NOT_HP_STAT_MODIFIER);
        modifyStatValueSamples.add(modifyHP);
        modifyStatValueSamples.add(modifyStrength);
        modifyStatValueSamples.add(modifyIntelligence);
        modifyStatValueSamples.add(modifyDexterity);

        for (ModifyStatValueSample modifyStatValueSample : modifyStatValueSamples) {
            modifySpecificStatvalue.test(modifyStatValueSample.modifier(), modifyStatValueSample.getter(), modifyStatValueSample.startValue(), modifyStatValueSample.modifyValue());
        }
    }

    @Test
    public void testCharacterRace() {
        int START_RACE_BUFFSIZE = 0;
        Assert.assertNull("Character Race should be null after creation", character.getRace());
        Assert.assertEquals("Character ChosenRaceBuffs should be empty after creation", character.getChosenRaceBuffs().size(), START_RACE_BUFFSIZE);

        int NEW_RACE_BUFFSIZE = 2;
        for (int i = 0; i < NEW_RACE_BUFFSIZE; i++) {
            character.updateChosenRaceBuffs(i, "");
        }

        Assert.assertEquals("Character ChosenRaceBuffs should contains buffs after adding them", character.getChosenRaceBuffs().size(), NEW_RACE_BUFFSIZE);

        Race NEW_RACE = mock(Race.class);
        String NEW_RACE_NAME = "Goblin";
        Mockito.when(NEW_RACE.getName()).thenReturn(NEW_RACE_NAME);

        character.setRace(NEW_RACE);
        String CHARACTER_NEW_RACE_NAME = character.getRace().getName();

        Assert.assertEquals("Character should have a race after setting it", CHARACTER_NEW_RACE_NAME, NEW_RACE_NAME);

        Assert.assertEquals("Character ChosenRaceBuffs should be empty after changing the race", character.getChosenRaceBuffs().size(), START_RACE_BUFFSIZE);
    }

    @Test
    public void testUpdateChosenRaceBuffs() {
        //here only replacement will be tested, because setting is tested at testCharacterRace
        int CHOICENUMBER_ZERO = 0;
        int CHOICENUMBER_ONE = 1;
        String START_BUFF_ZERO = "Buff zero";
        String START_BUFF_ONE = "Buff one";
        character.updateChosenRaceBuffs(CHOICENUMBER_ZERO, START_BUFF_ZERO);
        character.updateChosenRaceBuffs(CHOICENUMBER_ONE, START_BUFF_ONE);

        String ERR_MESSAGE = "ChosenRaceBuff is wrong or in false order";

        Assert.assertEquals(ERR_MESSAGE, character.getChosenRaceBuffs().get(CHOICENUMBER_ZERO), START_BUFF_ZERO);
        Assert.assertEquals(ERR_MESSAGE, character.getChosenRaceBuffs().get(CHOICENUMBER_ONE), START_BUFF_ONE);

        String NEW_BUFF_ZERO = "NEW Buff zero";
        String NEW_BUFF_ONE = "NEW Buff one";

        character.updateChosenRaceBuffs(CHOICENUMBER_ONE, NEW_BUFF_ONE);

        Assert.assertEquals(ERR_MESSAGE, character.getChosenRaceBuffs().get(CHOICENUMBER_ZERO), START_BUFF_ZERO);
        Assert.assertEquals(ERR_MESSAGE, character.getChosenRaceBuffs().get(CHOICENUMBER_ONE), NEW_BUFF_ONE);

        character.updateChosenRaceBuffs(CHOICENUMBER_ZERO, NEW_BUFF_ZERO);

        Assert.assertEquals(ERR_MESSAGE, character.getChosenRaceBuffs().get(CHOICENUMBER_ZERO), NEW_BUFF_ZERO);
        Assert.assertEquals(ERR_MESSAGE, character.getChosenRaceBuffs().get(CHOICENUMBER_ONE), NEW_BUFF_ONE);
    }

    @Test
    public void testGetAllbuffs() {
        Race NEW_RACE = mock(Race.class);
        String RACE_BUFF_ONE = "1";
        String RACE_BUFF_TWO = "2";
        String CHOSEN_RACE_BUFF_ONE = "3";
        String CHOSEN_RACE_BUFF_TWO = "4";
        Mockito.when(NEW_RACE.getBuffs()).thenReturn(Arrays.asList(RACE_BUFF_ONE, RACE_BUFF_TWO));
        character.setRace(NEW_RACE);
        character.updateChosenRaceBuffs(0, CHOSEN_RACE_BUFF_ONE);
        character.updateChosenRaceBuffs(1, CHOSEN_RACE_BUFF_TWO);

        List<String> RESULT = Arrays.asList(RACE_BUFF_ONE, RACE_BUFF_TWO, CHOSEN_RACE_BUFF_ONE, CHOSEN_RACE_BUFF_TWO);

        Assert.assertEquals("get all buffs does not return correct values", character.getAllBuffs(), RESULT);
    }

    @Test
    public void testSetRpgClass() {
        //here will be not tested that spells and passiv are empty after initialization, already tested in testCharacterLvlChange
        RPGClass NEW_RPG_CLASS = mock(RPGClass.class);
        String NEW_RPG_CLASS_NAME = "Necromancer";
        Mockito.when(NEW_RPG_CLASS.getName()).thenReturn(NEW_RPG_CLASS_NAME);

        Assert.assertNull("Character should not have a RPGClass after creation.", character.getRpgClass());

        String BLANK = "";
        character.getChosenPassivs().add(new Passiv(BLANK, BLANK, BLANK));
        character.getChosenSpells().add(new Spell(BLANK, BLANK, BLANK, BLANK, BLANK));

        character.setRpgClass(NEW_RPG_CLASS);

        Assert.assertEquals("Character should have a RPGClass after setting it.", character.getRpgClass().getName(), NEW_RPG_CLASS_NAME);
        Assert.assertEquals("Character should not have chosen Passivs after RPGClass change.", character.getChosenPassivs().size(), 0);
        Assert.assertEquals("Character should not have chosen Spells after RPGClass change.", character.getChosenSpells().size(), 0);

    }
}
