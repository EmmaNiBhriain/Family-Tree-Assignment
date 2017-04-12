import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import models.FileReader;

public class FileReaderTest {
	private FileReader testFileReader = new FileReader();
	private File validTester = new File("small-database.txt"); 
	//TODO MAY BE RECONSIDERED private File invalidTester;

	@Before 
	public void setup(){
		
	}
	
	/**
	 * Test that the program reads in the data provided and creates People objects from them.
	 * Check that an error is thrown if the file does not exist.
	 */
	@Test
	public void readFileTest() {
		testFileReader.readFile(validTester);
		
		assertEquals(Fixtures.people[0].getName(), testFileReader.people.get(0).getName());
		assertEquals(Fixtures.people[0].getBirthYear(), testFileReader.people.get(0).getBirthYear());
		assertEquals(Fixtures.people[0].getFather(), testFileReader.people.get(0).getFather());
		
	}

}
