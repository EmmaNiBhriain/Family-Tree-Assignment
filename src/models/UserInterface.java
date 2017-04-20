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

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class UserInterface implements ActionListener{
	//private MenuInterface menu = new MenuInterface();
	private String title = "Family Tree";
	private JFrame frame; 
	private JRadioButtonMenuItem addButton = new JRadioButtonMenuItem("Add");
	private JRadioButtonMenuItem removeButton = new JRadioButtonMenuItem("Remove");
	private JRadioButtonMenuItem modifyButton = new JRadioButtonMenuItem("Modify");
	private JRadioButtonMenuItem viewButton = new JRadioButtonMenuItem("View");
	private JRadioButtonMenuItem saveButton = new JRadioButtonMenuItem("Save Changes to File");
	private JRadioButtonMenuItem loadButton = new JRadioButtonMenuItem("Load saved File");
	private JButton confirm = new JButton("OK");
	private JLabel display;
	private FileReader fileReader;

	/**
	 * Default Constructor, uses the data from the original database file and displays a menu with a list of options
	 */
	public UserInterface(){
		fileReader = new FileReader();
		makeFrame();
		displayMainMenu();
	}
	
	/**
	 * Constructor for displaying the user interface when returning from another interface
	 * A new file reader is not required
	 * @param reader
	 */
	public UserInterface(FileReader reader){
		fileReader = reader;
		makeFrame();
		displayMainMenu();
	}
	
	public void makeFrame(){
		frame = new JFrame(title);
		//JPanel contentPane = (JPanel)frame.getContentPane();
		//contentPane.setLayout(new BorderLayout(10,0)); 
		//contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10))
	}
	
	
	public JPanel makePanel(JPanel contentPane){
		
		contentPane.setLayout(new BorderLayout()); 
		//contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));
		
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1)); //1 column, as many rows as necessary
		ButtonGroup buttons = new ButtonGroup();
		
		display = new JLabel("Please Select an operation");
		buttonPanel.add(display);
		
		addMenuItem(buttonPanel, buttons, addButton);
		addMenuItem(buttonPanel, buttons, removeButton);
		addMenuItem(buttonPanel, buttons, modifyButton);
		addMenuItem(buttonPanel, buttons, viewButton);
		addMenuItem(buttonPanel, buttons, saveButton);
		addMenuItem(buttonPanel, buttons, loadButton);
		
		confirm.addActionListener(this);
		buttonPanel.add(confirm);
		
		contentPane.add(buttonPanel); //add in the centre

		
		return contentPane;

	}
	
	/**
	 * Add a JRadioButton to a button group to ensure only one can be selected at a time
	 * Also add the button to the panel
	 * @param panel
	 * @param bgroup
	 * @param menuItem
	 */
	public void addMenuItem(JPanel panel, ButtonGroup bgroup, JRadioButtonMenuItem menuItem){
		//JRadioButtonMenuItem button = new JRadioButtonMenuItem(menuItem);
		//menuItem.setSize(new Dimension(80,80));
		//button.addActionListener(this); //method to handle the action is in this class
		bgroup.add(menuItem);
		panel.add(menuItem);
	}
	
	public void displayMainMenu(){
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setPreferredSize(new Dimension(500, 400));
		contentPane.setLayout(new BorderLayout(10,10)); 
		contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));
		
		JPanel displayPane = makePanel(contentPane);
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
	}
	
	/**
	 * Display the menu that will allow users to add a person to the system
	 */
	public void displayAddMenu(){
		
		AddPersonInterface addui = new AddPersonInterface(fileReader);
	}
	
	/**
	 * Display the menu that will allow users to remove a person from the system
	 */
	public void displayRemoveMenu(){
		RemovalInterface removalMenu = new RemovalInterface(fileReader);
		
	}
	
	/**
	 * Display the menu that will allow users to modify a person on the system
	 */
	public void displayModifyMenu(){

		ModifierInterface modify = new ModifierInterface(fileReader);
	}
	
	/**
	 * Display the user interface that will allow a user to view a person's family
	 */
	public void displayViewMenu(){
		DisplayInterface dis = new DisplayInterface(fileReader);
	}

	/**
	 * Depending on the option selected from the menu of options, open the relevant user interface
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if(addButton.isSelected()){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			System.out.println("add");
			displayAddMenu();
		}
		else if(removeButton.isSelected()){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			System.out.println("Remove");
			displayRemoveMenu();
		}
		else if(modifyButton.isSelected()){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			System.out.println("Modify");
			displayModifyMenu();
		}
		else if(viewButton.isSelected()){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			System.out.println("View");
			displayViewMenu();
		}	
		else if(saveButton.isSelected()){
			Serializer writeData = new Serializer(fileReader);
			writeData.writeToFile();
			writeData.close();
		}
		else if(loadButton.isSelected()){
			Serializer readData = new Serializer();
			readData.readFromFile();
			fileReader.setParentMap(readData.getInParents());
			fileReader.setPeopleMap(readData.getInPeople());
			readData.closeInputs();
			
		}
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
