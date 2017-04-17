package models;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
public class UserInterface implements ActionListener{
	//private MenuInterface menu = new MenuInterface();
	private String title = "Family Tree";
	private JFrame frame; 



	public UserInterface(){
		makeFrame();
		//displayMainMenu();
		//displayAddMenu();
		//displayRemoveMenu();
		//displayModifyMenu();
		displayViewMenu();
	}
	
	public void makeFrame(){
		frame = new JFrame(title);
		
		//JPanel contentPane = (JPanel)frame.getContentPane();
		//contentPane.setLayout(new BorderLayout(10,0)); 
		//contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10))
	}
	
	public void displayMainMenu(){
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setLayout(new BorderLayout(10,0)); 
		contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10));
		MenuInterface menu = new MenuInterface(contentPane);
		
		JPanel displayPane = menu.getContentPane();
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
	}
	
	public void displayAddMenu(){
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setLayout(new BorderLayout(10,0));
		AddPersonInterface addMenu = new AddPersonInterface(contentPane);
		
		JPanel displayPane = addMenu.getAddPanel();
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
	}
	
	public void displayRemoveMenu(){
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setLayout(new BorderLayout(10,0));
		RemovalInterface removalMenu = new RemovalInterface(contentPane);
		
		JPanel displayPane = removalMenu.getRemovePanel();
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
	}
	
	public void displayModifyMenu(){
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setLayout(new BorderLayout(10,0));
		ModifierInterface modifyMenu = new ModifierInterface(contentPane);
		
		JPanel displayPane = modifyMenu.getModifyPanel();
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
	}
	
	public void displayViewMenu(){
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setLayout(new BorderLayout(10,0));
		DisplayInterface modifyMenu = new DisplayInterface(contentPane);
		
		JPanel displayPane = modifyMenu.getViewPanel();
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
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
		}	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}
