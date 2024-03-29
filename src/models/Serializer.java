package models;
/**
 * 
 * @author Emma N� Bhriain
 *  
 */
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JOptionPane;

public class Serializer {
	private File aFile = new File ("UpdatedData.dat");
	private ObjectOutputStream objOStream = null;
	private FileOutputStream outByteStream = null;
	private FileReader fileReader;
	
	private ObjectInputStream objIStream;
	private FileInputStream inByteStream;
	
	private Map<String, ArrayList<String>> inParents;
	private Map<String, Person> inPeople;

	/**
	 * Constructor for writing out the data
	 * @param reader
	 */
	public Serializer(FileReader reader){
		fileReader = reader;
		try{
			outByteStream = new FileOutputStream(aFile);
			objOStream = new ObjectOutputStream(outByteStream);
		}
		catch(IOException e){
			System.out.println("File not found"+e);
		}
	}
	
	/**
	 * Constructor used for reading in saved data
	 */
	public Serializer(){
		try{
			inByteStream = new FileInputStream(aFile);
			objIStream = new ObjectInputStream(inByteStream);
		}
		catch(IOException e){
			System.out.println("Error reading from file" + e);
		}
	}
	
	
	/**
	 * Write the HashMap of parents as well as the HashMap of People to the file
	 * @param file
	 */
	public void writeToFile(){
		
		Map<String, ArrayList<String>> outParents = fileReader.getParentMap();
		Map<String, Person> outPeople = fileReader.getPeopleMap();
		
		try {
			objOStream.writeObject(outParents);
			objOStream.writeObject(outPeople);
			System.out.println("Written to file successful");
			JOptionPane.showMessageDialog(null, "Your changes have been successfully saved",  "Success!", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			System.out.println("error writing to file");
			 JOptionPane.showMessageDialog(null, "Unable to write to file.", "Error", JOptionPane.ERROR_MESSAGE); 

		}
	}
	
	/**
	 * Read in the stored Data from the file
	 */
	@SuppressWarnings("unchecked")
	public void readFromFile(){
		try {
			inParents = (Map<String, ArrayList<String>>)objIStream.readObject();
			inPeople = (Map<String, Person>)objIStream.readObject();
			System.out.println("File reading successful");
			JOptionPane.showMessageDialog(null, "File reading complete",  "Success!", JOptionPane.INFORMATION_MESSAGE);

		} catch (IOException e) {
			System.out.println("Error Reading From File" + e);
			 JOptionPane.showMessageDialog(null, "Unable to read from file.", "Error", JOptionPane.ERROR_MESSAGE); 
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading class");
			 JOptionPane.showMessageDialog(null, "Unable to read from file.", "Error", JOptionPane.ERROR_MESSAGE); 

		}
		
	}
	
	/**
	 * close the input stream
	 */
	public void closeInputs(){
		try{
			objIStream.close();
		}
		catch(IOException e){
			System.out.println("Error closing input stream " + e);
		}
	}
	
	/**
	 * Close the output stream
	 */
	public void close(){
		try {
			objOStream.close();
		} catch (IOException e) {
			System.out.println("Error closing stream");
		}
	}

	public Map<String, ArrayList<String>> getInParents() {
		return inParents;
	}

	public void setInParents(Map<String, ArrayList<String>> inParents) {
		this.inParents = inParents;
	}

	public Map<String, Person> getInPeople() {
		return inPeople;
	}

	public void setInPeople(Map<String, Person> inPeople) {
		this.inPeople = inPeople;
	}

	
}
