package net.tfobz.relationship;

import static org.junit.Assert.*;
import org.junit.Test;
import net.tfobz.relationship.Person.Gender;

public class PersonTestHannes {

	@Test
	public void creation() {
		Person p = new Person("Sepp", Gender.MALE);
		
		assertEquals(p.name, "Sepp");
		assertEquals(p.gender, Gender.MALE);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nameGenderEmpty() {
		Person p = new Person("a", Gender.MALE);
		
		p.setName(null);
		p.setGender(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nameEmpty() {
		Person p = new Person("a", Gender.MALE);
		
		p.setName(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void genderEmpty() {
		Person p = new Person("a", Gender.MALE);
		
		p.setGender(null);
	}

	@Test (expected = IllegalArgumentException.class)
	public void creationNameGenderEmpty(){
		Person p = new Person(null, null);
	}

	@Test (expected = IllegalArgumentException.class)
	public void creationNameEmpty(){
		Person p = new Person("", Gender.MALE);
	}

	@Test (expected = IllegalArgumentException.class)
	public void creationGenderEmpty(){
		Person p = new Person("a", null);
	}

	@Test
	public void equals(){
		Person p1 = new Person("a", Gender.FEMALE);
		Person p2 = new Person("a", Gender.FEMALE);

		assertEquals(p1.equals(p2), true);
	}

	@Test (expected = IllegalArgumentException.class)
	public void equalsNull(){
		Person p1 = new Person("a", Gender.FEMALE);

		p1.equals(null);
	}

	@Test
	public void parent(){
		// TODO

		Person p = new Person("Sepp", Gender.MALE);
		Person father = new Person("aaa", Gender.MALE);
		Person mother = new Person("bbb", Gender.FEMALE);

		p.setFather(father);
		p.setMother(mother);

		assertEquals(p.father, father);
		assertEquals(p.mother, mother);

		p.setFather(null);
		p.setMother(null);

		assertEquals(p.father, null);
		assertEquals(p.mother, null);
	}

	@Test (expected = IllegalArgumentException.class)
	public void parentIncorrectGender(){
		Person p = new Person("Sepp", Gender.MALE);

		p.setFather(new Person("aaa", Gender.FEMALE));
		p.setMother(new Person("bbb", Gender.MALE));
	}

	@Test
	public void children(){
		Person p = new Person("Sepp", Gender.MALE);
		Person f = new Person("aaa", Gender.MALE);
		Person m = new Person("bbb", Gender.FEMALE);

		f.children.add(p);

		p.setFather(f);
		p.setMother(m);

		assertEquals(p.father.children.size(), p.mother.children.size());
	}

	@Test
	public void daughtersSons(){
		Person p = new Person("Sepp", Gender.MALE);

		p.children.add(new Person("a", Gender.MALE));
		p.children.add(new Person("b", Gender.FEMALE));

		assertEquals(p.getDaughters().size(), 1);
		assertEquals(p.getSons().size(), 1);
	}

	@Test
	public void sistersBrothers(){
		Person p = new Person("Sepp", Gender.MALE);

		p.setFather(new Person("aaa", Gender.MALE));
		p.setMother(new Person("bbb", Gender.FEMALE));

		p.father.children.add(new Person("a", Gender.MALE));
		p.father.children.add(new Person("b", Gender.FEMALE));

		assertEquals(p.getBrothers().size(), 1);
		assertEquals(p.getSisters().size(), 1);
	}

	@Test
	public void descendants(){
		Person p = new Person("Sepp", Gender.MALE);

		p.children.add(new Person("a", Gender.MALE));
		p.children.add(new Person("b", Gender.FEMALE));

		assertEquals(p.getDescendants().size(), 2);
	}

	@Test (expected = IllegalArgumentException.class)
	public void parentOfHimself(){
		Person p = new Person("Sepp", Gender.MALE);

		p.setFather(p);
		p.setMother(p);
	}

	@Test (expected = IllegalArgumentException.class)
	public void parentIsDescendant(){
		Person p = new Person("Sepp", Gender.MALE);
		Person p2 = new Person("aa", Gender.MALE);

		p.children.add(p2);
		p.setFather(p2);
	}
}
