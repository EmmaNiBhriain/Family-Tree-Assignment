package models;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Stack;


public class FileReader {
	private static File smallData = new File("small-database.txt");
	private static File largeData = new File("large-database.txt");
	//private  ArrayList<Person> people = new ArrayList<Person>();
	private Stack<Person> peopleStack = new Stack<Person>();
	private Map<String, Person> peopleMap = new HashMap<String, Person>();
	private Map<String, ArrayList<String>> parentMap = new HashMap<String, ArrayList<String>>(); //Mapping of parent to children

	private boolean DEBUG = false;
	
	public FileReader(){
		readFile(smallData);
		buildConnections();
		
	}
	
	/**
	 * Read in the Family Tree data and create a person object from each set of data.
	 * Add each person to a stack.
	 * Add each person to a Hashmap using their name as a key
	 */
	public void readFile(File familyData){
		Scanner inFamily;
		try {
			inFamily = new Scanner(familyData);
			String delim = "\\s"; //split fields by a space.
			while(inFamily.hasNextLine()){
				String personDetails = inFamily.nextLine().trim();
				String[] allDetails = personDetails.split(delim);
				
				if(allDetails.length > 4){
					Person person = new Person(allDetails[0], allDetails[1].charAt(0), Integer.parseInt(allDetails[2]), allDetails[3], allDetails[4], null, null);
					peopleStack.push(person);
					peopleMap.put(person.getName(), person);
					if(DEBUG==true)System.out.println(person.toString());
				}
			}
			if(DEBUG == true)System.out.println("Hashmap size: " + peopleMap.size());
		} catch (FileNotFoundException e) {
			System.out.println("Could not read from file" + e);
		}
	}
	
	/**
	 * For each person, if their father is known, get the father object from the hashmap,
	 * add it to the current person as a father Node.
	 * Do the same for the mother.
	 * 
	 * For each parent, add them to a hashmap along with their children.
	 * TODO error checking for when a parent is not in the map but is not ?, null pointer exception?
	 */
	public void buildConnections(){
		while(peopleStack.size() != 0){
			Person child = peopleStack.pop();
			String father = child.getFather();
			String mother = child.getMother();
			
			if(!father.equals("?")){
				Person validDad = peopleMap.get(father);
				child.setFatherObject(validDad);
				
				//if the parentMap does not already contain the parent, add them
				//else, update the children
				if(!parentMap.containsKey(father)){
					ArrayList<String> children = new ArrayList<String>();
					children.add(child.getName());
					parentMap.put(father, children);
				}
				else{
					ArrayList<String> temp = parentMap.get(father);
					if(!temp.contains(child.getName())){
						temp.add(child.getName());
						parentMap.replace(father, temp);
					}
					
				}
				if(DEBUG==true)System.out.println("Dad added");
			}
			
			if(!mother.equals("?")){
				Person validMom = peopleMap.get(mother);
				child.setMotherObject(validMom);
				if(DEBUG==true)System.out.println("Mom added");
				
				if(!parentMap.containsKey(mother)){
					ArrayList<String> children = new ArrayList<String>();
					children.add(child.getName());
					parentMap.put(mother, children);
				}
				else{
					ArrayList<String> temp = parentMap.get(mother);
					if(!temp.contains(child.getName())){
						temp.add(child.getName());
						parentMap.replace(father, temp);					
					}
				}
			}
		}
	}
	
	/**
	 * Check if the parents of the new person are in the map, if so, add them as parentObjects
	 * Check it the new person is a parent of anyone else. If so, add him/her as a parentObject
	 * @param addedPerson
	 */
	public void add(Person addedPerson){		
		peopleStack.push(addedPerson); //TODO USE something other than a stack because I need to check if new person is a parent of any person
		peopleMap.put(addedPerson.getName(), addedPerson);
		buildConnections();		//as the only item in the stack, get the parents from the ma
		
		//Check if the new person is a parent, if so, get the relevant children objects and add a pointer to the new person
		if(parentMap.containsKey(addedPerson.getName())){
			ArrayList<String> temp = parentMap.get(addedPerson.getName());
			
			for(String name : temp){
				Person child = peopleMap.get(name);
				if(addedPerson.getGender()=='F')
					child.setMotherObject(addedPerson);
				else
					child.setFatherObject(addedPerson);
			}
		}
		
		if(DEBUG==true)System.out.println("Hashmap Size = " + peopleMap.size());
		
	}
	
