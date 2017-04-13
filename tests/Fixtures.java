import models.Person;
public class Fixtures {

	/**
	 * An array of people used for testing purposes.
	 */
	public static Person[] people ={
			new Person("Ralph", 'M', 1939, "?", "Marquise"),
			new Person("Brennan", 'M', 1941, "?", "Brady"),
			new Person("Franklin", 'M', 1889, "Marianna", "Andre"),
			new Person("Marquise", 'M', 1908, "?", "?"),
			new Person("Brady", 'M', 1917, "?", "Justin"),
			new Person("Marianna", 'F', 1851, "?", "?"), 
			new Person("Andre", 'M', 1852, "?", "?")
		};
	
	public static String[] parentsNames = {
			"Marquise",
			"Brady",
			"Marianna",
			"Andre"
	};
}
