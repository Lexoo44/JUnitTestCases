package net.tfobz.relationship;

import java.util.ArrayList;

public class Person
{
	public enum Gender { FEMALE, MALE };
	
	protected Gender gender = null;
	protected String name = null;
	protected Person mother = null;
	protected Person father = null;
	protected ArrayList<Person> children = new ArrayList();

	public Person(String name, Gender gender) {
		setName(name);
		setGender(gender);
	}

	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		if(gender == null) {
			throw new IllegalArgumentException("Gender kann nicht nul sein");
		}
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name == null || name.length() == 0) {
			throw new IllegalArgumentException("Name kann nicht null oder leer sein");
		}
		this.name = name;
	}
	
	public Person getMother() {
		return mother;
	}

	public void setMother(Person mother) {
		if(mother == this) {
			throw new IllegalArgumentException("Man kann nicht seine eigene Mutter sein");
		}

		if(getDescendants().contains(mother)) {
			throw new IllegalArgumentException("Deine Mutter kann nicht dein descendants sein ");
		}

		if(this.mother != null) {
			this.mother.getChildren().remove(this);
		}

		if(mother == null) {
			this.mother = null;
			return;
		}

		if(!mother.getGender().equals(Gender.FEMALE)) {
			throw new IllegalArgumentException("Mutter muss Famale als Gender haben");
		}

		if(!mother.getChildren().contains(this)) {
			mother.getChildren().add(this);
		}
		this.mother = mother;
	}
	
	public Person getFather() {
		return father;
	}

	public void setFather(Person father) {
		if(father == this){
			throw new IllegalArgumentException("Man kann nicht sein eigener Vater sein");
		}

		if(getDescendants().contains(father)) {
			throw new IllegalArgumentException("Vater kann nicht descendants sein");
		}

		if(this.father != null) {
			this.father.getChildren().remove(this);
		}

		if(father == null) {
			this.father = null;
			return;
		}

		if(!father.getGender().equals(Gender.MALE)) {
			throw new IllegalArgumentException("Vater muss Male als Gender habne");
		}

		if(!father.getChildren().contains(this)) {
			father.getChildren().add(this);
		}

		this.father = father;
	}
	
	public ArrayList<Person> getChildren() {
		return children;
	}

	public ArrayList<Person> getDaughters() {
		ArrayList<Person> out = new ArrayList<>();
		for(Person p : children) {
			if(p.getGender().equals(Gender.FEMALE)) {
				out.add(p);
			}
		}
		return out;
	}

	public ArrayList<Person> getSons() {
		ArrayList<Person> out = new ArrayList<>();
		for(Person p : children) {
			if(p.getGender().equals(Gender.MALE)) {
				out.add(p);
			}
		}
		return out;
	}

	private ArrayList<Person> mergeDuplicates(ArrayList<Person> list1, ArrayList<Person> list2) {
		ArrayList<Person> out = new ArrayList<>();
		for(Person p : list1) {
			if(list2.contains(p)) {
				out.add(p);
			}
		}
		return out;
	}

	public ArrayList<Person> getBrothers() {
		if(father == null || mother == null) {
			return new ArrayList<>();
		}
		ArrayList<Person> out = new ArrayList<>();
		ArrayList<Person> duplicates = mergeDuplicates(father.getChildren(), mother.getChildren());
		for(Person p : duplicates) {
			if(p.getGender().equals(Gender.MALE)){
				out.add(p);
			}
		}

		return out;
	}

	
	public ArrayList<Person> getSisters() {
		if(father == null || mother == null) {
			return new ArrayList<>();
		}
		ArrayList<Person> out = new ArrayList<>();
		ArrayList<Person> duplicates = mergeDuplicates(father.getChildren(), mother.getChildren());
		for(Person p : duplicates) {
			if(p.getGender().equals(Gender.FEMALE)){
				out.add(p);
			}
		}

		return out;
	}

	public ArrayList<Person> getDescendants() {
		return getDescendants(this);
	}

	private ArrayList<Person> getDescendants(Person p) {
		ArrayList<Person> out = new ArrayList<>(p.getChildren());

		for(Person pe : p.getChildren()) {
			for(Person per : getDescendants(pe)) {
				if(!out.contains(per)) {
					out.add(per);
				}
			}
		}

		return out;
	}

	// Vergleicht Objekte
	@Override
	public boolean equals(Object o) {
		if(o == null) {
			throw new IllegalArgumentException("Kann nicht mit null vergleichen");
		}

		if(!(o instanceof Person)) {
			return false;
		}

		Person p = (Person) o;
		return p.getGender().equals(gender) && p.getName().equals(name);
	}

	@Override
	public String toString() {
		return name + ":" + gender;
	}
}
