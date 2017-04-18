import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

import models.FileReader;
import models.Person;
import models.TreePrinter;

public class FileReaderTest {
	private FileReader testFileReader;
	private FileReader testFileReader1;
	private FileReader testFileReader2;
	private FileReader testFileReader3;
	private FileReader testFileReader4;
	private FileReader testFileReader5;
	private FileReader testFileReader6;
	private FileReader testFileReader7;
	private FileReader testFileReader8;
	private FileReader testFileReader9;
	private FileReader testFileReader0;

	private File validTester = new File("small-database.txt"); 
	//TODO MAY BE RECONSIDERED private File invalidTester;

	@Before 
	public void setup(){
		
	}
	
	/**
	 * Test that the program reads in the data provided and creates People objects from them.
	 * Check that an error is thrown if the file does not exist.
	 */
	//@Test
	public void readFileTest() {
		testFileReader = new FileReader();
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
		testFileReader1 = new FileReader();
		//testFileReader1.readFile(validTester);
		//testFileReader1.buildConnections();

		assertEquals(Fixtures.parentsNames[0], testFileReader1.getPeopleMap().get("Ralph").getFatherObject().getName());
		assertEquals(Fixtures.parentsNames[1], testFileReader1.getPeopleMap().get("Brennan").getFatherObject().getName());
		assertEquals(Fixtures.parentsNames[2], testFileReader1.getPeopleMap().get("Franklin").getMotherObject().getName());
		assertEquals(Fixtures.parentsNames[3], testFileReader1.getPeopleMap().get("Franklin").getFatherObject().getName());
		
		//Test that Mariann has one child
		assertEquals(1, testFileReader1.getParentMap().get("Marianna").size());
		
		//Test that Marquise has two children
		assertEquals(2, testFileReader1.getParentMap().get("Marquise").size());

	}
	
	/**
	 * Add Brady's dad to the database - Justin
	 * Check that he appears as a father Object for Brady's object and also that he appears as a son to Brennen
	 * i.e. Justin's fatherObject points to Brennan's object
	 */
	@Test
	public void addTest(){
		testFileReader2 = new FileReader();
		//testFileReader2.readFile(validTester);
		//testFileReader2.buildConnections();
		
		Person added1 = new Person ("Justin", 'M', 1897, "?", "Brennen", null, null);
		
		testFileReader2.add(added1);
		//Test that Justin appears as Brady's fatherObject
		assertEquals("Justin", testFileReader2.getPeopleMap().get("Brady").getFatherObject().getName());
		assertTrue(testFileReader2.getParentMap().containsKey("Justin"));
		
		ArrayList<String> justinsChildren = testFileReader2.getParentMap().get("Justin");
		assertTrue(justinsChildren.contains("Brady"));
		assertEquals(1, justinsChildren.size());

		
		//Test that Justin's fatherObject points to the Person "Brennan"
		assertEquals("Brennen", added1.getFatherObject().getName());
		
		Person added2 = new Person("Abril", 'F', 1855, "?", "?", null, null);
		testFileReader2.add(added2);

		//Test that Abril appears as the MotherObject for Dion
		assertEquals(added2.getName(), testFileReader2.getPeopleMap().get("Dion").getMotherObject().getName());
		
	}

	/**
	 * Marianna is the mother of Franklin : remove her
	 * Check that Franklin's mother object now points to null 
	 * Also check that Marianna's Person object is no longer contained in the HashMap
	 * TODO test that the same works when removing a father.
	 */
	@Test
	public void removeTest(){
		testFileReader3 = new FileReader();
		testFileReader3.readFile(validTester);
		//testFileReader.buildConnections();
		//Person toBeRemoved = testFileReader.getPeopleMap().get("Franklin").getMotherObject();
		testFileReader3.remove("Marianna");
		
		assertEquals(null, testFileReader3.getPeopleMap().get("Franklin").getMotherObject());
		assertFalse(testFileReader3.getPeopleMap().containsKey("Marianna"));
		assertFalse(testFileReader3.getParentMap().containsKey("Marianna"));
		//assertFalse(testFileReader.getPeopleMap().containsValue("Franklin"));

	}
	
