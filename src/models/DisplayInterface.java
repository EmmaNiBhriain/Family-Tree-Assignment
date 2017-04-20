package models;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DisplayInterface implements ActionListener{
	private JPanel topPanel = new JPanel();
	private JPanel middlePanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JLabel instructions;
	private JButton confirm;
	private JTextField name;
	private FileReader fileReader;
	private JPanel viewPanel;
	private Person viewPerson = new Person(null, ' ', 0, null, null, null, null);
	private JTextArea textArea = new JTextArea();
	private JFrame frame;
	private JFrame frame1;
	private JFrame frame2;
	private String title = "Family Tree";
	private JButton returnButton = new JButton("Return to Main Menu");
	private JButton backButton = new JButton("Back");
	
	private JPanel namePanel;
	private JPanel genderPanel;
	private JPanel agePanel;
	private JPanel deathPanel;
	private JPanel motherNamePanel;
	private JPanel fatherNamePanel;
	private JPanel parentPanel = new JPanel(new GridLayout(1,1));
	
	private ActionEvent confirmed;
	
	private boolean showFather = false;
	private boolean showMother = false;
	
	
	public DisplayInterface(FileReader reader){
		fileReader = reader;
		makeFrame();
	}
		
	public void makeFrame(){
			frame = new JFrame(title);

			JPanel contentPane = (JPanel)frame.getContentPane();
			contentPane.setPreferredSize(new Dimension(500, 400));
			contentPane.setLayout(new BorderLayout(10,0));
			//TODO set border if you like
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
				
		instructions = new JLabel("Enter Name of Person");
		name = new JTextField(10);
		confirm = new JButton("View");
		confirm.addActionListener(this);
		returnButton.addActionListener(this);
		
		topPanel.add(instructions);
		middlePanel.add(name);
		bottomPanel.add(returnButton);
		bottomPanel.add(confirm);
		
		return panel;
	}
	
	/**
	 * When a name has been selected and the button to "View" their ancestry has been pressed, 
	 * open the view to show them their heritage 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getActionCommand().equals("View")){
			//makeNextFrame(frame1);
			namePanel  = new JPanel(new GridLayout(1,1));
			agePanel = new JPanel(new GridLayout(1,1));
			deathPanel = new JPanel(new GridLayout(1,1));
			genderPanel = new JPanel(new GridLayout(1,1));
			motherNamePanel = new JPanel(new GridLayout(1,1));
			fatherNamePanel = new JPanel(new GridLayout(1,1));
			//frame1 = new JFrame();
			//makeNextFrame(frame1);
			//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			String findName = name.getText();
			
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			
			frame1 = new JFrame(title);
			if(fileReader.getPeopleMap().containsKey(findName)){
				viewPerson = fileReader.getPeopleMap().get(findName);
				JLabel infoName = new JLabel("Name: \t" + viewPerson.getName());
				JLabel infoGender = new JLabel("Gender: \t" + Character.toString(viewPerson.getGender()));
				JLabel infoYear = new JLabel("Year of Birth: \t" + Integer.toString(viewPerson.getBirthYear()));
				JLabel infoDeath;
				if(viewPerson.getDeathYear()==0){
					infoDeath = new JLabel("Year of Death: \tunknown");
				}
				else 
					infoDeath = new JLabel("Year of Death: \t" + Integer.toString(viewPerson.getDeathYear()));
				JLabel infoMom = new JLabel("Mother: \t:" + viewPerson.getMother());
				JLabel infoDad = new JLabel("Father: \t" + viewPerson.getFather());
				
				JButton viewParents = new JButton("View Parents' Details");
				viewParents.addActionListener(this);
						
				textArea = TreePrinter.print(viewPerson);
				namePanel.add(infoName);
				genderPanel.add(infoGender);
				agePanel.add(infoYear);
				deathPanel.add(infoDeath);
				motherNamePanel.add(infoMom);
				fatherNamePanel.add(infoDad);
				
				JPanel parentButtonP = new JPanel();
				parentButtonP.add(viewParents);
				parentPanel.add(parentButtonP, BorderLayout.WEST);
				
				JPanel contentPane = (JPanel)frame1.getContentPane();
				contentPane.setPreferredSize(new Dimension(700, 500));
				//JPanel displayPane = makeNextPanel(contentPane);
				contentPane = makeNextPanel(contentPane);
				frame1.pack();
				frame1.setVisible(true);
			}
			else{
				JOptionPane.showMessageDialog(null, "This person is not stored in the system", "Error", JOptionPane.ERROR_MESSAGE); 
				DisplayInterface display = new DisplayInterface(fileReader);
			}
			
		}
		else if(event.getActionCommand().equals("Return to Main Menu")){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			UserInterface user = new UserInterface(fileReader);
		}
		
		else if(event.getActionCommand().equals("Back")){
			frame1.dispatchEvent(new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING));
			//frame2.dispatchEvent(new WindowEvent(frame2, WindowEvent.WINDOW_CLOSING));

			textArea.setText(null);
			DisplayInterface view = new DisplayInterface(fileReader);
		}
		
		else if(event.getActionCommand().equals("View Parents' Details")){
			if(fileReader.getPeopleMap().containsKey(viewPerson.getFather())){
				showFather=true;
				//makeNextFrame();
			}
			
			if(fileReader.getPeopleMap().containsKey(viewPerson.getMother())){
				showMother = true;
				//makeNextFrame();
			}
			
			else if((!fileReader.getPeopleMap().containsKey(viewPerson.getFather()))&&(!fileReader.getPeopleMap().containsKey(viewPerson.getMother()))){
				JOptionPane.showMessageDialog(null, "This person's parents are not stored in the system", "Error", JOptionPane.ERROR_MESSAGE); 
			}
			frame1.dispatchEvent(new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING));
			//frame1.dispose();
			//textArea.setText(null);
			frame2 = new JFrame();
			makeNextFrame(frame2);
		}
		
		
	}
	
	public void makeNextFrame(JFrame newFrame){
		namePanel  = new JPanel(new GridLayout(1,1));
		agePanel = new JPanel(new GridLayout(1,1));
		deathPanel = new JPanel(new GridLayout(1,1));
		genderPanel = new JPanel(new GridLayout(1,1));
		motherNamePanel = new JPanel(new GridLayout(1,1));
		fatherNamePanel = new JPanel(new GridLayout(1,1));
		
		
		
		String findName = name.getText();
		
		//frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		
		newFrame = new JFrame(title);
		if(fileReader.getPeopleMap().containsKey(findName)){
			viewPerson = fileReader.getPeopleMap().get(findName);
			JLabel infoName = new JLabel("Name: \t" + viewPerson.getName());
			JLabel infoGender = new JLabel("Gender: \t" + Character.toString(viewPerson.getGender()));
			JLabel infoYear = new JLabel("Year of Birth: \t" + Integer.toString(viewPerson.getBirthYear()));
			JLabel infoDeath;
			if(viewPerson.getDeathYear()==0){
				infoDeath = new JLabel("Year of Death: \tunknown");
			}
			else 
				infoDeath = new JLabel("Year of Death: \t" + Integer.toString(viewPerson.getDeathYear()));
			JLabel infoMom = new JLabel("Mother: \t:" + viewPerson.getMother());
			JLabel infoDad = new JLabel("Father: \t" + viewPerson.getFather());
					
			namePanel.add(infoName);
			genderPanel.add(infoGender);
			agePanel.add(infoYear);
			deathPanel.add(infoDeath);
			motherNamePanel.add(infoMom);
			fatherNamePanel.add(infoDad);
			
			JPanel contentPane = (JPanel)newFrame.getContentPane();
			contentPane.setPreferredSize(new Dimension(700, 500));
			//JPanel displayPane = makeNextPanel(contentPane);
			contentPane = makeNextPanel(contentPane);
			newFrame.pack();
			newFrame.setVisible(true);
		}
		else{
			JOptionPane.showMessageDialog(null, "This person is not stored in the system", "Error", JOptionPane.ERROR_MESSAGE); 
			DisplayInterface display = new DisplayInterface(fileReader);
		}
	}
	
	public JPanel makeNextPanel(JPanel panel){
		panel.setLayout(new BorderLayout()); 
		JPanel middle = new JPanel(new GridLayout(0,3));
		

		JPanel bottom = new JPanel();
		
		JPanel top = new JPanel(new GridLayout());
		panel.add(top, BorderLayout.NORTH);
		panel.add(middle, BorderLayout.CENTER);
		panel.add(bottom, BorderLayout.SOUTH);
		
		instructions = new JLabel("Your Ancestors: ");
		backButton.addActionListener(this);
		
		//if((showFather == false)&&(showMother == false)){
			top.add(instructions);
			
			JPanel personPanel = new JPanel(new GridLayout(7,1));
			personPanel.add(namePanel);
			personPanel.add(genderPanel);
			personPanel.add(agePanel);
			personPanel.add(deathPanel);
			personPanel.add(motherNamePanel);
			personPanel.add(fatherNamePanel);
			
			if((showFather == false)&&(showMother == false)){
				personPanel.add(parentPanel);
			}
				
			middle.add(personPanel);
			
		//}
	
		
		if(showFather == true){
			JPanel dadPanel = new JPanel(new GridLayout(7,1));
			Person father = viewPerson.getFatherObject();
			JLabel dadName = new JLabel("Father's Name: \t" + father.getName());
			JLabel dadGender = new JLabel("Gender: \t" + Character.toString(father.getGender()));
			JLabel dadYear = new JLabel("Year of Birth: \t" + Integer.toString(father.getBirthYear()));
			JLabel dadDeath; 
			if(father.getDeathYear()==0){
				dadDeath = new JLabel("Year of Death: \tunknown");
			}
			else
				dadDeath = new JLabel("Year of Death: \t" + Integer.toString(father.getDeathYear()));
			JLabel dadFather = new JLabel("Father: \t" + father.getFather());
			JLabel dadMother = new JLabel("Mother: \t" + father.getMother());
			
			dadPanel.add(dadName);
			dadPanel.add(dadGender);
			dadPanel.add(dadYear);
			dadPanel.add(dadDeath);
			dadPanel.add(dadFather);
			dadPanel.add(dadMother);
			middle.add(dadPanel);
		}
		
		if(showMother == true){
			JPanel momPanel = new JPanel(new GridLayout(7,1));
			Person mother = viewPerson.getMotherObject();
			JLabel momName = new JLabel("Mother's Name: \t" + mother.getName());
			JLabel momGender = new JLabel("Gender: \t" + Character.toString(mother.getGender()));
			JLabel momYear = new JLabel("Year of Birth: \t" + Integer.toString(mother.getBirthYear()));
			
			JLabel momDeath;
			if(mother.getDeathYear() == 0){
				momDeath = new JLabel("Year of Death: \tunknown");
			}
			else 
				momDeath = new JLabel("Year of Death: \t" + Integer.toString(mother.getDeathYear()));
			JLabel momFather = new JLabel("Father: \t" + mother.getFather());
			JLabel momMother = new JLabel("Mother: \t" + mother.getMother());
			
			momPanel.add(momName);
			momPanel.add(momGender);
			momPanel.add(momYear);
			momPanel.add(momDeath);
			momPanel.add(momFather);
			momPanel.add(momMother);

			middle.add(momPanel);
		}
		if((showFather == false)&&(showMother == false)){
			middle.add(textArea);
		}
		
		bottom.add(backButton);
		//panel.add(comp)
		
		
		return panel;		
	}

	public JPanel getViewPanel() {
		return viewPanel;
	}

	public void setViewPanel(JPanel viewPanel) {
		this.viewPanel = viewPanel;
	}
	
	

}
