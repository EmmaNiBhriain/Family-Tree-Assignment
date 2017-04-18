package models;
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
	private String menuButton1 = "Add";
	private String menuButton2 = "Remove";
	private String menuButton3 = "Modify";
	private String menuButton4 = "View";
	private JLabel display;


	public UserInterface(){
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
		
		addMenuItem(buttonPanel, buttons, menuButton1);
		addMenuItem(buttonPanel, buttons, menuButton2);
		addMenuItem(buttonPanel, buttons, menuButton3);
		addMenuItem(buttonPanel, buttons, menuButton4);
				
		contentPane.add(buttonPanel); //add in the centre

		
		return contentPane;

	}
	
	public void addMenuItem(JPanel panel, ButtonGroup bgroup, String menuItem){
		JRadioButtonMenuItem button = new JRadioButtonMenuItem(menuItem);
		//button.setSize(new Dimension(80,80));
		button.addActionListener(this); //method to handle the action is in this class
		bgroup.add(button);
		panel.add(button);
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
	
	public void displayAddMenu(){
		
		AddPersonInterface addui = new AddPersonInterface();
	}
	
	public void displayRemoveMenu(){
		RemovalInterface removalMenu = new RemovalInterface();
		
	}
	
	public void displayModifyMenu(){

		ModifierInterface modify = new ModifierInterface();
	}
	
	public void displayViewMenu(){
		DisplayInterface dis = new DisplayInterface();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if(command.equals("Add")){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			System.out.println("add");
			displayAddMenu();
		}
		else if(command.equals("Remove")){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			System.out.println("Remove");
			displayRemoveMenu();
		}
		else if(command.equals("Modify")){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			System.out.println("Modify");
			displayModifyMenu();
		}
		else if(command.equals("View")){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			System.out.println("View");
			displayViewMenu();
		}	
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
