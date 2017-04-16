package models;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPersonInterface implements ActionListener{
	private FileReader filereader = new FileReader();
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



	public AddPersonInterface(JPanel panel){
		AddPanel = makePanel(panel);
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
		
		panel.add(Panel1);
		panel.add(Panel2);
		panel.add(Panel3);
		panel.add(Panel4);
		panel.add(Panel5);
		panel.add(okay);

		return panel;
		
	}
	
	public void addField(JPanel panel, JTextField text){
		//text.addActionListener(this); //method to handle the action is in this class
		panel.add(text);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
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
		filereader.add(newPerson);

	}
	
	public JPanel getAddPanel() {
		return AddPanel;
	}

	public void setAddPanel(JPanel addPanel) {
		AddPanel = addPanel;
	}
}
