package userInterfaces;
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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.FileReader;
import models.Person;

public class ModifierInterface implements ActionListener{
	private JPanel modifyPanel;
	private JPanel topPanel = new JPanel();
	private JPanel middlePanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JLabel instructions;
	private JButton confirm;
	private JTextField name;
	private FileReader fileReader;
	
	private JPanel Panel0 = new JPanel(new GridLayout(1,2));
	private JPanel Panel1= new JPanel(new GridLayout(1,2));
	private JPanel Panel2 = new JPanel(new GridLayout(1,2));
	private JPanel Panel3 = new JPanel(new GridLayout(1,2));
	private JPanel Panel4 = new JPanel(new GridLayout(1,2));
	private JPanel Panel5 = new JPanel(new GridLayout(1,2));
	private JPanel Panel6 = new JPanel(new GridLayout(1,2));
	private JPanel Panel7 = new JPanel(new GridLayout(1,2));
	
	private JLabel nameL = new JLabel("Name : ");
	private JTextField newName;
	private JLabel genderL = new JLabel("Gender : ");
	private JTextField gender;
	private JLabel yearL = new JLabel("Year of Birth : ");
	private JTextField birthYear;
	private JLabel deathL = new JLabel("Year of Death : ");
	private JTextField death;
	private JLabel fatherL = new JLabel("Father : ");
	private JTextField father;
	private JLabel motherL = new JLabel("Mother : ");
	private JTextField mother;
	private JFrame frame;
	private JFrame frame1;
	private String title = "Family Tree";
	private JButton returnButton = new JButton("Return to Main Menu");
	private JButton menuButton = new JButton("Back to Modifier Menu");

	private JLabel display;
	private Person modifyPerson = new Person(null, ' ', 0, null, null, null, null);
	
	/**
	 * Constructor for the user interface that allows the user to make changes to an existing person
	 * @param reader
	 */
	public ModifierInterface(FileReader reader){
		fileReader = reader;
		makeFrame();
	}
	
