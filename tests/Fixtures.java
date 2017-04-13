import models.Person;
public class Fixtures {

	/**
	 * An array of people used for testing purposes.
	 */
	public static Person[] people ={
			new Person("Ralph", 'M', 1939, "?", "Marquise", null, null),
			new Person("Brennan", 'M', 1941, "?", "Brady", null, null),
			new Person("Franklin", 'M', 1889, "Marianna", "Andre", null, null),
			new Person("Marquise", 'M', 1908, "?", "?", null, null),
			new Person("Brady", 'M', 1917, "?", "Justin", null, null),
			new Person("Marianna", 'F', 1851, "?", "?", null, null), 
			new Person("Andre", 'M', 1852, "?", "?", null, null),
			new Person("Brennen", 'M', 1867, "?", "?", null, null)
		};
	
	public static String[] parentsNames = {
			"Marquise",
			"Brady",
			"Marianna",
			"Andre"
	};
}
