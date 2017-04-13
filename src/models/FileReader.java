package models;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class FileReader {
	private static File smallData = new File("small-database.txt");
	private File largeData = new File("large-database.txt");
	//private  ArrayList<Person> people = new ArrayList<Person>();
	private Queue<Person> peopleQ = new PriorityQueue<Person>();
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
					Person person = new Person(allWords[0], allWords[1].charAt(0), Integer.parseInt(allWords[2]), allWords[3], allWords[4]);
					peopleQ.add(person);
					peopleMap.put(person.getName(), person);
					System.out.println(person.toString());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read from file" + e);
		}
	}
	
	public void buildConnections(){
		
	}
	
	public static void main(String[] args){
		FileReader filer = new FileReader();
		filer.readFile(smallData);
	}

	public Queue<Person> getPeopleQ() {
		return peopleQ;
	}

	public void setPeopleQ(Queue<Person> peopleQ) {
		this.peopleQ = peopleQ;
	}

	
}
