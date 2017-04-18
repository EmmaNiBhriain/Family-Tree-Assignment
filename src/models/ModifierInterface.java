package models;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ModifierInterface implements ActionListener{
	private JPanel modifyPanel;
	private JPanel topPanel = new JPanel();
	private JPanel middlePanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JLabel instructions;
	private JButton confirm;
	private JTextField name;
	private FileReader fileReader;
	
	private JPanel Panel1= new JPanel(new GridLayout(1,2));
	private JPanel Panel2 = new JPanel(new GridLayout(1,2));
	private JPanel Panel3 = new JPanel(new GridLayout(1,2));
	private JPanel Panel4 = new JPanel(new GridLayout(1,2));
	private JPanel Panel5 = new JPanel(new GridLayout(1,2));
	private JLabel nameL = new JLabel("Name : ");
	private JTextField newName = new JTextField (10);
	private JLabel genderL = new JLabel("Gender : ");
	private JTextField gender = new JTextField (10);
	private JLabel yearL = new JLabel("Year of Birth : ");
	private JTextField birthYear = new JTextField (10);
	private JLabel fatherL = new JLabel("Father : ");
	private JTextField father = new JTextField (10);
	private JLabel motherL = new JLabel("Mother : ");
	private JTextField mother = new JTextField (10);
	private JFrame frame;
	private String title = "Family Tree";

	
	private Person modifyPerson = new Person(null, ' ', 0, null, null, null, null);


	
	
	public ModifierInterface(JPanel panel){
		fileReader = new FileReader();
		modifyPanel = makePanel(panel);
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
		
		topPanel.add(instructions);
		middlePanel.add(name);
		bottomPanel.add(confirm);
		return panel;
		
	}

	public JPanel getModifyPanel() {
		return modifyPanel;
	}

	public void setModifyPanel(JPanel modifyPanel) {
		this.modifyPanel = modifyPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("OK")){
			
			String findName = name.getText();
			modifyPerson = fileReader.getPeopleMap().get(findName);
						
			frame = new JFrame(title);

			JPanel contentPane = (JPanel)frame.getContentPane();
			contentPane.setLayout(new BorderLayout(10,10));
			//modifyPanel = makeNextPanel();

			JPanel displayPane = makeNextPanel(contentPane);
			contentPane = displayPane;
			frame.pack();
			frame.setVisible(true);
			
			//modifyPanel = makeNextPanel();
		}
		
		else if(e.getActionCommand().equals("Update")){
			String nameChange;
			char newGender;
			int newYear;
			String newFather;
			String newMother;
			
			if(newName.getText().equals("")){
				nameChange = null;
			}
			else
				nameChange = newName.getText();
			
			
			if(gender.getText().equals("")){
				newGender = ' ';
			}
			else
				 newGender = gender.getText().charAt(0);
			
			
			if(birthYear.getText().equals("")){
				newYear = 0;
			}
			else
				newYear = Integer.parseInt(birthYear.getText());
			
			if(father.getText().equals("")){
				newFather = null;
			}
			else
				newFather = father.getText();
			
			if(mother.getText().equals("")){
				newMother = null;
			}
			else
				newMother = mother.getText();
			
			
			System.out.println("Inserted text fields : " + nameChange + " " + newGender+ " " + newYear + " " + newFather + " " + newMother);
			modifyPerson = fileReader.modify(modifyPerson, nameChange, newGender, newYear, newFather, newMother);
			if(!fileReader.getPeopleMap().containsKey(modifyPerson))
				System.out.println("Update complete," + modifyPerson.getName());
			else
				System.out.println("Error updating name");
		}
		
	}
	
	public JPanel makeNextPanel(JPanel panel){
		//JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2)); 
		//contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));
		
		Panel1.add(nameL);
		addField(Panel1, newName);

		Panel2.add(genderL);
		addField(Panel2, gender);

		Panel3.add(yearL);
		addField(Panel3, birthYear);

		Panel4.add(fatherL);
		addField(Panel4, father);
		
		Panel5.add(motherL);
		addField(Panel5, mother);

		
		
		JButton okay = new JButton("Update");		
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

}
