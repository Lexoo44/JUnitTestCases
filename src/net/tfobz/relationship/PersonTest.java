package net.tfobz.relationship;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PersonTest {

	//Peson Konstruktor
    Person p = new Person("Sepp", Person.Gender.MALE);
    
    // Test der Testet ob Konstruktor eigenschaften korekt gesetzt wurden
     
    @Test
    public void creation() {
        assertEquals(p.getGender(), Person.Gender.MALE);
        assertEquals(p.getName(), "Sepp");
    }

    // Test der die Methode GenderEmpty und nameEpty Testet
    @Test
    public void nameGenderEmpty() {
        genderEmpty();
        nameEmpty();
    }

    // Test der Gender auf null setet und IllegalArgumentException wirft
    @Test
    public void genderEmpty() {
        assertThrows(IllegalArgumentException.class, () -> p.setGender(null));
    }

    // Test der Name auf null und auf "" setet und IllegalArgumentException wirft
    @Test
    public void nameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> p.setName(""));
        assertThrows(IllegalArgumentException.class, () -> p.setName(null));
    }

    // Test der creationNameEmpty und creationGenderEmpty testet
    @Test
    public void creationNameGenderEmpty() {
        creationNameEmpty();
        creationGenderEmpty();
    }

    // Test der Auf fehlende eigenschaften im Konstruktor testet
    @Test
    public void creationNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Person(null, null));
        assertThrows(IllegalArgumentException.class, () -> new Person("", null));
    }

    //Test der Auf fehlende eigenschaften im Konstruktor testet
    @Test 
    public void creationGenderEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Person("Sepp", null));
    }

    // Test der testetob zwei personen gleich sind 
    @Test
    public void equals() {
        Person p2 = new Person("Sepp", Person.Gender.MALE);
        assertEquals(p, p2);
        p2.setName("Jan");
        assertNotEquals(p, p2);
        p2.setName("Sepp");
        assertEquals(p, p2);
        p2.setGender(Person.Gender.FEMALE);
        assertNotEquals(p, p2);
    }

    // test der eine Person mit null vergleicht 
    @Test
    public void equalsNull() {
        assertThrows(IllegalArgumentException.class, () -> p.equals(null));
    }

    // test der testet ob mutter und Vater richtig gesetzt werden und wenn mutter und Vater null sind,
    // Mutter und Vater gelöscht werden 
    @Test
    public void parent() {
        Person father = new Person("Sepp Senior", Person.Gender.MALE);
        p.setFather(father);
        assertEquals(p.getFather(), father);

        Person mother = new Person("Marisa", Person.Gender.FEMALE);
        p.setMother(mother);
        assertEquals(p.getMother(), mother);

        p.setFather(null);
        assertNull(p.getFather());

        p.setMother(null);
        assertNull(p.getMother());
    }

    // Test der testet ob Mutter und Vater das Richtige geschlecht haben
    @Test
    public void parentIncorrectGender() {
        Person father = new Person("Sepp Senior", Person.Gender.FEMALE);
        assertThrows(IllegalArgumentException.class, () -> p.setFather(father));

        father.setGender(Person.Gender.MALE);
        p.setFather(father);
        assertEquals(p.getFather(), father);

        Person mother = new Person("Marisa", Person.Gender.MALE);
        assertThrows(IllegalArgumentException.class, () -> p.setMother(mother));

        mother.setGender(Person.Gender.FEMALE);
        p.setMother(mother);
        assertEquals(p.getMother(), mother);
    }

    // Test der testet ob Mutter und Vater richtg gesetzt werden, 
    // ob das Kind nicht öfters bei seinen Eltern gesetzt wird und 
    // ob das Kind wenn Mutter oder Vater gelöscht werden auch gelöscht werden und bei
    // mutter oder Vater wieder hinzugefügt werden
    @Test
    public void children() {
        Person father = new Person("Sepp Senior", Person.Gender.MALE);
        p.setFather(father);
        assertTrue(father.getChildren().contains(p));
        p.setFather(father);
        int count = Collections.frequency(father.getChildren(), p);
        assertEquals(count, 1);

        Person father2 = new Person("Sepp Senior 2", Person.Gender.MALE);
        p.setFather(father2);
        assertTrue(father2.getChildren().contains(p));
        assertFalse(father.getChildren().contains(p));

        p.setFather(father);
        assertTrue(father.getChildren().contains(p));
        p.setFather(null);
        assertFalse(father.getChildren().contains(p));

        Person mother = new Person("Marisa", Person.Gender.FEMALE);
        p.setMother(mother);
        assertTrue(mother.getChildren().contains(p));
        p.setMother(mother);
        count = Collections.frequency(mother.getChildren(), p);
        assertEquals(count, 1);

        Person mother2 = new Person("Marisa 2", Person.Gender.FEMALE);

        p.setMother(mother2);
        assertTrue(mother2.getChildren().contains(p));
        assertFalse(mother.getChildren().contains(p));

        p.setMother(mother);
        assertTrue(mother.getChildren().contains(p));
        p.setMother(null);
        assertFalse(mother.getChildren().contains(p));
    }

    // Test der die Methoden getSons und get Daughters testet
    @Test
    public void daughtersSons() {
        Person father = new Person("Sepp Senior", Person.Gender.MALE);
        p.setFather(father);

        assertTrue(father.getSons().contains(p));
        assertEquals(father.getDaughters().size(), 0);

        Person mother = new Person("Marisa", Person.Gender.FEMALE);

        assertFalse(mother.getSons().contains(p));
        assertEquals(mother.getSons().size(), 0);
        assertEquals(mother.getDaughters().size(), 0);

        Person p2 = new Person("Anna", Person.Gender.FEMALE);
        p2.setMother(mother);

        assertTrue(mother.getDaughters().contains(p2));
    }

    //Testet ob Brüder und Töchter richtig geliefert werden, 
    // Halbbroder oder Halbschwerstern sollen nicht zurückgeliefet werden
    @Test
    public void sistersBrothers() {
        p.setMother(null);
        p.setFather(null);

        assertEquals(p.getSisters().size(), 0);
        assertEquals(p.getBrothers().size(), 0);

        Person father = new Person("Sepp Senior", Person.Gender.MALE);
        Person mother = new Person("Marisa", Person.Gender.FEMALE);

        p.setFather(father);
        p.setMother(mother);

        Person brother = new Person("Ben", Person.Gender.MALE);
        brother.setFather(father);
        brother.setMother(mother);

        assertTrue(p.getBrothers().contains(brother));

        Person sister = new Person("Frieda", Person.Gender.FEMALE);
        sister.setMother(mother);

        assertFalse(p.getSisters().contains(sister));
        sister.setFather(father);

        assertTrue(p.getSisters().contains(sister));
    }

    // Test zur kontrolle der Nachkommen 
    @Test
    public void descendants() {
        Person father = new Person("Sepp Senior", Person.Gender.MALE);
        Person child1 = new Person("Frieda", Person.Gender.FEMALE);

        Person child2 = new Person("Anna", Person.Gender.FEMALE);
        Person child3 = new Person("Max", Person.Gender.MALE);

        p.setFather(father);
        child1.setFather(father);

        assertEquals(father.getDescendants(), List.of(p, child1));

        child2.setMother(child1);
        child3.setFather(p);
        child3.setMother(child1);

        assertEquals(father.getDescendants(), List.of(p, child1, child3, child2));
    }

    //Test ob man sein eigener Vater ist wirft exception
    @Test
    public void parentOfHimself() {
        assertThrows(IllegalArgumentException.class, () -> p.setFather(p));
    }

    // Test ob man sein eigener Onkel ist
    @Test
    public void parentIsDescendant() {
        Person father = new Person("Sepp Senior", Person.Gender.MALE);

        p.setFather(father);

        assertThrows(IllegalArgumentException.class, () -> father.setFather(p));
    }

}