package org.example.loaders;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.mainframe.UsedValues;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class LoadersTest {
    File file = new File("src/test/resources/PnpCharacterCreator2.xlsx");
    FileInputStream fileInputStream = null;
    XSSFWorkbook workbook = null;

    @Before
    public void loadWorkbook() throws IOException {
        fileInputStream = new FileInputStream(file);
        workbook = new XSSFWorkbook(fileInputStream);
    }

    @Test
    public void testLoaderUtilRace() {
        Sheet raceSheet = workbook.getSheet(UsedValues.RACE_SHEETNAME);

        String raceOutput = "{0=[Name, Mensch], 1=[Größe, 1,60-1,90 M], 2=[Gewicht, 60-110 Kg], 3=[HP, 100], 4=[Bewegungsreichweite, 5], 5=[Effekte, 2+ Leveln Fertigkeiten, 20+ Punkte zum verteilen, 1+ Talent], 6=[Debuffs], 7=[Auswahl,  2+Charism, 20 Basisrüstung], 8=[Auswahl, 1+ Bewegung, 3+ Initiative], 9=[Name, Goblin], 10=[Größe, 1,00-1,10 M], 11=[Gewicht, 20-30 Kg], 12=[HP, 40], 13=[Bewegungsreichweite, 6], 14=[Effekte, 3+ Charisma, 3+ Diebeskunst, 3+ Ini, Kann zwei mal Täglich einen Angriff garantiert ausweichen und kann sich nach einem Angriff erneut bewegen und bekommt keine Gelegenheitsangriffe ab, Dunkelsicht, Dodgecap ist auf 80% erhöht, 10%+ Dodge], 15=[Debuffs, Kann der Gier nach Gold nicht widerstehen], 16=[Auswahl, 2+ Bewegung, Heimlichkeit 2+], 17=[Auswahl, 2+ Bewegung, Diebeskunst 2+]}";

        Assert.assertEquals(LoaderUtils.getMap(raceSheet).toString(), raceOutput);
    }

    @Test
    public void testLoaderUtilClass() {
        Sheet classSheet = workbook.getSheet(UsedValues.CLASS_SHEETNAME);
        String classOutput = "{0=[Name, Nekromant], 1=[Stuffe, 1], 2=[Schnelligkeit, Art, Name, Effekt, Range], 3=[Langsam, Basis, Seelengeschoss, Feuert ein geschoss und verursacht 1d10+Wissen schaden (ignoriert Rüstung), 7], 4=[Schnell, Basis, Leiche auflösen, Löst eine Leiche auf und du heilst dich um 5% deiner Max HP, 5], 5=[Langsam, Einfach, Zombie erschaffen, Erschafft einen Zombie aus einer Leiche, dieser hat HP in Höhe des Levels *15, kann für1d10 beissen, bei Treffer ist das Ziel infiziert und verwandelt sich bei Tod auch in einen Zombie, 5], 6=[Langsam, Einfach, Eisstrahl, Fügt bei Treffer 1d20+Wissen Schaden zu und reduziert die Bewegung des Ziels um 1, 7], 7=[Langsam, Fortgeschritten, Untote Beleben, Belebt eine Leiche als Untoten wieder, dieser hat die gleichen Stats wie zu seinen Lebzeiten kurz vor dem Tod, 5], 8=[-, Passiv, Untotenbeschwörer, Beschworene Untote gehorchen dir (max.3), über das Limit beschworene Tote wüten wahlos umher, /], 9=[-, Passiv, Geteilte Kraft, Diener unter deiner Kontrolle erhalten dein Wissen*3 als Rüstung und machen dein Wissen*3 zusätzlichen Schaden, /], 10=[Stuffe, 2], 11=[Schnelligkeit, Art, Name, Effekt, Range], 12=[Langsam, Einfach, Skelettkrieger, Erschafft aus einer Leiche einen Skelettkrieger oder einen Skelettmagier, Stats basieren auf der Leiche, 5], 13=[Schnell, Fortgeschritten, Frostschild, Schützt sich selbst oder einen Verbündeten um 2d20+Wissen*2 , Angreifer werden 1 Platz in der Ini-Leiste nach hinter versetzt, 5], 14=[Schnell, Fortgeschritten, Leichenexplosion, Lässt eine Leiche explodieren, getroffene Feinde erhalten 2d20+Wissen*2 Schaden und sind infiziert, 5/2(Explusionsradius)], 15=[Langsam, Experte, Erhabene Totenbeschwörung, Belebt eine Leiche wieder, diese hat noch ihr Bewusstsein, muss dir aber gehörchen solange die Bedingungen erfüllt sind, 5], 16=[-, Passiv, Mehrfachnutzen, Kann aus einer Leiche 2 Beschwörungen vollziehen, /, oder, -, Passiv, Kommunikation mit den Toten, Kann einen mit einem Toten sprechen (Max 5 fragen pro Toten), /], 17=[-, Passiv, Seelendiener, Kann die Seele eines verstorbenen als Geist kontrollieren, dieser ist Unsichtbar(kann durch Zauber entdeckt werden) und hat keine Angriffe, Sicht kann geteilt werden, /, oder, -, Passiv, Seelenfresser, Kann von frischen Leichen die Seele absorbieren um temporär einen Boost zu erhalten (Abhängig von der Leiche) oder die Seele in einen deiner Diener transferieren, der Diener bekommt einen Boost abhängig von der Seele, /], 18=[Stuffe, 3], 19=[Schnelligkeit, Art, Name, Effekt, Range], 20=[Schnell, Einfach, Leichenspeicherung/Seelenspeicherung, Absorbiert eine Leiche oder Seele und speichert diese in deinem Körper, kann für einen Aktionspunkt wieder ausgestoßen werden (max.5), 1], 21=[Langsam, Fortgeschritten, Seelenbombe, Feuert eine Sammlung von Seelen auf den Gegner, bei Treffer erhält dieser pro Seele 5%Max HP Schaden, 7], 22=[Langsam, Experte, Frostkugel, Feuert eine Frostkugel auf ein Ziel, bei Treffer erhält das 3d20+Wissen*3 Schaden und wird ein Platz nach hinter verschoben in der Ini-Leiste und verliert 2 Bewegung, 7], 23=[Langsam, Legendär, Monstrosität , Erschafft aus 3 Leichen oder 3 Dienern oder einer Kombination davon eine zusammengesetzte Leiche, hat die Eigenschafften aller 3 Leichen (kann nicht mit einer Monstrosität selbst genutzt werden), 5, oder, Langsam, Legendär, Frostnova, Getroffene Feinde erhalten 1d100+Wissen*5 Schaden und verlieren 1 Aktionspunkt und 2 Bewegung, 7/2*2], 24=[-, Passiv, Meister der Toten, Das Limit für die Kontrollierung von Untoten erhöht sich auf 5, /, oder, -, Passiv, Frosthände, Die Angriffe deiner Diener reduzieren die Bewegung des Ziels um 2, /], 25=[Stuffe, 4], 26=[Schnelligkeit, Art, Name, Effekt, Range], 27=[Langsam, Experte, Griff in das Totenreich, Zieht eine dir bekannte verstorbene Seele aus dem Totenreich, du kannst diese Seele einem Diener geben, dieser erhält Eigenschaften abhängig von der Seele, die Seele wird dabei verbraucht, 1], 28=[Langsam, Legendär, Banshee oder Lich Beschwören, Beschwört aus 5 Seelen eine Banshee, diese kann bei Erfolg in ein feindliches Ziel eindringen und dieses kontrollieren, dieses Ziel steht dann unter deiner Kontrolle bis das Ziel stirbt oder durch andere Wege befreit wird, die Banshee geht beim eindringen verloren oder Beschwört aus 3 Leichen und 2 Seelen einen Lich, dieser hat Seelenmagie (Stats basieren auf dem Material), 5], 29=[-, Passiv, Magischer Pakt, Schaden den du bekommst wird auf deinen nächsten Diener verteilt , /, oder, -, Passiv, Totenrüstung, Pro in dir gespeicherte Leiche und oder Seele erhälst du 5 Rüstung, /], 30=[-, Passiv, Recycling, Kann einen verstorbenen Diener erneut als Leiche nutzen (max. 1 mal ), /, oder, -, Passiv, Großes Potenzial, Das Limit von Leichenspeicher wird auf 10 Leichen und 10 Seelen erhöht, /], 31=[Stuffe, 5], 32=[Schnelligkeit, Art, Name, Effekt, Range], 33=[Langsam, Experte, Seelenmal, Makiert ein Ziel und verursacht dabei 3d20+Wissen*3 Schaden, sollte das Ziel sterben erhälst du die Seele des Ziels, beim Tod des Ziels werden Gegner in der Nähe mit Seelenmal makiert, 7(Makierung)/2 (beim Tod)], 34=[Langsam, Legendär, Knochendrache, Eschafft aus 3 Leichen,6 Skeletten, 3 Dienern oder einer Komibination davon einen Knochendrachen, (Stats basieren auf dem Material) , dieser beherrscht Frostmagie, 5], 35=[Schnell, Passiv, Übernahme, Kann einmal Täglich in den Körper eines Dieners eintauchen, während du im Körper deines Dieners bist, könnt ihr beide separat agieren, im Körper deines Dieners erhälst du kein Schaden, 5, oder, -, Passiv, Vampirfürst, Wenn du insgesamt 10 Leichen und 10 Seelen in dir aufnimmst, kannst du zum Vampirfürst aufsteigen, als Vampirfürst reicht es Blut als Nahrung zu dir zu nehmen, dabei Regeneriest du je nach Mengen zwischen 30-100 %MAX HP, du benötigst kein Schlaf mehr, du erhälst +5 Bewegung, 20%Dodge Chance, deine Bewegung erfolgt sofort (teleportierst dich), bei der Einnahme von Blut erhälst du auch einen Bonus auf einen ausgewählten Stat (Stk/int oder ges) je nach Menge des Blutes (temporär), du kannst anderen dein Blut einverleiben um sie ebenfalls zum Vampirfürsten zu machen , /], 36=[Name, Beschwörer], 37=[Stuffe, 1], 38=[Schnelligkeit, Art, Name, Effekt, Range], 39=[Langsam, Basis, Säurepfeil, Feuert ein Säuregeschoss und verursacht 1d10+Wissen schaden und verringert die Rüstung des Ziels um 1d6, 7], 40=[Schnell, Basis, Beschwörung auslösen, Löst eine Beschwörung auf und du heilst dich um 5% deiner Max HP, 5], 41=[Langsam, Einfach, Niederen Dämon beschwören, Erzeugt einen Siegelkreis und beschwört einen niederen Dämon, welcher dir gehorchen muss solange die Bedingung erfüllt ist, 5], 42=[Langsam, Einfach, Phantomdiener, Erzeugt ein Duplikat des Ziels, dieses hat eine Ausweichchance von 50% und hat 50% der Stärke des Duplikats (kann keine Fähigkeiten des Ziels wirken), stirbt bei einem treffer, 5], 43=[Langsam, Fortgeschritten, Erdelementar, Erschafft einen Erdelementar, dieser hat Wissen*2+2d20 HP, physischer Schaden wird um 30% reduziert, er greift mit 2d20+Wissen*2 an und hat dabei eine Stunchance von 20%, hat 2 Aktionspunkte pro Zug, hat einen Konter: verhärtung: 50% DR , 5], 44=[-, Passiv, Haustiermeister, Beschworene Diener gehorchen dir (max.3), über das Limit beschworene Diener wüten wahlos umher, /], 45=[-, Passiv, Geteilte Kraft, Diener unter deiner Kontrolle erhalten dein Wissen*3 als Rüstung und machen dein Wissen*3 zusätzlichen Schaden, /], 46=[Stuffe, 2], 47=[Schnelligkeit, Art, Name, Effekt, Range], 48=[Langsam, Einfach, Beschwörungen heilen, Heilt bis zu 2 beschwörere Diener um 1d20+Wissen, 7], 49=[Langsam, Fortgeschritten, Feuerelementar , Erschafft einen Feuerelementar, dieser hat Wissen*2+2d20 HP, Feueres, er greift mit 2d20+Wissen*2 an (Fernkampf,7Range) und reduziert dabei 1d10 Rüstung, hat 2 Aktionspunkte pro Zug,, 5], 50=[Langsam, Fortgeschritten, Luftelementar, Erschafft einen Windelementar, dieser hat Wissen*2+2d20 HP, Windres, er greift mit 2d20+Wissen*2 an und bekommt keine gelegenheitsangriffe ab, hat 3 Aktionspunkte pro Zug, 5], 51=[Langsam, Experte, Dämon Beschwören, Erzeugt einen Siegelkreis und beschwört einen Dämon, welcher dir gehorchen muss solange die Bedingung erfüllt ist, 5], 52=[-, Passiv, Diener Reserve, Kann Beschworene Diener in einen Gegenstand einsperren und jederzeit rausholen , /, oder, -, Passiv, Aktiontransfer, Du kannst deine Aktionspunkte einem Diener transferieren, /], 53=[-, Passiv, Magischer Pakt, Die hälfte des Schadens die du bekommst wird auf deine Diener verteilt , /, oder, -, Passiv, Diener Falle, Deine Beschwörungen explodieren beim Tod und verursachen dabei 2d20+Wissen*2 Schaden, 2], 54=[Stuffe, 3], 55=[Schnelligkeit, Art, Name, Effekt, Range], 56=[Konter, Einfach, Tauschen, Tauscht den Platz mit einem Diener, 7], 57=[Langsam, Fortgeschritten, Wasserelementar, Erschafft einen Wasserelementar, dieser hat Wissen*2+2d20 HP, Wasserres, er greift mit 2d20+Wissen*2 (Fernkampf 7 Range) und reduziert die Bewegung des Ziels um 1, hat 2 Aktionspunkte pro Zug, 5], 58=[Schnell, Experte, Magischer Schild, Das Ziel erhält einen Magischen Schild der jeglichen Schaden um 50% verringert solange er aktiv ist, Höhe des Schilds: 3d20+Wissen*3, 5], 59=[Langsam, Legendär, Gottesfaust, Erschafft eine Gigantische Faust welche aus dem Himmel angreift, bei Treffer erhält das Ziel 1d100+Wissen*5 Schaden, 60% stun, 7, oder, Langsam, Legendär, Leerenfeld, Erzeugt ein Feld in dem keine Zeit vergeht und keine Magie möglich ist, hält für 2 Runden , 7/2*2], 60=[-, Passiv, Haustierliebhaber, Das Limit für die Kontrollierung von Dienern erhöht sich auf 5, /, oder, -, Passiv, Wie ich, Erworbene Resistenzen wirken sich auf die ganze Gruppe aus (Bsp Feuerelementar ist Feuerresistent -> Spieler und andere Beschwörungen sind es auch), /], 61=[Stuffe, 4], 62=[Schnelligkeit, Art, Name, Effekt, Range], 63=[Langsam, Experte, Bannen, Verbannt eine Beschwörung, 7], 64=[Langsam, Legendär, Koloss erschaffen, Erschafft einen Gigantischen Koloss welcher Blitze schießen und ein Gewitter erschaffen kann (Stufe=Wissen/2), 5], 65=[-, Passiv, Dienertausch, Kann die Positionen von 2 Dienern untereinander tauschen (Konteraktion), /, oder, -, Passiv, Beschworener Rucksack, Inventarplatz +10, /], 66=[-, Passiv, Bis zum Schluss, Beschwörungen haben ein 1HP Safe, /, oder, -, Passiv, Für den Meister, Solltest du auf 0 HP fallen bleiben deine Beschwörungen trotzdem und opfern sich für dich, pro geopferte Beschwörung erhälst du 20%Max HP zurück, /], 67=[Stuffe, 5], 68=[Schnelligkeit, Art, Name, Effekt, Range], 69=[Langsam, Experte, Ultimativer Diener , Ein ausgewählter Diener erhält für 2 Runden ein Aktionspunkt mehr, 30 Rüstung und verursacht zusätzlich 2d20+Wissen*2 Schaden , 7], 70=[Langsam, Legendär, Höheren Dämon beschwören, Erzeugt einen Siegelkreis und beschwört einen höheren Dämon, welcher dir gehorchen muss solange die Bedingung erfüllt ist, 5], 71=[-, Passiv, Ultimativer Beschwörungsmeister, Beschwörungen sind immun gegen den Zauber \"Bannen\" und gegen negative Statuseffekte , /, oder, -, Passiv, Zweites Leben, Wenn einer deiner kontrollierten Diener auf 0 HP fällt, wird er für seine 1 Runde komplett wiederhergestellt und löst sich nach der Runde auf, /]}";

        Assert.assertEquals(LoaderUtils.getMap(classSheet).toString(), classOutput);
    }

    @Test
    public void testLoaderUtilTalent() {
        Sheet talentsSheet = workbook.getSheet(UsedValues.TALENTS_SHEETNAME);

        String talentMatrixOutput = "{0=[Freischaltung auf den Stufen, 1, 3, 5, 7, 9], 1=[Name, Talente, Unique], 2=[Vitalität, 10 HP mehr pro Level, Unique], 3=[Erhöhte Kraft, 1 Stärke mehr pro Level, Unique], 4=[Erweiteter Verstand, 1 Int mehr pro Level, Unique], 5=[Erhöhte Reaktion, 1 Ges mehr pro Level, Unique], 6=[Gut gerüstet, 5 Basisrüstung pro Level, Unique], 7=[Schauspieler, 3+ Charisma], 8=[Wachsam, 5+ Ini und kann nicht überrascht werden], 9=[Athlet, 15% Dodge], 10=[Fernkampfexperte, Ist immer im Vorteill mit Fernkampfwaffen], 11=[Beidhändig, Kann eine Zweihandwaffe einhändig führen], 12=[Forscher, Im Vorteil beim Fallen entdecken und halber Schaden durch Fallen], 13=[Widerstandsfähig, Erhält alle HP beim Rasten zurück und 5 HP mehr pro Level], 14=[Elementarer Adept, Ignoriert eine gewählte Resistenz und kann bei dieser Schadensart keine 1 mehr würfeln], 15=[Waffenmeister, Kann nach einem Kill oder crit mit einer Nahkampfwaffe einen weiteren Angriff ausführen (einmal pro Runde)], 16=[Meister der Rüstungen, Jeweilige Schadensreduzierungen werden um 10% erhöht], 17=[Glückspilz, Kann täglich einmal neuwürfeln], 18=[Magiertöter, Wenn ein Ziel einen Zauber innerhalb deiner Reichweite wirkt, kann ein gelegenheitsangriff durchgeführt werden und sollte der Zauber auf dich gewirkt werden, ist dieser im Nachteil], 19=[Magienovize, Kann einen Zauber des nächsthöheren Levels nutzen (aus dem Magie Bereich)], 20=[Meister der Kampfkunst, Kann einen Zauber des nächsthöheren Levels nutzen (aus dem Kriegskunst Bereich)], 21=[Meister des Hinterhalts, Kann einen Zauber des nächsthöheren Levels nutzen (aus dem Hinterhalt Bereich)], 22=[Leichtfüßig, 2+ Bewegung und kann sich nach dem Angreifen bewegen], 23=[Unterhalter, Kann mit Musikinstrumenten umgehen und bekommt dabei einen Bonus von 3+], 24=[Wilder Angreifer, Ist bei Waffenangriffen im Vorteil], 25=[Wächter, Wird ein Verbündeter in deiner Reichweite angegriffen, kannst du einen Gelegenheitsangriff ausführen], 26=[Scharfschütze, Fernkampfangriffe machen erhöhten Schaden (1d10 alle 2 Level/beginnend mit Level 1)], 27=[Schildmeister, Blocken mit einem Schild ist verbessert um 1d10 alle 2 Level/beginnend mit Level 1], 28=[Zauberschütze, Zauber brauchen eine Augenzahl weniger für einen crit (stackt sich mit anderen Effekten)], 29=[Tarvernenschläger, Waffenlose Angriffe oder improvisierte Waffen fügen Stärke *3 Schaden zu ], 30=[Alkoholiker , Hat Betrunken einen Bonus von 3+ auf seine Würfe], 31=[Bluttrinker, Heilt 10% des verursachten Schadens (muss mit einer Nahkampfwaffe erfolgen)], 32=[Erhöhte Kapazität (leicht), 4+ Einfache Spellslots], 33=[Erhöhte Kapazität (mittel), 3+ Fortgeschrittene Spellslots], 34=[Erhöhte Kapazität (stark), 2+ Epische Spellslots], 35=[Erhöhte Kapazität (enorm), 1+ Legendäre Spellslots]}";

        Assert.assertEquals(LoaderUtils.getMap(talentsSheet).toString(), talentMatrixOutput);
    }

    @Test
    public void testLoaderUtilSpellslots() {
        Sheet spellslotsSheet = workbook.getSheet(UsedValues.SPELLSLOTS_SHEETNAME);

        String spellslottMatrixOutput = "{0=[Lvl, Einfach, Fort, Experte, Legendär], 1=[Divider, 6, 8, 12, 20], 2=[1, 4, 3, 0, 0], 3=[2, 5, 3, 1, 0], 4=[3, 6, 4, 2, 1], 5=[4, 6, 4, 3, 2], 6=[5, 6, 4, 4, 2], 7=[6, 6, 5, 4, 2], 8=[7, 6, 5, 4, 3], 9=[8, 6, 6, 5, 4], 10=[9, 6, 6, 6, 4], 11=[10, 6, 6, 6, 5]}";

        Assert.assertEquals(LoaderUtils.getMap(spellslotsSheet).toString(), spellslottMatrixOutput);
    }

    @Test
    public void testRaceLoader() {
        Sheet raceSheet = workbook.getSheet(UsedValues.RACE_SHEETNAME);

        String raceOutput = "[\n" +
                "\n" +
                " Race: Mensch\n" +
                " height=1,60-1,90 M\n" +
                " weight=60-110 Kg\n" +
                " HP=100\n" +
                " movement=5\n" +
                " buffs=[2+ Leveln Fertigkeiten, 20+ Punkte zum verteilen, 1+ Talent]\n" +
                " debuffs=[]\n" +
                " choices=[[ 2+Charism, 20 Basisrüstung], [1+ Bewegung, 3+ Initiative]], \n" +
                "\n" +
                " Race: Goblin\n" +
                " height=1,00-1,10 M\n" +
                " weight=20-30 Kg\n" +
                " HP=40\n" +
                " movement=6\n" +
                " buffs=[3+ Charisma, 3+ Diebeskunst, 3+ Ini, Kann zwei mal Täglich einen Angriff garantiert ausweichen und kann sich nach einem Angriff erneut bewegen und bekommt keine Gelegenheitsangriffe ab, Dunkelsicht, Dodgecap ist auf 80% erhöht, 10%+ Dodge]\n" +
                " debuffs=[Kann der Gier nach Gold nicht widerstehen]\n" +
                " choices=[[2+ Bewegung, Heimlichkeit 2+], [2+ Bewegung, Diebeskunst 2+]]]";

        Assert.assertEquals(RaceLoader.getRacesFromMap(LoaderUtils.getMap(raceSheet)).toString(), raceOutput);
    }

    @Test
    public void testClassLoader() {
        Sheet classSheet = workbook.getSheet(UsedValues.CLASS_SHEETNAME);

        String classOutput = "[\nName=Nekromant\n" +
                ", classLvls=[\n" +
                "lvl=1, \n" +
                "lspellList=[Spell{tempo='Langsam', difficulty='Basis', name='Seelengeschoss', effect='Feuert ein geschoss und verursacht 1d10+Wissen schaden (ignoriert Rüstung)', range='7'}, Spell{tempo='Schnell', difficulty='Basis', name='Leiche auflösen', effect='Löst eine Leiche auf und du heilst dich um 5% deiner Max HP', range='5'}, Spell{tempo='Langsam', difficulty='Einfach', name='Zombie erschaffen', effect='Erschafft einen Zombie aus einer Leiche, dieser hat HP in Höhe des Levels *15, kann für1d10 beissen, bei Treffer ist das Ziel infiziert und verwandelt sich bei Tod auch in einen Zombie', range='5'}, Spell{tempo='Langsam', difficulty='Einfach', name='Eisstrahl', effect='Fügt bei Treffer 1d20+Wissen Schaden zu und reduziert die Bewegung des Ziels um 1', range='7'}, Spell{tempo='Langsam', difficulty='Fortgeschritten', name='Untote Beleben', effect='Belebt eine Leiche als Untoten wieder, dieser hat die gleichen Stats wie zu seinen Lebzeiten kurz vor dem Tod', range='5'}], \n" +
                "lpassivList=[Passiv{name='Untotenbeschwörer', effect='Beschworene Untote gehorchen dir (max.3), über das Limit beschworene Tote wüten wahlos umher', range='/'}, Passiv{name='Geteilte Kraft', effect='Diener unter deiner Kontrolle erhalten dein Wissen*3 als Rüstung und machen dein Wissen*3 zusätzlichen Schaden', range='/'}], \n" +
                "lchoosables=[], \n" +
                "lvl=2, \n" +
                "lspellList=[Spell{tempo='Langsam', difficulty='Einfach', name='Skelettkrieger', effect='Erschafft aus einer Leiche einen Skelettkrieger oder einen Skelettmagier, Stats basieren auf der Leiche', range='5'}, Spell{tempo='Schnell', difficulty='Fortgeschritten', name='Frostschild', effect='Schützt sich selbst oder einen Verbündeten um 2d20+Wissen*2 , Angreifer werden 1 Platz in der Ini-Leiste nach hinter versetzt', range='5'}, Spell{tempo='Schnell', difficulty='Fortgeschritten', name='Leichenexplosion', effect='Lässt eine Leiche explodieren, getroffene Feinde erhalten 2d20+Wissen*2 Schaden und sind infiziert', range='5/2(Explusionsradius)'}, Spell{tempo='Langsam', difficulty='Experte', name='Erhabene Totenbeschwörung', effect='Belebt eine Leiche wieder, diese hat noch ihr Bewusstsein, muss dir aber gehörchen solange die Bedingungen erfüllt sind', range='5'}], \n" +
                "lpassivList=[], \n" +
                "lchoosables=[[Passiv{name='Mehrfachnutzen', effect='Kann aus einer Leiche 2 Beschwörungen vollziehen', range='/'}, Passiv{name='Kommunikation mit den Toten', effect='Kann einen mit einem Toten sprechen (Max 5 fragen pro Toten)', range='/'}], [Passiv{name='Seelendiener', effect='Kann die Seele eines verstorbenen als Geist kontrollieren, dieser ist Unsichtbar(kann durch Zauber entdeckt werden) und hat keine Angriffe, Sicht kann geteilt werden', range='/'}, Passiv{name='Seelenfresser', effect='Kann von frischen Leichen die Seele absorbieren um temporär einen Boost zu erhalten (Abhängig von der Leiche) oder die Seele in einen deiner Diener transferieren, der Diener bekommt einen Boost abhängig von der Seele', range='/'}]], \n" +
                "lvl=3, \n" +
                "lspellList=[Spell{tempo='Schnell', difficulty='Einfach', name='Leichenspeicherung/Seelenspeicherung', effect='Absorbiert eine Leiche oder Seele und speichert diese in deinem Körper, kann für einen Aktionspunkt wieder ausgestoßen werden (max.5)', range='1'}, Spell{tempo='Langsam', difficulty='Fortgeschritten', name='Seelenbombe', effect='Feuert eine Sammlung von Seelen auf den Gegner, bei Treffer erhält dieser pro Seele 5%Max HP Schaden', range='7'}, Spell{tempo='Langsam', difficulty='Experte', name='Frostkugel', effect='Feuert eine Frostkugel auf ein Ziel, bei Treffer erhält das 3d20+Wissen*3 Schaden und wird ein Platz nach hinter verschoben in der Ini-Leiste und verliert 2 Bewegung', range='7'}], \n" +
                "lpassivList=[], \n" +
                "lchoosables=[[Spell{tempo='Langsam', difficulty='Legendär', name='Monstrosität ', effect='Erschafft aus 3 Leichen oder 3 Dienern oder einer Kombination davon eine zusammengesetzte Leiche, hat die Eigenschafften aller 3 Leichen (kann nicht mit einer Monstrosität selbst genutzt werden)', range='5'}, Spell{tempo='Langsam', difficulty='Legendär', name='Frostnova', effect='Getroffene Feinde erhalten 1d100+Wissen*5 Schaden und verlieren 1 Aktionspunkt und 2 Bewegung', range='7/2*2'}], [Passiv{name='Meister der Toten', effect='Das Limit für die Kontrollierung von Untoten erhöht sich auf 5', range='/'}, Passiv{name='Frosthände', effect='Die Angriffe deiner Diener reduzieren die Bewegung des Ziels um 2', range='/'}]], \n" +
                "lvl=4, \n" +
                "lspellList=[Spell{tempo='Langsam', difficulty='Experte', name='Griff in das Totenreich', effect='Zieht eine dir bekannte verstorbene Seele aus dem Totenreich, du kannst diese Seele einem Diener geben, dieser erhält Eigenschaften abhängig von der Seele, die Seele wird dabei verbraucht', range='1'}, Spell{tempo='Langsam', difficulty='Legendär', name='Banshee oder Lich Beschwören', effect='Beschwört aus 5 Seelen eine Banshee, diese kann bei Erfolg in ein feindliches Ziel eindringen und dieses kontrollieren, dieses Ziel steht dann unter deiner Kontrolle bis das Ziel stirbt oder durch andere Wege befreit wird, die Banshee geht beim eindringen verloren oder Beschwört aus 3 Leichen und 2 Seelen einen Lich, dieser hat Seelenmagie (Stats basieren auf dem Material)', range='5'}], \n" +
                "lpassivList=[], \n" +
                "lchoosables=[[Passiv{name='Magischer Pakt', effect='Schaden den du bekommst wird auf deinen nächsten Diener verteilt ', range='/'}, Passiv{name='Totenrüstung', effect='Pro in dir gespeicherte Leiche und oder Seele erhälst du 5 Rüstung', range='/'}], [Passiv{name='Recycling', effect='Kann einen verstorbenen Diener erneut als Leiche nutzen (max. 1 mal )', range='/'}, Passiv{name='Großes Potenzial', effect='Das Limit von Leichenspeicher wird auf 10 Leichen und 10 Seelen erhöht', range='/'}]], \n" +
                "lvl=5, \n" +
                "lspellList=[Spell{tempo='Langsam', difficulty='Experte', name='Seelenmal', effect='Makiert ein Ziel und verursacht dabei 3d20+Wissen*3 Schaden, sollte das Ziel sterben erhälst du die Seele des Ziels, beim Tod des Ziels werden Gegner in der Nähe mit Seelenmal makiert', range='7(Makierung)/2 (beim Tod)'}, Spell{tempo='Langsam', difficulty='Legendär', name='Knochendrache', effect='Eschafft aus 3 Leichen,6 Skeletten, 3 Dienern oder einer Komibination davon einen Knochendrachen, (Stats basieren auf dem Material) , dieser beherrscht Frostmagie', range='5'}], \n" +
                "lpassivList=[], \n" +
                "lchoosables=[[Passiv{name='Übernahme', effect='Kann einmal Täglich in den Körper eines Dieners eintauchen, während du im Körper deines Dieners bist, könnt ihr beide separat agieren, im Körper deines Dieners erhälst du kein Schaden', range='5'}, Passiv{name='Vampirfürst', effect='Wenn du insgesamt 10 Leichen und 10 Seelen in dir aufnimmst, kannst du zum Vampirfürst aufsteigen, als Vampirfürst reicht es Blut als Nahrung zu dir zu nehmen, dabei Regeneriest du je nach Mengen zwischen 30-100 %MAX HP, du benötigst kein Schlaf mehr, du erhälst +5 Bewegung, 20%Dodge Chance, deine Bewegung erfolgt sofort (teleportierst dich), bei der Einnahme von Blut erhälst du auch einen Bonus auf einen ausgewählten Stat (Stk/int oder ges) je nach Menge des Blutes (temporär), du kannst anderen dein Blut einverleiben um sie ebenfalls zum Vampirfürsten zu machen ', range='/'}]]]}, \n" +
                "Name=Beschwörer\n" +
                ", classLvls=[\n" +
                "lvl=1, \n" +
                "lspellList=[Spell{tempo='Langsam', difficulty='Basis', name='Säurepfeil', effect='Feuert ein Säuregeschoss und verursacht 1d10+Wissen schaden und verringert die Rüstung des Ziels um 1d6', range='7'}, Spell{tempo='Schnell', difficulty='Basis', name='Beschwörung auslösen', effect='Löst eine Beschwörung auf und du heilst dich um 5% deiner Max HP', range='5'}, Spell{tempo='Langsam', difficulty='Einfach', name='Niederen Dämon beschwören', effect='Erzeugt einen Siegelkreis und beschwört einen niederen Dämon, welcher dir gehorchen muss solange die Bedingung erfüllt ist', range='5'}, Spell{tempo='Langsam', difficulty='Einfach', name='Phantomdiener', effect='Erzeugt ein Duplikat des Ziels, dieses hat eine Ausweichchance von 50% und hat 50% der Stärke des Duplikats (kann keine Fähigkeiten des Ziels wirken), stirbt bei einem treffer', range='5'}, Spell{tempo='Langsam', difficulty='Fortgeschritten', name='Erdelementar', effect='Erschafft einen Erdelementar, dieser hat Wissen*2+2d20 HP, physischer Schaden wird um 30% reduziert, er greift mit 2d20+Wissen*2 an und hat dabei eine Stunchance von 20%, hat 2 Aktionspunkte pro Zug, hat einen Konter: verhärtung: 50% DR ', range='5'}], \n" +
                "lpassivList=[Passiv{name='Haustiermeister', effect='Beschworene Diener gehorchen dir (max.3), über das Limit beschworene Diener wüten wahlos umher', range='/'}, Passiv{name='Geteilte Kraft', effect='Diener unter deiner Kontrolle erhalten dein Wissen*3 als Rüstung und machen dein Wissen*3 zusätzlichen Schaden', range='/'}], \n" +
                "lchoosables=[], \n" +
                "lvl=2, \n" +
                "lspellList=[Spell{tempo='Langsam', difficulty='Einfach', name='Beschwörungen heilen', effect='Heilt bis zu 2 beschwörere Diener um 1d20+Wissen', range='7'}, Spell{tempo='Langsam', difficulty='Fortgeschritten', name='Feuerelementar ', effect='Erschafft einen Feuerelementar, dieser hat Wissen*2+2d20 HP, Feueres, er greift mit 2d20+Wissen*2 an (Fernkampf,7Range) und reduziert dabei 1d10 Rüstung, hat 2 Aktionspunkte pro Zug,', range='5'}, Spell{tempo='Langsam', difficulty='Fortgeschritten', name='Luftelementar', effect='Erschafft einen Windelementar, dieser hat Wissen*2+2d20 HP, Windres, er greift mit 2d20+Wissen*2 an und bekommt keine gelegenheitsangriffe ab, hat 3 Aktionspunkte pro Zug', range='5'}, Spell{tempo='Langsam', difficulty='Experte', name='Dämon Beschwören', effect='Erzeugt einen Siegelkreis und beschwört einen Dämon, welcher dir gehorchen muss solange die Bedingung erfüllt ist', range='5'}], \n" +
                "lpassivList=[], \n" +
                "lchoosables=[[Passiv{name='Diener Reserve', effect='Kann Beschworene Diener in einen Gegenstand einsperren und jederzeit rausholen ', range='/'}, Passiv{name='Aktiontransfer', effect='Du kannst deine Aktionspunkte einem Diener transferieren', range='/'}], [Passiv{name='Magischer Pakt', effect='Die hälfte des Schadens die du bekommst wird auf deine Diener verteilt ', range='/'}, Passiv{name='Diener Falle', effect='Deine Beschwörungen explodieren beim Tod und verursachen dabei 2d20+Wissen*2 Schaden', range='2'}]], \n" +
                "lvl=3, \n" +
                "lspellList=[Spell{tempo='Konter', difficulty='Einfach', name='Tauschen', effect='Tauscht den Platz mit einem Diener', range='7'}, Spell{tempo='Langsam', difficulty='Fortgeschritten', name='Wasserelementar', effect='Erschafft einen Wasserelementar, dieser hat Wissen*2+2d20 HP, Wasserres, er greift mit 2d20+Wissen*2 (Fernkampf 7 Range) und reduziert die Bewegung des Ziels um 1, hat 2 Aktionspunkte pro Zug', range='5'}, Spell{tempo='Schnell', difficulty='Experte', name='Magischer Schild', effect='Das Ziel erhält einen Magischen Schild der jeglichen Schaden um 50% verringert solange er aktiv ist, Höhe des Schilds: 3d20+Wissen*3', range='5'}], \n" +
                "lpassivList=[], \n" +
                "lchoosables=[[Spell{tempo='Langsam', difficulty='Legendär', name='Gottesfaust', effect='Erschafft eine Gigantische Faust welche aus dem Himmel angreift, bei Treffer erhält das Ziel 1d100+Wissen*5 Schaden, 60% stun', range='7'}, Spell{tempo='Langsam', difficulty='Legendär', name='Leerenfeld', effect='Erzeugt ein Feld in dem keine Zeit vergeht und keine Magie möglich ist, hält für 2 Runden ', range='7/2*2'}], [Passiv{name='Haustierliebhaber', effect='Das Limit für die Kontrollierung von Dienern erhöht sich auf 5', range='/'}, Passiv{name='Wie ich', effect='Erworbene Resistenzen wirken sich auf die ganze Gruppe aus (Bsp Feuerelementar ist Feuerresistent -> Spieler und andere Beschwörungen sind es auch)', range='/'}]], \n" +
                "lvl=4, \n" +
                "lspellList=[Spell{tempo='Langsam', difficulty='Experte', name='Bannen', effect='Verbannt eine Beschwörung', range='7'}, Spell{tempo='Langsam', difficulty='Legendär', name='Koloss erschaffen', effect='Erschafft einen Gigantischen Koloss welcher Blitze schießen und ein Gewitter erschaffen kann (Stufe=Wissen/2)', range='5'}], \n" +
                "lpassivList=[], \n" +
                "lchoosables=[[Passiv{name='Dienertausch', effect='Kann die Positionen von 2 Dienern untereinander tauschen (Konteraktion)', range='/'}, Passiv{name='Beschworener Rucksack', effect='Inventarplatz +10', range='/'}], [Passiv{name='Bis zum Schluss', effect='Beschwörungen haben ein 1HP Safe', range='/'}, Passiv{name='Für den Meister', effect='Solltest du auf 0 HP fallen bleiben deine Beschwörungen trotzdem und opfern sich für dich, pro geopferte Beschwörung erhälst du 20%Max HP zurück', range='/'}]], \n" +
                "lvl=5, \n" +
                "lspellList=[Spell{tempo='Langsam', difficulty='Experte', name='Ultimativer Diener ', effect='Ein ausgewählter Diener erhält für 2 Runden ein Aktionspunkt mehr, 30 Rüstung und verursacht zusätzlich 2d20+Wissen*2 Schaden ', range='7'}, Spell{tempo='Langsam', difficulty='Legendär', name='Höheren Dämon beschwören', effect='Erzeugt einen Siegelkreis und beschwört einen höheren Dämon, welcher dir gehorchen muss solange die Bedingung erfüllt ist', range='5'}], \n" +
                "lpassivList=[], \n" +
                "lchoosables=[[Passiv{name='Ultimativer Beschwörungsmeister', effect='Beschwörungen sind immun gegen den Zauber \"Bannen\" und gegen negative Statuseffekte ', range='/'}, Passiv{name='Zweites Leben', effect='Wenn einer deiner kontrollierten Diener auf 0 HP fällt, wird er für seine 1 Runde komplett wiederhergestellt und löst sich nach der Runde auf', range='/'}]]]}]";

        Assert.assertEquals(RPGClassLoader.getRPGClassFromMap(LoaderUtils.getMap(classSheet)).toString(), classOutput);
    }

    @Test
    public void testTalentLoader() {
        Sheet talentsSheet = workbook.getSheet(UsedValues.TALENTS_SHEETNAME);

        String talentMatrixOutput = "TalentMatrix{talents=[\n" +
                "Talent{\n" +
                "name='Vitalität', \n" +
                "description='10 HP mehr pro Level', \n" +
                "unique=true}, \n" +
                "Talent{\n" +
                "name='Erhöhte Kraft', \n" +
                "description='1 Stärke mehr pro Level', \n" +
                "unique=true}, \n" +
                "Talent{\n" +
                "name='Erweiteter Verstand', \n" +
                "description='1 Int mehr pro Level', \n" +
                "unique=true}, \n" +
                "Talent{\n" +
                "name='Erhöhte Reaktion', \n" +
                "description='1 Ges mehr pro Level', \n" +
                "unique=true}, \n" +
                "Talent{\n" +
                "name='Gut gerüstet', \n" +
                "description='5 Basisrüstung pro Level', \n" +
                "unique=true}, \n" +
                "Talent{\n" +
                "name='Schauspieler', \n" +
                "description='3+ Charisma', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Wachsam', \n" +
                "description='5+ Ini und kann nicht überrascht werden', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Athlet', \n" +
                "description='15% Dodge', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Fernkampfexperte', \n" +
                "description='Ist immer im Vorteill mit Fernkampfwaffen', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Beidhändig', \n" +
                "description='Kann eine Zweihandwaffe einhändig führen', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Forscher', \n" +
                "description='Im Vorteil beim Fallen entdecken und halber Schaden durch Fallen', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Widerstandsfähig', \n" +
                "description='Erhält alle HP beim Rasten zurück und 5 HP mehr pro Level', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Elementarer Adept', \n" +
                "description='Ignoriert eine gewählte Resistenz und kann bei dieser Schadensart keine 1 mehr würfeln', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Waffenmeister', \n" +
                "description='Kann nach einem Kill oder crit mit einer Nahkampfwaffe einen weiteren Angriff ausführen (einmal pro Runde)', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Meister der Rüstungen', \n" +
                "description='Jeweilige Schadensreduzierungen werden um 10% erhöht', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Glückspilz', \n" +
                "description='Kann täglich einmal neuwürfeln', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Magiertöter', \n" +
                "description='Wenn ein Ziel einen Zauber innerhalb deiner Reichweite wirkt, kann ein gelegenheitsangriff durchgeführt werden und sollte der Zauber auf dich gewirkt werden, ist dieser im Nachteil', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Magienovize', \n" +
                "description='Kann einen Zauber des nächsthöheren Levels nutzen (aus dem Magie Bereich)', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Meister der Kampfkunst', \n" +
                "description='Kann einen Zauber des nächsthöheren Levels nutzen (aus dem Kriegskunst Bereich)', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Meister des Hinterhalts', \n" +
                "description='Kann einen Zauber des nächsthöheren Levels nutzen (aus dem Hinterhalt Bereich)', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Leichtfüßig', \n" +
                "description='2+ Bewegung und kann sich nach dem Angreifen bewegen', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Unterhalter', \n" +
                "description='Kann mit Musikinstrumenten umgehen und bekommt dabei einen Bonus von 3+', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Wilder Angreifer', \n" +
                "description='Ist bei Waffenangriffen im Vorteil', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Wächter', \n" +
                "description='Wird ein Verbündeter in deiner Reichweite angegriffen, kannst du einen Gelegenheitsangriff ausführen', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Scharfschütze', \n" +
                "description='Fernkampfangriffe machen erhöhten Schaden (1d10 alle 2 Level/beginnend mit Level 1)', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Schildmeister', \n" +
                "description='Blocken mit einem Schild ist verbessert um 1d10 alle 2 Level/beginnend mit Level 1', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Zauberschütze', \n" +
                "description='Zauber brauchen eine Augenzahl weniger für einen crit (stackt sich mit anderen Effekten)', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Tarvernenschläger', \n" +
                "description='Waffenlose Angriffe oder improvisierte Waffen fügen Stärke *3 Schaden zu ', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Alkoholiker ', \n" +
                "description='Hat Betrunken einen Bonus von 3+ auf seine Würfe', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Bluttrinker', \n" +
                "description='Heilt 10% des verursachten Schadens (muss mit einer Nahkampfwaffe erfolgen)', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Erhöhte Kapazität (leicht)', \n" +
                "description='4+ Einfache Spellslots', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Erhöhte Kapazität (mittel)', \n" +
                "description='3+ Fortgeschrittene Spellslots', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Erhöhte Kapazität (stark)', \n" +
                "description='2+ Epische Spellslots', \n" +
                "unique=false}, \n" +
                "Talent{\n" +
                "name='Erhöhte Kapazität (enorm)', \n" +
                "description='1+ Legendäre Spellslots', \n" +
                "unique=false}], unlockLvls=[1, 3, 5, 7, 9]}";

        Assert.assertEquals(TalentLoader.getTalentMatrixFromMap(LoaderUtils.getMap(talentsSheet)).toString(), talentMatrixOutput);
    }

    @Test
    public void testSpellslotsLoader() {
        Sheet spellslotsSheet = workbook.getSheet(UsedValues.SPELLSLOTS_SHEETNAME);

        String spellslottMatrixOutput = "SpellslotsMatrix{spellslotsMatrix={1={Einfach=4, Experte=0, Fort=3, Legendär=0}, 2={Einfach=5, Experte=1, Fort=3, Legendär=0}, 3={Einfach=6, Experte=2, Fort=4, Legendär=1}, 4={Einfach=6, Experte=3, Fort=4, Legendär=2}, 5={Einfach=6, Experte=4, Fort=4, Legendär=2}, 6={Einfach=6, Experte=4, Fort=5, Legendär=2}, 7={Einfach=6, Experte=4, Fort=5, Legendär=3}, 8={Einfach=6, Experte=5, Fort=6, Legendär=4}, 9={Einfach=6, Experte=6, Fort=6, Legendär=4}, 10={Einfach=6, Experte=6, Fort=6, Legendär=5}}, simpleKey='Einfach', advancedkey='Fort', expertKey='Experte', legendaryKey='Legendär', simpleDivider=6, advancedDivider=8, expertDivider=12, legendarDivider=20}";

        Assert.assertEquals(SpellslotsLoader.getSpellslotsMatrixFromMap(LoaderUtils.getMap(spellslotsSheet)).toString(), spellslottMatrixOutput);
    }


}
