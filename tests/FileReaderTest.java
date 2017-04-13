import static org.junit.Assert.*;

import java.io.File;
import java.util.EmptyStackException;

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
		
		assertEquals(Fixtures.people[6].getName(), testFileReader.getPeopleStack().peek().getName());
		assertEquals(Fixtures.people[6].getBirthYear(), testFileReader.getPeopleStack().peek().getBirthYear());
		assertEquals(Fixtures.people[6].getFather(), testFileReader.getPeopleStack().peek().getFather());
		
		//Check that an IndexOutOfBoundsException is thrown for invalid array index.
		try{
			testFileReader.getPeopleStack().clear();
			testFileReader.getPeopleStack().peek().getName();
			fail("Expected error not thrown");
		}
		catch(EmptyStackException e){
			assertTrue(true);
		}
	}
	
	/**
	 * Test that parents are being added as fields of a Person object
	 * A pointer is made from a person and a parent (person Object)
	 * Check that the name of the parent object for the array of people is correct
	 */
	@Test 
	public void buildConnectionsTest(){
		testFileReader.readFile(validTester);
		testFileReader.buildConnections();

		assertEquals(Fixtures.parentsNames[0], testFileReader.getPeopleMap().get("Ralph").getFatherObject().getName());
		assertEquals(Fixtures.parentsNames[1], testFileReader.getPeopleMap().get("Brennan").getFatherObject().getName());
		assertEquals(Fixtures.parentsNames[2], testFileReader.getPeopleMap().get("Franklin").getMotherObject().getName());
		assertEquals(Fixtures.parentsNames[3], testFileReader.getPeopleMap().get("Franklin").getFatherObject().getName());
	}

}
