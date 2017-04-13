package models;
import java.io.File;
import java.io.FileNotFoundException;
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

	
	public FileReader(){
		
	}
	
	/**
	 * Read in the Family Tree data and create a person object from each set of data.
	 * Add each person to a priority queue sorted by year of birth
	 * Add each person to a Hashmap using their name as a key
	 */
	public void readFile(File familyData){
		Scanner inFamily;
		try {
			inFamily = new Scanner(familyData);
			String delim = "\\s"; //split fields by a space.
			while(inFamily.hasNextLine()){
				String wordDetails = inFamily.nextLine().trim();
				String[] allWords = wordDetails.split(delim);
				
				if(allWords.length > 4){
					Person person = new Person(allWords[0], allWords[1].charAt(0), Integer.parseInt(allWords[2]), allWords[3], allWords[4], null, null);
					peopleStack.push(person);
					peopleMap.put(person.getName(), person);
					System.out.println(person.toString());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read from file" + e);
		}
	}
	
	/**
	 * For each person, if their father is known, get the father object from the hashmap,
	 * add it to the current person as a father Node.
	 * Do the same for the mother.
	 */
	public void buildConnections(){
		while(peopleStack.size() != 0){
			Person child = peopleStack.pop();
			String father = child.getFather();
			String mother = child.getMother();
			
			if(!father.equals("?")){
				Person validDad = peopleMap.get(father);
				child.setFatherObject(validDad);
				System.out.println("Dad added");
			}
			
			if(!mother.equals("?")){
				Person validMom = peopleMap.get(mother);
				child.setMotherObject(validMom);
				System.out.println("Mom added");
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
		buildConnections();		//as the only item in the stack, get the parents from the map
		
		/*for each member of the map, if addedPerson's gender = f, check if the mother is of the same name
		if addedPerson's gender = m, check is the father is of the same name
		if so, add the addedPerson as a parent to that person*/
		Iterator<Entry<String, Person>> it = peopleMap.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<String, Person> people = (Map.Entry<String, Person>)it.next();
		    if(addedPerson.getGender() == 'M'){
		    	if(people.getValue().getFather().equals(addedPerson.getName())){
		    		people.getValue().setFatherObject(addedPerson);
		    	}
		    }
		    else{
		    	if(people.getValue().getMother().equals(addedPerson.getName())){
		    		people.getValue().setMotherObject(addedPerson);
		    	}
		    }
		    System.out.println(people.getKey() + " = " + people.getValue());
		}
		System.out.println("Hashmap Size = " + peopleMap.size());
		
	}
	
	public static void main(String[] args){
		FileReader filer = new FileReader();
		filer.readFile(smallData);
		filer.buildConnections();
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


	
}