	/**
	 * Change Marianna's name to Mary
	 * Check that Marianna is not a key for either the peopleMap or the parentMap
	 * Check that Franklin's mother field is updated
	 * Check that Frankling still points to the correct object
	 * Check that Mary is now a key for the HashMaps
	 */
	@Test
	public void modifyNameTest(){
		testFileReader6 = new FileReader();
		
		Person change = testFileReader6.getPeopleMap().get("Marianna");
		testFileReader6.modify(change, "Mary", ' ', 0, null, null);
		
		assertFalse(testFileReader6.getParentMap().containsKey("Marianna"));
		assertFalse(testFileReader6.getPeopleMap().containsKey("Marianna"));
		
		assertEquals("Mary", testFileReader6.getPeopleMap().get("Franklin").getMother());
		assertEquals("Mary", testFileReader6.getPeopleMap().get("Franklin").getMotherObject().getName());
		
		assertTrue(testFileReader6.getParentMap().containsKey("Mary"));
		assertTrue(testFileReader6.getPeopleMap().containsKey("Mary"));

	}
	
	/**
	 * Change Brady's gender to female
	 * Check that it was updated successfully
	 * Check that Brennan's mother is now Brady and that the motherObject field now points to Brady
	 * Check that Brennan's father is now unknown and the fatherObject field now point to null
	 */
	@Test 
	public void modifyGenderTest(){
		testFileReader7 = new FileReader();
		
		Person genderChange = testFileReader7.getPeopleMap().get("Brady");
		
		testFileReader7.modify(genderChange, null, 'F', 0, null, null);
		
		assertEquals('F', testFileReader7.getPeopleMap().get("Brady").getGender());
		
		assertEquals("Brady", testFileReader7.getPeopleMap().get("Brennan").getMother());
		assertEquals(genderChange, testFileReader7.getPeopleMap().get("Brennan").getMotherObject());
		
		assertEquals("?", testFileReader7.getPeopleMap().get("Brennan").getFather());
		assertEquals(null, testFileReader7.getPeopleMap().get("Brennan").getFatherObject());
	}
	
	/**
	 * Change Franklin's year of birth 
	 * Test that is was updated
	 * 
	 * Do the same for Marianna and Brady
	 */
	@Test
	public void modifyBirthYearTest(){
		testFileReader8 = new FileReader();
		
		Person ageChange = testFileReader8.getPeopleMap().get("Franklin");
		Person Marianna = testFileReader8.getPeopleMap().get("Marianna");
		Person Brady = testFileReader8.getPeopleMap().get("Brady");

		testFileReader8.modify(ageChange, null, ' ', 1977, null, null);
		testFileReader8.modify(Marianna, null, ' ', 1888, null, null);
		testFileReader8.modify(Brady, null, ' ', 1402, null, null);
		
		assertEquals(1977, testFileReader8.getPeopleMap().get("Franklin").getBirthYear());
		assertEquals(1888, testFileReader8.getPeopleMap().get("Marianna").getBirthYear());
		assertEquals(1402, testFileReader8.getPeopleMap().get("Brady").getBirthYear());
	}
	
