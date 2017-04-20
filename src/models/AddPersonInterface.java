package models;
/**
 * 
 * @author Emma Ní Bhriain
 *
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddPersonInterface implements ActionListener{
	private FileReader fileReader;
	private JLabel nameL = new JLabel("Name : ");
	private JTextField name = new JTextField (10);
	private JLabel genderL = new JLabel("Gender : ");
	private JTextField gender = new JTextField (10);
	private JLabel yearL = new JLabel("Year of Birth : ");
	private JTextField birthYear = new JTextField (10);
	private JLabel deathYearL = new JLabel("Year of Death : ");
	private JTextField deathYear = new JTextField (10);
	private JLabel fatherL = new JLabel("Father : ");
	private JTextField father = new JTextField (10);
	private JLabel motherL = new JLabel("Mother : ");
	private JTextField mother = new JTextField (10);
	private JPanel AddPanel;
	private JLabel display;
	private JPanel Panel1= new JPanel(new GridLayout(1,2));
	private JPanel Panel2 = new JPanel(new GridLayout(1,2));
	private JPanel Panel3 = new JPanel(new GridLayout(1,2));
	private JPanel Panel4 = new JPanel(new GridLayout(1,2));
	private JPanel Panel5 = new JPanel(new GridLayout(1,2));
	private JPanel Panel6 = new JPanel(new GridLayout(1,2));
	private JPanel Panel7 = new JPanel(new GridLayout(1,2));
	private JFrame frame;
	private String title = "Family Tree";


	private JButton returnButton = new JButton("Return to Main Menu");


	/**
	 * Constructor for the User Interface that allows the user to add a person to the program
	 * @param reader
	 */
	public AddPersonInterface(FileReader reader){
		fileReader = reader;
		makeFrame();
	}
	
	
	public void makeFrame(){
		frame = new JFrame(title);

		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setPreferredSize(new Dimension(500, 400));
		contentPane.setLayout(new BorderLayout(10,0));
		//contentPane = makePanel(contentPane);
		//TODO set border if you like
		JPanel displayPane = makePanel(contentPane);
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Returns the JPanel with a selection of labels and fields to be filled out
	 * @param panel
	 * @return
	 */
	public JPanel makePanel(JPanel panel){
		
		panel.setLayout(new GridLayout(7, 2)); 
		//contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));
		
		Panel1.add(nameL);
		addField(Panel1, name);

		Panel2.add(genderL);
		addField(Panel2, gender);

		Panel3.add(yearL);
		addField(Panel3, birthYear);

		Panel4.add(fatherL);
		addField(Panel4, father);
		
		Panel5.add(motherL);
		addField(Panel5, mother);
		
		Panel7.add(deathYearL);
		addField(Panel7,deathYear);
				
		
		JButton okay = new JButton("Add");		
		okay.addActionListener(this);

		//display = new JLabel("Enter Details");
		//displayPanel.add(display);
		returnButton.addActionListener(this);
		
		Panel6.add(returnButton);
		Panel6.add(okay);
		
		panel.add(Panel1);
		panel.add(Panel2);
		panel.add(Panel3);
		panel.add(Panel4);
		panel.add(Panel5);
		panel.add(Panel7);
		panel.add(Panel6);

		return panel;
		
	}
	
	public void addField(JPanel panel, JTextField text){
		//text.addActionListener(this); //method to handle the action is in this class
		panel.add(text);
	}

	/**
	 * If the "Add" button is pressed, a new person is created using the input information 
	 * Connections are made between this person and their relatives if their relatives are stored in the program
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("Add")){
			System.out.println("Name " + name.getText());
			System.out.println("Gender " + gender.getText());
			System.out.println("Year of Birth " + birthYear.getText());
			System.out.println("Father " + father.getText());
			System.out.println("Mother " + mother.getText());
			boolean allow;
			String newName;
			if(name.getText().equals("")){
				allow = false;
				newName = "unknown";
				JOptionPane.showMessageDialog(null, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE); 
			}
			else{
				newName = name.getText();
				allow = true;
			}
			
			char newGender;
			System.out.println(gender.getText());
			if((!gender.getText().equalsIgnoreCase(("M")))|(!gender.getText().equalsIgnoreCase("F")))
				newGender = ' ';
			else
				newGender = gender.getText().toUpperCase().charAt(0);
			
			int newYear;
			if(birthYear.getText().equals(""))
				newYear = 0;
			else
				newYear = Integer.parseInt(birthYear.getText());
			
			String newFather;
			if(father.getText().equals(""))
				newFather = "?";
			else
				newFather = father.getText();
			
			String newMother;
			if(mother.getText().equals(""))
				newMother = "?";
			else
				newMother = mother.getText();
			
			int newDeath;
			if(deathYear.getText().equals("")){
				newDeath = 0;
			}
			else
				newDeath = Integer.parseInt(deathYear.getText());
			
			
			if(allow == true){
				Person newPerson = new Person(newName, newGender, newYear, newFather, newMother, null, null);
				if(newDeath>0)
					newPerson.setDeathYear(newDeath);
				if(fileReader.getPeopleMap().containsKey(newPerson.getName())){
					 JOptionPane.showMessageDialog(null, "Unable to add Person. \nThis person already exists in the database.", "Warning", JOptionPane.ERROR_MESSAGE); 
				}
				else{
					fileReader.add(newPerson);
					JOptionPane.showMessageDialog(null, "You have successfully added a Person",  "Success!", JOptionPane.INFORMATION_MESSAGE);
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //close the current window and redisplay the main menu
					UserInterface user = new UserInterface(fileReader);
				}
			}
		
		}
		
		else if(event.getActionCommand().equals("Return to Main Menu")){
			//close this window
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //close the current window and redisplay the main menu
			UserInterface user = new UserInterface(fileReader);
		}
		

	}
	
	public JPanel getAddPanel() {
		return AddPanel;
	}

	public void setAddPanel(JPanel addPanel) {
		AddPanel = addPanel;
	}
}
