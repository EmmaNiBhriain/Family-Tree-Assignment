package models;
import java.io.*;

public class Person implements Serializable{
	private String name;
	private char gender;
	private int birthYear;
	private String mother;
	private String father;
	private Person motherObject;
	private Person fatherObject;
	private int deathYear;
	
	/**
	 * Constructor for a person
	 * @param name
	 * @param gender
	 * @param birthYear
	 * @param mother
	 * @param father
	 */
	public Person(String name, char gender, int birthYear, String mother, String father, Person motherObject, Person fatherObject){
		this.name = name; 
		this.gender = gender;
		this.birthYear = birthYear;
		this.mother = mother;
		this.father = father;
		this.motherObject = motherObject;
		this.fatherObject = fatherObject;
		this.deathYear = 0;
	}
	
	
	
	
	@Override
	public String toString(){
		return "Name : " + this.name + "\t Gender: " + this.gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public String getMother() {
		return mother;
	}

	public void setMother(String mother) {
		this.mother = mother;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public Person getMotherObject() {
		return motherObject;
	}

	public void setMotherObject(Person motherObject) {
		this.motherObject = motherObject;
	}

	public Person getFatherObject() {
		return fatherObject;
	}

	public void setFatherObject(Person fatherObject) {
		this.fatherObject = fatherObject;
	}




	public int getDeathYear() {
		return deathYear;
	}




	public void setDeathYear(int deathYear) {
		if(deathYear>0)
			this.deathYear = deathYear;
		else 
			this.deathYear = 0;
	}
	
	
}
