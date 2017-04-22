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
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import models.FileReader;
import models.Person;
import models.TreePrinter;

public class DisplayInterface implements ActionListener {
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
	JButton viewParents = new JButton("View Parents' Details");

	private JPanel namePanel;
	private JPanel genderPanel;
	private JPanel agePanel;
	private JPanel deathPanel;
	private JPanel motherNamePanel;
	private JPanel fatherNamePanel;
	private JPanel parentPanel = new JPanel(new GridLayout(0, 1));

	private boolean showFather = false;
	private boolean showMother = false;

	/**
	 * Constructor for the interface that displays a person's relations and
	 * family tree
	 * 
	 * @param reader
	 */
	public DisplayInterface(FileReader reader) {
		fileReader = reader;
		makeFrame();
	}

	/**
	 * Make the frame for the initial view
	 */
	public void makeFrame() {
		frame = new JFrame(title);

		JPanel contentPane = (JPanel) frame.getContentPane();
		contentPane.setPreferredSize(new Dimension(500, 400));
		contentPane.setLayout(new BorderLayout(10, 0));
		JPanel displayPane = makePanel(contentPane);
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Make the panel for the initial view
	 * This includes a text box where the user can enter the name of the person they wish to view
	 * @param panel
	 * @return
	 */
	public JPanel makePanel(JPanel panel) {
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
	 * Creates the frame and pane that will be used to display the family details of the person input in the previous view
	 * @param findName
	 */
	public void createView(String findName){
		namePanel = new JPanel(new GridLayout(1, 1));
		agePanel = new JPanel(new GridLayout(1, 1));
		deathPanel = new JPanel(new GridLayout(1, 1));
		genderPanel = new JPanel(new GridLayout(1, 1));
		motherNamePanel = new JPanel(new GridLayout(1, 1));
		fatherNamePanel = new JPanel(new GridLayout(1, 1));
		//String findName = name.getText();

		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

		frame1 = new JFrame(title);
		if (fileReader.getPeopleMap().containsKey(findName)) {
			viewPerson = fileReader.getPeopleMap().get(findName);
			JLabel infoName = new JLabel("Name: \t" + viewPerson.getName());
			JLabel infoGender = new JLabel("Gender: \t" + Character.toString(viewPerson.getGender()));
			JLabel infoYear = new JLabel("Year of Birth: \t" + Integer.toString(viewPerson.getBirthYear()));
			JLabel infoDeath;
			if (viewPerson.getDeathYear() == 0) {
				infoDeath = new JLabel("Year of Death: \tunknown");
			} else
				infoDeath = new JLabel("Year of Death: \t" + Integer.toString(viewPerson.getDeathYear()));
			JLabel infoMom = new JLabel("Mother: \t:" + viewPerson.getMother());
			JLabel infoDad = new JLabel("Father: \t" + viewPerson.getFather());

			// JButton viewParents = new JButton("View Parents' Details");
			viewParents.addActionListener(this);

			textArea = TreePrinter.print(viewPerson);
			namePanel.add(infoName);
			genderPanel.add(infoGender);
			agePanel.add(infoYear);
			deathPanel.add(infoDeath);
			motherNamePanel.add(infoMom);
			fatherNamePanel.add(infoDad);

			JPanel allButtons = new JPanel(new GridLayout(8, 1));
			JButton viewSiblings = new JButton("View Person's Siblings");
			viewSiblings.addActionListener(this);

			JButton viewCousins = new JButton("View Person's Cousins");
			viewCousins.addActionListener(this);

			JButton viewParentSiblings = new JButton("View Aunts and Uncles");
			viewParentSiblings.addActionListener(this);

			JButton viewGrandParentSiblings = new JButton("View Great-Aunts and Uncles");
			viewGrandParentSiblings.addActionListener(this);

			allButtons.add(viewSiblings);
			allButtons.add(viewCousins);
			allButtons.add(viewParentSiblings);
			allButtons.add(viewGrandParentSiblings);
			allButtons.add(viewParents);

			/*
			 * JPanel parentButtonP = new JPanel();
			 * parentButtonP.add(viewParents);
			 */
			parentPanel.add(allButtons, BorderLayout.EAST);

			JPanel contentPane = (JPanel) frame1.getContentPane();
			contentPane.setPreferredSize(new Dimension(700, 500));
			// JPanel displayPane = makeNextPanel(contentPane);
			contentPane = makeNextPanel(contentPane);
			frame1.pack();
			frame1.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "This person is not stored in the system", "Error",
					JOptionPane.ERROR_MESSAGE);
			DisplayInterface display = new DisplayInterface(fileReader);
		}
	}

	/**
	 * When a name has been selected and the button to "View" their ancestry has
	 * been pressed, open the view to show them their heritage If a person
	 * wishes to view their parents' details, a window appears that shows each
	 * parent's details, if known
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals("View")) {
			 //makeNextFrame(frame1);
			createView(name.getText());

		} else if (event.getActionCommand().equals("Return to Main Menu")) {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			UserInterface user = new UserInterface(fileReader);
		}

		else if (event.getActionCommand().equals("Back")) {
			frame1.dispatchEvent(new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING));
			textArea.setText(null);
			DisplayInterface view = new DisplayInterface(fileReader);
		}
		
		else if (event.getActionCommand().equals("View Parents' Details")) {
			if (fileReader.getPeopleMap().containsKey(viewPerson.getFather())) {
				showFather = true;
				// makeNextFrame();
			}

			if (fileReader.getPeopleMap().containsKey(viewPerson.getMother())) {
				showMother = true;
				// makeNextFrame();
			}

			else if ((!fileReader.getPeopleMap().containsKey(viewPerson.getFather()))
					&& (!fileReader.getPeopleMap().containsKey(viewPerson.getMother()))) {
				JOptionPane.showMessageDialog(null, "This person's parents are not stored in the system", "Error",JOptionPane.ERROR_MESSAGE);
			}
			frame1.dispatchEvent(new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING));
			makeNextFrame(frame2);
		} 
		
		else if (event.getActionCommand().equals("View Person's Siblings")) {
			System.out.println("siblings");
			String[] displaySiblings = fileReader.getSiblings(viewPerson);
			JOptionPane.showMessageDialog(null, displaySiblings, "Siblings: ", JOptionPane.PLAIN_MESSAGE);

		} 
		else if (event.getActionCommand().equals("View Person's Cousins")) {
			System.out.println("cousins");
			showCousins(viewPerson);
		} 
		
		
		else if (event.getActionCommand().equals("View Aunts and Uncles")) {
			System.out.println("aunts and uncles");
			// aunts and uncles = siblings of both parents
			showAuntsAndUncles(viewPerson);
		} 
		
		
		else if (event.getActionCommand().equals("View Great-Aunts and Uncles")) {
			System.out.println("great aunts and uncles");

			// great-aunts and uncles = siblings of all four grandparents
			showGreatAuntsAndUncles(viewPerson);

		}

	}
	
	
	/**
	 * If the User chooses to view a list of aunts and uncles, if the mother is known, return her siblings
	 * Likewise, if the father is known, return his siblings
	 * If either parent is unknown, return a JOption Pane saying that the information is unavailable for that side of the family
	 */
	public void showAuntsAndUncles(Person viewPerson){
		if(!viewPerson.getMother().equals("?")){
			Person mother = viewPerson.getMotherObject();
			String[]motherSiblings = fileReader.getSiblings(mother);
			JOptionPane.showMessageDialog(null, motherSiblings, "Mother's Side: " , JOptionPane.PLAIN_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(null, "No Aunts or Uncles on the mother's side", "Error" , JOptionPane.PLAIN_MESSAGE);

		}
		
		if(!viewPerson.getFather().equals("?")){
			Person father = viewPerson.getFatherObject();
			String[]fatherSiblings = fileReader.getSiblings(father);
			JOptionPane.showMessageDialog(null, fatherSiblings, "Father's Side: " , JOptionPane.PLAIN_MESSAGE);
		}
		else{
			JOptionPane.showMessageDialog(null, "No Aunts or Uncles on the father's side", "Error" , JOptionPane.PLAIN_MESSAGE);

		}
	}
	
	/**
	 * Get the siblings of the parents of the person and show a list of their children
	 * @param viewPerson
	 */
	public void showCousins(Person viewPerson){
		ArrayList<String> parentsSiblings = new ArrayList<String>(); 

		if(!viewPerson.getMother().equals("?")){
			Person mother = viewPerson.getMotherObject();
			String[]motherSiblings = fileReader.getSiblings(mother);
			parentsSiblings.addAll(Arrays.asList(motherSiblings));
		}
		if(!viewPerson.getFather().equals("?")){
			Person father = viewPerson.getFatherObject();
			String[]fatherSiblings = fileReader.getSiblings(father);
			parentsSiblings.addAll(Arrays.asList(fatherSiblings));
		}
		if(parentsSiblings.size()>0){
			ArrayList<String>cousins = new ArrayList<String>();
			for(String parent : parentsSiblings){
				if(fileReader.getParentMap().containsKey(parent)){
					ArrayList<String>temoCousinList = fileReader.getParentMap().get(parent);
					cousins.addAll(temoCousinList);
				}
			}
			if(cousins.size()>0){
				String[] displayCousins = cousins.toArray(new String[cousins.size()]);
				JOptionPane.showMessageDialog(null, displayCousins, "Cousins: " , JOptionPane.PLAIN_MESSAGE);
			}		
			else {
				JOptionPane.showMessageDialog(null, "No cousins to display", "Cousins: " , JOptionPane.PLAIN_MESSAGE);
				System.out.println("no cousins");
			}

		}
	
		else {
			JOptionPane.showMessageDialog(null, "No cousins to display", "Cousins: " , JOptionPane.PLAIN_MESSAGE);
			System.out.println("no cousins");
		}
	}
	
	/**
	 * To get the great aunts and uncles, get the siblings of each of the four grandparents if known
	 */
	public void showGreatAuntsAndUncles(Person viewPerson){
		if (!viewPerson.getMother().equals("?")) {
			if(!viewPerson.getMotherObject().getMother().equals("?")){
				Person grandMother = viewPerson.getMotherObject().getMotherObject();
				String[] grandmotherSiblings = fileReader.getSiblings(grandMother);
				JOptionPane.showMessageDialog(null, grandmotherSiblings, "Mother's Mother's Side: ", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "GrandMother 1 = no siblings", "Error", JOptionPane.PLAIN_MESSAGE);
			}
			
			if(!viewPerson.getMotherObject().getFather().equals("?")){
				Person grandFather = viewPerson.getMotherObject().getFatherObject();
				String[] grandfatherSiblings = fileReader.getSiblings(grandFather);
				JOptionPane.showMessageDialog(null, grandfatherSiblings, "Mother's Father's Side: ", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "GrandFather 1 = no siblings", "Error", JOptionPane.PLAIN_MESSAGE);
			}
			
		}
		else{
			JOptionPane.showMessageDialog(null, "No information available from mother's side of the family", "Error", JOptionPane.PLAIN_MESSAGE);

		}
		if (!viewPerson.getFather().equals("?")) {
			if(!viewPerson.getFatherObject().getMother().equals("?")){
				Person grandMother = viewPerson.getFatherObject().getMotherObject();
				String[] grandmotherSiblings = fileReader.getSiblings(grandMother);
				JOptionPane.showMessageDialog(null, grandmotherSiblings, "Father's Mother's Side: ", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "GrandMother 2 = no siblings", "Error", JOptionPane.PLAIN_MESSAGE);
			}
			if(!viewPerson.getFatherObject().getFather().equals("?")){
				Person grandFather = viewPerson.getFatherObject().getFatherObject();
				String[] grandfatherSiblings = fileReader.getSiblings(grandFather);
				JOptionPane.showMessageDialog(null, grandfatherSiblings, "Father's Father's Side: ", JOptionPane.PLAIN_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(null, "GrandFather 2 = no siblings", "Error", JOptionPane.PLAIN_MESSAGE);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, "No information available from father's side of the family", "Error", JOptionPane.PLAIN_MESSAGE);

		}
	}


	/**
	 * Make the next Frame, for displaying the parent's details
	 * @param newFrame
	 */
	public void makeNextFrame(JFrame newFrame) {
		namePanel = new JPanel(new GridLayout(1, 1));
		agePanel = new JPanel(new GridLayout(1, 1));
		deathPanel = new JPanel(new GridLayout(1, 1));
		genderPanel = new JPanel(new GridLayout(1, 1));
		motherNamePanel = new JPanel(new GridLayout(1, 1));
		fatherNamePanel = new JPanel(new GridLayout(1, 1));

		String findName = name.getText();

		// frame.dispatchEvent(new WindowEvent(frame,
		// WindowEvent.WINDOW_CLOSING));

		newFrame = new JFrame(title);
		if (fileReader.getPeopleMap().containsKey(findName)) {
			viewPerson = fileReader.getPeopleMap().get(findName);
			JLabel infoName = new JLabel("Name: \t" + viewPerson.getName());
			JLabel infoGender = new JLabel("Gender: \t" + Character.toString(viewPerson.getGender()));
			JLabel infoYear = new JLabel("Year of Birth: \t" + Integer.toString(viewPerson.getBirthYear()));
			JLabel infoDeath;
			if (viewPerson.getDeathYear() == 0) {
				infoDeath = new JLabel("Year of Death: \tunknown");
			} else
				infoDeath = new JLabel("Year of Death: \t" + Integer.toString(viewPerson.getDeathYear()));
			JLabel infoMom = new JLabel("Mother: \t:" + viewPerson.getMother());
			JLabel infoDad = new JLabel("Father: \t" + viewPerson.getFather());

			namePanel.add(infoName);
			genderPanel.add(infoGender);
			agePanel.add(infoYear);
			deathPanel.add(infoDeath);
			motherNamePanel.add(infoMom);
			fatherNamePanel.add(infoDad);

			JPanel contentPane = (JPanel) newFrame.getContentPane();
			contentPane.setPreferredSize(new Dimension(700, 500));
			// JPanel displayPane = makeNextPanel(contentPane);
			contentPane = makeNextPanel(contentPane);
			newFrame.pack();
			newFrame.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "This person is not stored in the system", "Error",
					JOptionPane.ERROR_MESSAGE);
			DisplayInterface display = new DisplayInterface(fileReader);
		}
	}

	
	
	/**
	 * Make the next panel, for either displaying the parent's details or the person's family connections
	 * @param panel
	 * @return
	 */
	public JPanel makeNextPanel(JPanel panel) {
		panel.setLayout(new BorderLayout());
		JPanel middle = new JPanel(new GridLayout(0, 3));

		JPanel bottom = new JPanel();

		JPanel top = new JPanel(new GridLayout());
		panel.add(top, BorderLayout.NORTH);
		panel.add(middle, BorderLayout.CENTER);
		panel.add(bottom, BorderLayout.SOUTH);

		instructions = new JLabel("Your Ancestors: ");
		backButton.addActionListener(this);


		// if((showFather == false)&&(showMother == false)){
		top.add(instructions);

		JPanel personPanel = new JPanel(new GridLayout(6, 1));
		personPanel.add(namePanel);
		personPanel.add(genderPanel);
		personPanel.add(agePanel);
		personPanel.add(deathPanel);
		personPanel.add(motherNamePanel);
		personPanel.add(fatherNamePanel);
		middle.add(personPanel);

		// }

		if (showFather == true) {
			JPanel dadPanel = new JPanel(new GridLayout(7, 1));
			Person father = viewPerson.getFatherObject();
			JLabel dadName = new JLabel("Father's Name: \t" + father.getName());
			JLabel dadGender = new JLabel("Gender: \t" + Character.toString(father.getGender()));
			JLabel dadYear = new JLabel("Year of Birth: \t" + Integer.toString(father.getBirthYear()));
			JLabel dadDeath;
			if (father.getDeathYear() == 0) {
				dadDeath = new JLabel("Year of Death: \tunknown");
			} else
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

		if (showMother == true) {
			JPanel momPanel = new JPanel(new GridLayout(7, 1));
			Person mother = viewPerson.getMotherObject();
			JLabel momName = new JLabel("Mother's Name: \t" + mother.getName());
			JLabel momGender = new JLabel("Gender: \t" + Character.toString(mother.getGender()));
			JLabel momYear = new JLabel("Year of Birth: \t" + Integer.toString(mother.getBirthYear()));

			JLabel momDeath;
			if (mother.getDeathYear() == 0) {
				momDeath = new JLabel("Year of Death: \tunknown");
			} else
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
		if ((showFather == false) && (showMother == false)) {
			middle.add(textArea);
			middle.add(parentPanel);
		}
		bottom.add(backButton);
		return panel;
	}

	public JPanel getViewPanel() {
		return viewPanel;
	}

	public void setViewPanel(JPanel viewPanel) {
		this.viewPanel = viewPanel;
	}

}
