package net.tfobz.relationship;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonListTest {

    @Test
    void testString01(){
        BufferedReader reader = new BufferedReader(new StringReader(""));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString02(){
        BufferedReader reader = new BufferedReader(new StringReader("\n"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString03() throws IOException {
        BufferedReader reader = new BufferedReader(new StringReader("blabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString04(){
        BufferedReader reader = new BufferedReader(new StringReader("blabla,blabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString05(){
        BufferedReader reader = new BufferedReader(new StringReader("blabla;blabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString06(){
        BufferedReader reader = new BufferedReader(new StringReader("blabla;blabla;blabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString07(){
        BufferedReader reader = new BufferedReader(new StringReader("blabla;blabla;blabla;blabla;blabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString08(){
        BufferedReader reader = new BufferedReader(new StringReader(";;;"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString09(){
        BufferedReader reader = new BufferedReader(new StringReader("null;null;null;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString10(){
        BufferedReader reader = new BufferedReader(new StringReader(";;null;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString11(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;;null;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString12(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;blabla;null;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString13(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\nblabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString14(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\nblabla,blabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString15(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\nblabla;blabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString16(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\nblabla;blabla;blabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString17(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\nblabla;blabla;blabla;blabla;blabla"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString18(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\n;;;"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString19(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\nnull;null;null;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString20(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\n;;null;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString21(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\nElsa;;null;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString22(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\nElsa;blabla;null;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString23(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;;"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString24(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;null\nSepp;MALE;null;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString25(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;Resi;null"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    @Test
    void testString26(){
        BufferedReader reader = new BufferedReader(new StringReader("Sepp;MALE;null;Hugo"));
        assertThrows(IllegalArgumentException.class, () -> new PersonList(reader));
    }

    // Test ob String richtig in die PersonList aufgenommen wird
    @Test
    void readPersonsCorrect() {
        String fileString = "Sepp;MALE;null;null\nRosi;FEMALE;null;null\n" +
                "Rudi;MALE;Rosi;Sepp\nElsa;FEMALE;Rosi;Sepp\nEdit;FEMALE;Rosi;Sepp\n" +
                "Hugo;MALE;Elsa;Rudi\nHerta;FEMALE;Elsa;Rudi";

        BufferedReader reader = new BufferedReader(new StringReader(fileString));
        assertDoesNotThrow(() -> new PersonList(reader));
    }

    //Test der testet ob die datei relationship.txt richtig funktioniert
    @Test
    void readPersonsFromFile() throws IOException {
        String currentPath = "D:\\Eclipse2021\\Projects\\Person\\src\\net\\tfobz\\relationship";
        assertDoesNotThrow(() -> new PersonList(currentPath + "\\relationship.txt"));

        PersonList l = new PersonList(currentPath + "\\relationship.txt");
        Person sepp = new Person("Sepp", Person.Gender.MALE);
        Person rosi = new Person("Rosi", Person.Gender.FEMALE);
        Person rudi = new Person("Rudi", Person.Gender.MALE);
        rudi.setFather(sepp);
        rudi.setMother(rosi);
        Person elsa = new Person("Elsa", Person.Gender.FEMALE);
        elsa.setFather(sepp);
        elsa.setMother(rosi);

        assertEquals(l, List.of(sepp, rosi, rudi, elsa));

        assertEquals(l.get(0).getChildren(), List.of(rudi, elsa));
        assertEquals(l.get(1).getChildren(), List.of(rudi, elsa));

        assertEquals(sepp, l.get(2).getFather());
        assertEquals(rosi, l.get(2).getMother());

        assertEquals(sepp, l.get(3).getFather());
        assertEquals(rosi, l.get(3).getMother());
    }
}