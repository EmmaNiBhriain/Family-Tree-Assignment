import static org.junit.Assert.*;

import java.io.File;
import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

import models.FileReader;
import models.Person;

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
		int lastIndex = Fixtures.people.length - 1;
		assertEquals(Fixtures.people[lastIndex].getName(), testFileReader.getPeopleStack().peek().getName());
		assertEquals(Fixtures.people[lastIndex].getBirthYear(), testFileReader.getPeopleStack().peek().getBirthYear());
		assertEquals(Fixtures.people[lastIndex].getFather(), testFileReader.getPeopleStack().peek().getFather());
		
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
	
	/**
	 * Add Brady's dad to the database - Justin
	 * Check that he appears as a father Object for Brady's object and also that he appears as a son to Brennen
	 * i.e. Justin's fatherObject points to Brennan's object
	 */
	@Test
	public void addTest(){
		testFileReader.readFile(validTester);
		testFileReader.buildConnections();
		
		Person added1 = new Person ("Justin", 'M', 1897, "?", "Brennen", null, null);
		
		testFileReader.add(added1);
		//Test that Justin appears as Brady's fatherObject
		assertEquals(added1.getName(), testFileReader.getPeopleMap().get("Brady").getFatherObject().getName());
		
		//Test that Justin's fatherObject points to the Person "Brennan"
		assertEquals("Brennen", added1.getFatherObject().getName());
		
		Person added2 = new Person("Abril", 'F', 1855, "?", "?", null, null);
		testFileReader.add(added2);

		//Test that Abril appears as the MotherObject for Dion
		assertEquals(added2.getName(), testFileReader.getPeopleMap().get("Dion").getMotherObject().getName());
	}

	/**
	 * Marianna is the mother of Franklin : remove her
	 * Check that Franklin's mother object now points to null 
	 * Also check that Marianna's Person object is no longer contained in the HashMap
	 */
	@Test
	public void removeTest(){
		testFileReader.readFile(validTester);
		testFileReader.buildConnections();
		Person toBeRemoved = testFileReader.getPeopleMap().get("Franklin").getMotherObject();
		testFileReader.remove(toBeRemoved);
		
		assertEquals(null, testFileReader.getPeopleMap().get("Franklin").getMotherObject());
		assertFalse(testFileReader.getPeopleMap().containsKey("Marianna"));
		assertFalse(testFileReader.getPeopleMap().containsValue(toBeRemoved));

	}
}