	public void makeFrame(){
		frame = new JFrame(title);

		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setPreferredSize(new Dimension(500, 400));
		contentPane.setLayout(new BorderLayout(10,0));
		JPanel displayPane = makePanel(contentPane);
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public JPanel makePanel(JPanel panel){
		panel.setLayout(new BorderLayout()); 
		
		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(middlePanel, BorderLayout.CENTER);
		panel.add(bottomPanel, BorderLayout.SOUTH);
				
		instructions = new JLabel("Enter Name of Person to Modify");
		name = new JTextField(10);
		confirm = new JButton("OK");
		confirm.addActionListener(this);
		returnButton.addActionListener(this);
		
		
		topPanel.add(instructions);
		middlePanel.add(name);
		bottomPanel.add(returnButton);
		bottomPanel.add(confirm);
		return panel;
		
	}

	public JPanel getModifyPanel() {
		return modifyPanel;
	}

	public void setModifyPanel(JPanel modifyPanel) {
		this.modifyPanel = modifyPanel;
	}

	/**
	 * Allows the user to select the name of the person they wish to modify
	 * If this Person is not on the system, a warning dialog box appears
	 * Otherwise, a form will appear with the person's existing details and the user may then change these details and save the changes
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("OK")){
			
			String findName = name.getText();
			if(fileReader.getPeopleMap().containsKey(findName)){
				modifyPerson = fileReader.getPeopleMap().get(findName);
				
				frame1 = new JFrame(title);

				JPanel contentPane = (JPanel)frame1.getContentPane();
				contentPane.setLayout(new BorderLayout(10,10));
				//modifyPanel = makeNextPanel();
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //close the current window and redisplay the main menu
				//frame = new JFrame();

				JPanel displayPane = makeNextPanel(contentPane);
				contentPane = displayPane;
				frame1.pack();
				frame1.setVisible(true);
			}
			else
				JOptionPane.showMessageDialog(null, "This person is not stored in the system", "Error", JOptionPane.ERROR_MESSAGE); 
			
			//modifyPanel = makeNextPanel();
		}
		
		else if(e.getActionCommand().equals("Update")){
			String nameChange;
			char newGender;
			int newYear;
			int newDeath;
			String newFather;
			String newMother;
			
			if(newName.getText().equals(modifyPerson.getName())){
				nameChange = null;
			}
			else
				nameChange = newName.getText();
			
			
			if(gender.getText().equals(Character.toString(modifyPerson.getGender()))){
				newGender = ' ';
			}
			else
				 newGender = gender.getText().charAt(0);
			
			
			if(birthYear.getText().equals(Integer.toString(modifyPerson.getBirthYear()))){
				newYear = 0;
			}
			else
				newYear = Integer.parseInt(birthYear.getText());
			
			if(death.getText().equals(Integer.toString(modifyPerson.getDeathYear()))){
				newDeath = 0;
			}
			else
				newDeath = Integer.parseInt(death.getText());
			
			if(father.getText().equals(modifyPerson.getFather())){
				newFather = null;
			}
			else
				newFather = father.getText();
			
			if(mother.getText().equals(modifyPerson.getMother())){
				newMother = null;
			}
			else
				newMother = mother.getText();
			
			
			System.out.println("Inserted text fields : " + nameChange + " " + newGender+ " " + newYear + " " + newFather + " " + newMother);
			modifyPerson = fileReader.modify(modifyPerson, nameChange, newGender, newYear, newFather, newMother);
			modifyPerson.setDeathYear(newDeath);
			JOptionPane.showMessageDialog(null, "Information successfully updated",  "Success!", JOptionPane.INFORMATION_MESSAGE);
			frame1.dispatchEvent(new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING)); //close the current window and redisplay the main menu
			UserInterface user = new UserInterface(fileReader);
		}
		
		else if(e.getActionCommand().equals("Return to Main Menu")){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //close the current window and redisplay the main menu
			UserInterface user = new UserInterface(fileReader);
		}
		
		else if(e.getActionCommand().equals("Back to Modifier Menu")){
			frame1.dispatchEvent(new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING)); //close the current window and redisplay the main menu
			ModifierInterface mod = new ModifierInterface(fileReader);
		}
		
	}
	
	public JPanel makeNextPanel(JPanel panel){
		//JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 2)); 
		//contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));
		display = new JLabel("Enter Details");
		Panel0.add(display);
		
		Panel1.add(nameL);
		newName = new JTextField(modifyPerson.getName());
		addField(Panel1, newName);

		Panel2.add(genderL);
		gender = new JTextField(Character.toString(modifyPerson.getGender()));
		addField(Panel2, gender);

		Panel3.add(yearL);
		birthYear = new JTextField(Integer.toString(modifyPerson.getBirthYear()));
		addField(Panel3, birthYear);
		
		Panel7.add(deathL);
		if(modifyPerson.getDeathYear()==0){
			death = new JTextField("0");
		}
		else
			death = new JTextField(Integer.toString(modifyPerson.getDeathYear()));
		addField(Panel7, death);

		Panel4.add(fatherL);
		father = new JTextField(modifyPerson.getFather());
		addField(Panel4, father);
		
		Panel5.add(motherL);
		mother = new JTextField(modifyPerson.getMother());
		addField(Panel5, mother);

		
		
		JButton okay = new JButton("Update");		
		okay.addActionListener(this);

		menuButton.addActionListener(this);

		Panel6.add(menuButton);
		Panel6.add(okay);
		
		//panel.add(Panel0);
		panel.add(Panel1);
		panel.add(Panel2);
		panel.add(Panel3);
		panel.add(Panel7);
		panel.add(Panel4);
		panel.add(Panel5);
		panel.add(Panel6);

		return panel;
		
	}
	
	public void addField(JPanel panel, JTextField text){
		//text.addActionListener(this); //method to handle the action is in this class
		panel.add(text);
	}

}
