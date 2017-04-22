package models;
/**
 * 
 * @author Emma Ní Bhriain
 *
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TreePrinter {
	
	private static List<Person> people  = new ArrayList<Person>();
	
	private static int height;
	private static Person unknown = new Person ("?", ' ', 0, "?", "?", null, null);
	private static Person temp1 = new Person("unknown", ' ', 0, "?", null, null, null); 
	private static Person temp2 = new Person("unknown", ' ', 0, null, null, null, null); 
	private static JTextArea viewPanel = new JTextArea();

	
	public static JTextArea print(Person root){
		people.add(root);
		height = TreePrinter.getHeight(root);
		viewPanel = printTree(people, height);
		return viewPanel;
	}
	
	/**
	 * Print the Tree 
	 * Pass the root in the List and height of the tree
	 * 
	 * 
	 * @param levelPeople
	 * @param level
	 */
	private static JTextArea printTree(List<Person> levelPeople, int level){
		//List<Person> people = new ArrayList<Person>();
		int exponent = height - level;
		int sizeOfArray = (int)Math.pow(2, exponent);
		Person[] peopleLayer = new Person[sizeOfArray];
		
		
		//indentation for first node of certain level
		printIndent(level);
		int size = levelPeople.size();
		for(int i =0; i<size; i++){
		
			Person temp = levelPeople.get(0);//peopleLayer[i];//levelPeople.get(0);
			//print name
			System.out.print(temp.getName());
			viewPanel.append(temp.getName());
			
			//spaces between names, depending on the level
			printSpacing(level);
			
			//if the parents are known
			if(level>2){
				if((temp.getFatherObject()!=null)&&(temp.getMotherObject()!= null)){
					//temp1 = temp.getFatherObject();
					//temp2 = temp.getMotherObject();
					levelPeople.add(temp.getFatherObject());
					levelPeople.add(temp.getMotherObject());
				}
				
				
				else if((temp.getFatherObject()==null)&&(temp.getMotherObject()== null)){
					if(!temp.getFather().equals("?")){
						Person blank = new Person(temp.getFather(), 'M', 0, "?", "?", null, null);
						levelPeople.add(blank);
					}
					else
						levelPeople.add(unknown);
					
					if(!temp.getMother().equals("?")){
						Person blank = new Person(temp.getMother(), 'F', 0, "?", "?", null, null);
						levelPeople.add(blank);
					}
					else
						levelPeople.add(unknown);
					//nothing happens, end of tree
				}
				
				else if((temp.getFatherObject()!=null)&&(temp.getMotherObject()== null)){
					levelPeople.add(temp.getFatherObject());
					if(!temp.getMother().equals("?")){
						Person blank = new Person(temp.getMother(), 'F', 0, "?", "?", null, null);
						levelPeople.add(blank);
					}
					else{
						levelPeople.add(unknown);
					}
				}
				
				else if((temp.getFatherObject()==null)&&(temp.getMotherObject()!= null)){
					if(!temp.getFather().equals("?")){
						Person blank = new Person(temp.getFather(), 'M', 0, "?", "?", null, null);
						levelPeople.add(blank);
					}
					else{
						levelPeople.add(unknown);
					}
					levelPeople.add(temp.getMotherObject());
				}
			}
			levelPeople.remove(temp);
			//System.out.println(); //go to a new line

			
		}
		//levelPeople.remove(temp);
		System.out.println(); //go to a new line
		viewPanel.append("\n\n");
		
		if(level>2){
			printTree(people, level-1);
		}
		
		return viewPanel;
	}
	
	/**
	 * print the indent for the relevant level of the person in the tree
	 * the lower the level, the smaller the indent
	 * @param level
	 */
	private static void printIndent(int level){
		int g = (int) Math.pow(2, level-1); //2 to the power of the level -1
		for(int i = g ; i>0; i--){
			System.out.print(" ");
			viewPanel.append(" ");
		}
	}

	/**
	 * Print the horizontal space between nodes on the same level
	 */
	private static void printSpacing(int level){
		int h = (int) ((Math.pow(2, level-1))*2)-1;
		for(int i = h; i>0; i--){
			System.out.print(" ");
			viewPanel.append(" ");
		}
	}
	  
	/**
	 * Get the height of the tree, starting with the given person as the root
	 * @param person
	 * @return
	 */
	private static int getHeight(Person person){
		if(person == null)
			return 1;
		else{
			int fatherTree = getHeight(person.getFatherObject());
			int motherTree = getHeight(person.getMotherObject());
			return 1 + Math.max(fatherTree, motherTree);
	
		}
	}

}
