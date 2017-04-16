package models;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class UserInterface implements ActionListener{
	//private MenuInterface menu = new MenuInterface();
	private String title = "Family Tree";
	private JFrame frame; 



	public UserInterface(){
		makeFrame();
		displayMenu();
	}
	
	public void makeFrame(){
		frame = new JFrame(title);
		
		//JPanel contentPane = (JPanel)frame.getContentPane();
		//contentPane.setLayout(new BorderLayout(10,0)); 
		//contentPane.setBorder(new EmptyBorder( 10, 10, 10, 10))
	}
	
	public void displayMenu(){
		
		
		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setLayout(new BorderLayout(10,0)); 
		MenuInterface menu = new MenuInterface(contentPane);
		
		JPanel displayPane = menu.getContentPane();
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("action performed");		
	}
}
