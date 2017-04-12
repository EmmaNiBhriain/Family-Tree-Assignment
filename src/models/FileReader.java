package models;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class FileReader {
	private static File smallData = new File("small-database.txt");
	private File largeData = new File("large-database.txt");
	public static ArrayList<Person> people = new ArrayList<Person>();
	
	public FileReader(){
		
	}
	
	/**
	 * Read in the Family Tree data and create a person object from each set of data.
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
					people.add(person);
					System.out.println(person.toString());
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not read from file" + e);
		}
	}
	
	public static void main(String[] args){
		FileReader filer = new FileReader();
		filer.readFile(smallData);
	}
}
