package models;

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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddPersonInterface implements ActionListener{
	private FileReader fileReader;
	private JLabel nameL = new JLabel("Name : ");
	private JTextField name = new JTextField (10);
	private JLabel genderL = new JLabel("Gender : ");;
	private JTextField gender = new JTextField (10);
	private JLabel yearL = new JLabel("Year of Birth : ");;
	private JTextField birthYear = new JTextField (10);
	private JLabel fatherL = new JLabel("Father : ");;
	private JTextField father = new JTextField (10);
	private JLabel motherL = new JLabel("Mother : ");;
	private JTextField mother = new JTextField (10);
	private JPanel AddPanel;
	private JLabel display;
	private JPanel Panel1= new JPanel(new GridLayout(1,2));
	private JPanel Panel2 = new JPanel(new GridLayout(1,2));
	private JPanel Panel3 = new JPanel(new GridLayout(1,2));
	private JPanel Panel4 = new JPanel(new GridLayout(1,2));
	private JPanel Panel5 = new JPanel(new GridLayout(1,2));
	private JPanel Panel6 = new JPanel(new GridLayout(1,2));
	private JFrame frame;
	private String title = "Family Tree";


	private JButton returnButton = new JButton("Return to Main Menu");



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
	
	public JPanel makePanel(JPanel panel){
		
		panel.setLayout(new GridLayout(6, 2)); 
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
		panel.add(Panel6);

		return panel;
		
	}
	
	public void addField(JPanel panel, JTextField text){
		//text.addActionListener(this); //method to handle the action is in this class
		panel.add(text);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("Add")){
			System.out.println("Name " + name.getText());
			System.out.println("Gender " + gender.getText());
			System.out.println("Year of Birth " + birthYear.getText());
			System.out.println("Father " + father.getText());
			System.out.println("Mother " + mother.getText());
			
			String newName = name.getText();
			char newGender = gender.getText().charAt(0);
			int newYear = Integer.parseInt(birthYear.getText());
			String newFather = father.getText();
			String newMother = mother.getText();
			
			Person newPerson = new Person(newName, newGender, newYear, newFather, newMother, null, null);
			fileReader.add(newPerson);
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