	/**
	 * Change Dion's mother to ?
	 * Check that his mother is updated and his mother object now points to null
	 * Check that Dion's previous mother, Abril, is no longer in the parent map
	 * 
	 * Change Franklin's mother to Emily
	 * Check that Franklin now appears in Emily's child arraylist
	 * Check that Franklin's mother field is now Emily and that his motherObject points to her object
	 * Check that Marianna (Franklin's previous mother) is no longer a parent
	 * 
	 * Change Saige's mother to Samara
	 * Check that Samara is now a parent
	 * Check that Saige's relevant fields now point to Samara
	 */
	@Test
	public void modifyMotherTest(){
		testFileReader9 = new FileReader();
		Person Dion = testFileReader9.getPeopleMap().get("Dion");
		Person Franklin = testFileReader9.getPeopleMap().get("Franklin");
		Person Saige = testFileReader9.getPeopleMap().get("Saige");
		
		Person Emily = testFileReader9.getPeopleMap().get("Emily");
		Person Samara = testFileReader9.getPeopleMap().get("Samara");
		
		
		testFileReader9.modify(Dion, null, ' ', 0, "?", null);
		assertEquals("?", Dion.getMother());
		assertFalse(testFileReader9.getParentMap().containsKey("Abril"));
		
		
		testFileReader9.modify(Franklin, null,' ' , 0, "Emily", null);
		ArrayList<String> emilys = testFileReader9.getParentMap().get("Emily");
		assertTrue(emilys.contains("Franklin"));
		assertEquals("Emily", Franklin.getMother());
		assertEquals(Emily, Franklin.getMotherObject());
		assertFalse(testFileReader9.getParentMap().containsKey("Marianna"));
		
		testFileReader9.modify(Saige, null, ' ', 0, "Samara", null);
		assertTrue(testFileReader9.getParentMap().containsKey("Samara"));
		ArrayList<String> samaras = testFileReader9.getParentMap().get("Samara");
		assertEquals(1, samaras.size());
		assertTrue(samaras.contains("Saige"));
		assertEquals("Samara", Saige.getMother());
		assertEquals(Samara, Saige.getMotherObject());
	}
	
	/**
	 * Change Ralph's father to ?
	 * Check that his father is updated and his father object now points to null
	 * Check that Ralph's previous father, Marquise, has only one child, Samara
	 * 
	 * Change Jayde's father to Trenton
	 * Check that Jayde now appears in Trenton child arraylist
	 * Check that Jayde's father field is now Trenton and that her fatherObject points to his object
	 * Check that Grayson (Jayde's previous father) is no longer a parent
	 * 
	 * Change Andre's father to Peter (someone not in the database)
	 * Check that Andre's father field is now Peter 
	 * Check that Andre's father Object field is null
	 */
	@Test
	public void modifyFatherTest(){
		testFileReader0 = new FileReader();
		
		Person Ralph = testFileReader0.getPeopleMap().get("Ralph");
		Person Jayde = testFileReader0.getPeopleMap().get("Jayde");
		Person Andre = testFileReader0.getPeopleMap().get("Andre");
		
		Person Trenton = testFileReader0.getPeopleMap().get("Trenton");
		Person Marquise = testFileReader0.getPeopleMap().get("Marquise");
		Person Grayson = testFileReader0.getPeopleMap().get("Grayson");
		Person Peter = new Person("Peter", 'M', 1850, "?", "?", null, null);
		
		//Change Ralph's father to unknown
		testFileReader0.modify(Ralph, null, ' ', 0, null, "?");
		assertEquals("?", Ralph.getFather());
		assertEquals(null, Ralph.getFatherObject());
		
		ArrayList<String> marquises = testFileReader0.getParentMap().get("Marquise");
		assertEquals(1, marquises.size());
		assertFalse(marquises.contains("Ralph"));
		
		//Change Jayde's father to Trenton
		testFileReader0.modify(Jayde, null, ' ', 0, null, "Trenton");
		assertEquals("Trenton", Jayde.getFather());
		assertEquals(Trenton, Jayde.getFatherObject());
		
		ArrayList<String> trentons = testFileReader0.getParentMap().get("Trenton");
		assertTrue(trentons.contains("Jayde"));
		assertFalse(testFileReader0.getParentMap().containsKey("Grayson"));
		
		//Change Andre's father to Peter
		testFileReader0.modify(Andre, null, ' ', 0, null, "Peter");
		assertEquals("Peter", Andre.getFather());
		assertEquals(null, Andre.getFatherObject());
	}
	
	
	@Test 
	public void viewAncestorsTest(){
		testFileReader5 = new FileReader();

		testFileReader5.readFile(validTester);
		testFileReader5.buildConnections();
		
		Person viewFamily = testFileReader5.getPeopleMap().get("Franklin");
		//testFileReader.viewAncestors(viewFamily, " ");
		
		Person viewFamily1 = testFileReader5.getPeopleMap().get("Brennan");
		//testFileReader.viewAncestors(viewFamily1, " ");
		
		Person viewFamily2 = testFileReader5.getPeopleMap().get("Isis");
		//testFileReader.viewAncestors(viewFamily2, " ");
		TreePrinter.print(viewFamily);
		
	}
	
	
}
