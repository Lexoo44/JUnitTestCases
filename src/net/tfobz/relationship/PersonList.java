package net.tfobz.relationship;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class PersonList extends ArrayList<Person>
{
	public PersonList() {
	}
	
	public PersonList(BufferedReader reader) throws IOException {
		readPersons(reader);
	}
	
	public PersonList(String filename) throws FileNotFoundException, IOException {
		FileInputStream in = new FileInputStream(filename);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		readPersons(reader);
	}
	
	private void readPersons(BufferedReader reader) throws IOException {
		String currentLine = reader.readLine();

		if(currentLine == null) {
			throw new IllegalArgumentException("Datei leer");
		}
		
		do {
			String[] split = currentLine.split(";");
			if(split.length != 4) {
				throw new IllegalArgumentException("Ungültiges format");
			}

			Person person = new Person(split[0], Person.Gender.valueOf(split[1]));
			for(Person p : this) {
				if(p.getName().equals(split[2])){
					person.setMother(p);
				} else if(p.getName().equals(split[3])){
					person.setFather(p);
				}
			}

			if(!split[2].equals("null") && person.getMother() == null) {
				throw new IllegalArgumentException("Kann nicht Person " + split[2] + " als mutter setzen");
			}

			if (!split[3].equals("null") && person.getFather() == null) {
				throw new IllegalArgumentException("Kann nicht Person " + split[3] + " als Vater setzen");
			}

			if(contains(person)) {
				throw new IllegalArgumentException("Diese Person exestiert breits");
			}

			add(person);
		} while((currentLine = reader.readLine()) != null);
	}

}
