package models;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;

public class MenuInterface implements ActionListener{

	private JFrame menuJFrame; 
	private String menuButton1 = "Add";
	private String menuButton2 = "Remove";
	private String menuButton3 = "Modify";
	private String menuButton4 = "View";
	private JLabel display;
	private JPanel contentPane;

	

	public MenuInterface(JPanel panel){
		contentPane = makePanel(panel);
	}
	
	public JPanel makePanel(JPanel contentPane){
		
		contentPane.setLayout(new BorderLayout()); 
		//contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));
		
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1)); //1 column, as many rows as necessary
		ButtonGroup buttons = new ButtonGroup();
		
		display = new JLabel("Please Select an operation");
		buttonPanel.add(display);
		
		addMenuItem(buttonPanel, buttons,menuButton1);
		addMenuItem(buttonPanel, buttons,menuButton2);
		addMenuItem(buttonPanel, buttons, menuButton3);
		addMenuItem(buttonPanel, buttons, menuButton4);
				
		contentPane.add(buttonPanel); //add in the centre

		
		return contentPane;

	}
	
	public void addMenuItem(JPanel panel, ButtonGroup bgroup, String menuItem){
		JRadioButtonMenuItem button = new JRadioButtonMenuItem(menuItem);
		button.addActionListener(this); //method to handle the action is in this class
		bgroup.add(button);
		panel.add(button);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();

		if(command.equals("Add")){
			System.out.println("add");
		}
		else if(command.equals("Remove")){
			System.out.println("Remove");
		}
		else if(command.equals("Modify")){
			System.out.println("Modify");
		}
		else if(command.equals("View")){
			System.out.println("View");
		}
			
	}
		
	
	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}
}
