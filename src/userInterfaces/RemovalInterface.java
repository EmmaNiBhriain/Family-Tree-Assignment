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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.FileReader;

public class RemovalInterface implements ActionListener{
	private FileReader fileReader;
	private JPanel RemovePanel;
	private JPanel topPanel = new JPanel();
	private JPanel middlePanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JTextField name;
	private JLabel instructions;
	private JButton confirm;
	private JFrame frame;
	private String title = "Family Tree";
	private JButton returnButton = new JButton("Return to Main Menu");

	/**
	 * Constructor for the user interface that allows the user to remove a person from the program
	 * @param reader
	 */
	public RemovalInterface(FileReader reader){
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
				
		instructions = new JLabel("Enter Name of Person to Remove");
		name = new JTextField(10);
		confirm = new JButton("OK");
		confirm.addActionListener(this);
		
		returnButton.addActionListener(this);

		
		topPanel.add(instructions);
		middlePanel.add(name);
		bottomPanel.add(returnButton);
		bottomPanel.add(confirm);
		
		return panel;
		
	}

	/**
	 * Allows the user to input the name of the person they would like to remove. 
	 * If this person is not stored in the program, a warning dialog box appears
	 * Otherwise, the person is removed from the program
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("OK")){
			System.out.println("Hashmap size: " + fileReader.getPeopleMap().size());
			System.out.println(name.getText());
			if(fileReader.getPeopleMap().containsKey(name.getText())){
				fileReader.remove(name.getText());
				System.out.println("Person removed");
				System.out.println("Hashmap size: " + fileReader.getPeopleMap().size());
				JOptionPane.showMessageDialog(null, "You have successfully removed a Person",  "Success!", JOptionPane.INFORMATION_MESSAGE);
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //close the current window and redisplay the main menu
				UserInterface user = new UserInterface(fileReader);
			}
			else
				JOptionPane.showMessageDialog(null, "This person is not stored in the database", "Error", JOptionPane.ERROR_MESSAGE); 
			
		}
		
		else if(e.getActionCommand().equals("Return to Main Menu")){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING)); //close the current window and redisplay the main menu
			UserInterface user = new UserInterface(fileReader);
		}
		
	}


	public JPanel getRemovePanel() {
		return RemovePanel;
	}


	public void setRemovePanel(JPanel removePanel) {
		RemovePanel = removePanel;
	}
	

}