	/**
	 * If the removedPerson was the parent of another person, change the relevant parentObject field to null
	 * Then delete the Person object
	 * @param removedPerson
	 */
	public void remove(String name) {
		if (peopleMap.containsKey(name)) {
			Person removedPerson = peopleMap.get(name);

			if (parentMap.containsKey(name)) {
				ArrayList<String> temp = parentMap.get(name);

				if (removedPerson.getGender() == 'M') {
					for (String child : temp) {
						Person change = peopleMap.get(child);
						change.setFatherObject(null);
					}
				} else {
					for (String child : temp) {
						Person change = peopleMap.get(child);
						change.setMotherObject(null);
					}
				}
				
				parentMap.remove(name); //remove the entry for the removed Person in the parent Hashmap.
			} else{
				System.out.println("Not a parent");
			}
			peopleMap.remove(name);
		}
		else
			System.out.println("Sorry, invalid name");
		// Person temp = peopleMap.get(removedPerson.getName());
		
	}
	
	/**
	 * Change details of a Person.
	 * If the parameters are not null, update the relevant fields.
	 * If updating a parent, it is necessary to check if the new parent is in the Hashmap and if so, 
	 * point the relevant parent object field to the parent object.
	 * TODO if a parent is changed, make the changes for any siblings who point to the same person object
	 * TODO if a parent name is changed, change the parent name in the field for the child
	 * @param person
	 * @param name
	 * @param gender
	 * @param birthYear
	 * @param mother
	 * @param father
	 */
	public Person modify(Person person, String name, char gender, int birthYear, String mother, String father){
		if(name!= null){
			/* remove the person objects from the maps     
			 * 1. Set the new name as the key for the person in the peopleMap
			 * 2. For each of the parents of this person, go the the parentMap, retrieve their children collections and replace the old name with the new name
			 * 3. If the person was a parent, replace the entry in the parentMap with their new name, for each of their children, update their parent Fields
			 * */
			//Part1
			String previousName = person.getName();
			//Person temp = peopleMap.get(previousName);
			peopleMap.remove(previousName);
			person.setName(name);
			peopleMap.put(name, person);
			
			//Part2
			String tempMom = person.getMother();
			if(!tempMom.equals("?")){
				ArrayList<String> children =  parentMap.get(tempMom);
				if(children.contains(previousName)){
					children.remove(previousName);
					children.add(name);
				}
			}
			
			String tempDad = person.getFather();
			if(!tempDad.equals("?")){
				ArrayList<String> children = parentMap.get(tempDad);
				if(children.contains(previousName)){
					children.remove(previousName);
					children.add(name);
				}
			}
			
			//Part3
			if(parentMap.containsKey(previousName)){
				ArrayList<String> children = parentMap.get(previousName);
				for(String child: children){
					Person childObj = peopleMap.get(child);
					if(person.getGender()=='M'){
						childObj.setFather(name);
					}
					else
						childObj.setMother(name);
				}
				
				parentMap.remove(previousName);
				parentMap.put(name, children);
			}
			
		}
		
		/*
		 * Change the gender of the person
		 * If the person is a parent, and the other parent of their child is unknown, 
		 * update the child's fields to point the other parent object to the parent
		 * i.e If Mary is the registered as the father of Paul, and the gender is updated to be male, 
		 * change Mary to Paul's mother, provided he does not already have a mother
		 * 
		 */
		if(gender != ' '){
			if(person.getGender()!=gender){
				char oldGender = person.getGender();
				person.setGender(gender);

				if(parentMap.containsKey(person.getName())){
					ArrayList<String> children = parentMap.get(person.getName());
					for(String childName : children){
						Person child = peopleMap.get(childName);
						if(oldGender == 'M'){
							if(child.getMother().equals("?")){
								child.setMother(person.getName());
								child.setFather("?");
								child.setMotherObject(person);
								child.setFatherObject(null);
							}
						}
						else if(oldGender == 'F'){
							if(child.getFather().equals("?")){
								child.setFather(person.getName());
								child.setMother("?");
								child.setFatherObject(person);
								child.setMotherObject(null);
							}
						}
					}
				}
			}
		}
		
		
		if(birthYear!= 0){
			if(birthYear != person.getBirthYear()){
				person.setBirthYear(birthYear);
			}
		}
		
		/*
		 * If the new mother is in the database, create a pointer from the person to this person
		 * If the old mother is known, remove this person from her ArrayList in the parentMap
		 * If the new Mother was already a mother, add this person to her ArrayList of children
		 * If she is a first time mother, create an entry for her in the parentMap with this person as her only child
		 * 
		 */
		if(mother!= null){
			String oldMother = person.getMother();
			if(!oldMother.equals(mother)){
				person.setMother(mother);
				if(peopleMap.containsKey(mother)){
					Person tempMother = peopleMap.get(mother);
					person.setMotherObject(tempMother);
				}
				else
					person.setMotherObject(null);
				

				if((!oldMother.equals("?"))&&(parentMap.containsKey(oldMother))){
					ArrayList<String> kids = parentMap.get(oldMother);
					kids.remove(person.getName());
					if(kids.size()==0)
						parentMap.remove(oldMother);
				}
				
				if(parentMap.containsKey(mother)){
					ArrayList<String> mChildren = parentMap.get(mother);
					mChildren.add(person.getName());
					parentMap.replace(mother, mChildren);
				}
				else{
					ArrayList<String> newChild = new ArrayList<String>();
					newChild.add(person.getName());
					parentMap.put(mother, newChild);
				}
			}
		}
			
		
		/*
		 * If the new father is in the database, create a pointer from the person to this person
		 * If the old father is known, remove this person from his ArrayList in the parentMap
		 * If the new father was already a father, add this person to his ArrayList of children
		 * If he is a first time father, create an entry for him in the parentMap with this person as his only child
		 * 
		 */
		if(father!= null){
			String oldFather = person.getFather();
			if(!oldFather.equals(father)){
				person.setFather(father);
				if(peopleMap.containsKey(father)){
					Person tempFather = peopleMap.get(father);
					person.setFatherObject(tempFather);
				}
				else
					person.setFatherObject(null);
				

				if((!oldFather.equals("?"))&&(parentMap.containsKey(oldFather))){
					ArrayList<String> kids = parentMap.get(oldFather);
					kids.remove(person.getName());
					if(kids.size()==0)
						parentMap.remove(oldFather);
				}
				
				if(parentMap.containsKey(father)){
					ArrayList<String> mChildren = parentMap.get(father);
					mChildren.add(person.getName());
					parentMap.replace(father, mChildren);
				}
				else{
					ArrayList<String> newChild = new ArrayList<String>();
					newChild.add(person.getName());
					parentMap.put(father, newChild);
				}
			}
		}
		return person;
	}
	
	
	public void viewAncestors(Person root, String indent){
		if (root == null)
			return;
		System.out.println(" " + indent + root.getName());
		if ((root.getFatherObject() != null) && (root.getMotherObject() == null)){
			if(!root.getMother().equals("?"))
				System.out.println("  " + indent + root.getMother());
			viewAncestors(root.getFatherObject(), indent + " ");
		}
		else if ((root.getMotherObject() != null) && (root.getFatherObject() == null)){
			if(!root.getFather().equals("?"))
				System.out.println("  " + indent + root.getFather());
			viewAncestors(root.getMotherObject(), indent + " ");
		}
		else if ((root.getMotherObject() != null) && (root.getFatherObject() != null)) {
			Person left = root.getFatherObject();
			viewAncestors(root.getMotherObject(), indent + " ");
			viewAncestors(left, indent + " ");
		}
		else if((root.getMotherObject() == null) && (root.getFatherObject() == null)){
			if(!root.getFather().equals("?"))
				System.out.println("  " + indent + root.getFather());
			if(!root.getMother().equals("?"))
				System.out.println("  " + indent + root.getMother());
		}
	}
	
	
	
	public static void main(String[] args){
		FileReader filer = new FileReader();
		//Person viewFamily1 = filer.getPeopleMap().get("Isis");
		//TreePrinter.print(viewFamily1);
		//filer.readFile(smallData);
		//filer.buildConnections();
		UserInterface ui = new UserInterface();
		//MenuInterface menu = new MenuInterface();
	}

	public Stack<Person> getPeopleStack() {
		return peopleStack;
	}

	public void setPeopleStack(Stack<Person> peopleStack) {
		this.peopleStack = peopleStack;
	}

	public Map<String, Person> getPeopleMap() {
		return peopleMap;
	}

	public void setPeopleMap(Map<String, Person> peopleMap) {
		this.peopleMap = peopleMap;
	}

	public Map<String, ArrayList<String>> getParentMap() {
		return parentMap;
	}

	public void setParentMap(Map<String, ArrayList<String>> parentMap) {
		this.parentMap = parentMap;
	}


	
}
