package models;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private FileReader fileReader = new FileReader();
	private JPanel viewPanel;
	private Person viewPerson = new Person(null, ' ', 0, null, null, null, null);
	private JTextArea textArea = new JTextArea(10,110);
	private JFrame frame;
	private JFrame frame1;
	private String title = "Family Tree";
	private JButton returnButton = new JButton("Return to Main Menu");
	private JButton backButton = new JButton("Back");
	
	
	public DisplayInterface(){
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
			String findName = name.getText();
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			
			frame1 = new JFrame(title);
			
			viewPerson = fileReader.getPeopleMap().get(findName);
			
			textArea = TreePrinter.print(viewPerson);
			
			
			JPanel contentPane = (JPanel)frame1.getContentPane();
			contentPane.setPreferredSize(new Dimension(500, 400));
			JPanel displayPane = makeNextPanel(contentPane);
			contentPane = displayPane;
			frame1.pack();
			frame1.setVisible(true);
		}
		else if(event.getActionCommand().equals("Return to Main Menu")){
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			UserInterface user = new UserInterface();
		}
		
		else if(event.getActionCommand().equals("Back")){
			frame1.dispatchEvent(new WindowEvent(frame1, WindowEvent.WINDOW_CLOSING));
			DisplayInterface view = new DisplayInterface();
		}
		
		
		
	}
	
	public JPanel makeNextPanel(JPanel panel){
		panel.setLayout(new BorderLayout()); 

		JPanel bottom = new JPanel();
		JPanel middle = new JPanel();
		JPanel top = new JPanel();
		panel.add(top, BorderLayout.NORTH);
		panel.add(middle, BorderLayout.CENTER);
		panel.add(bottom, BorderLayout.SOUTH);
		
		instructions = new JLabel("Your Ancestors: ");
		backButton.addActionListener(this);
		
		top.add(instructions);
		middle.add(textArea);
		bottom.add(backButton);
		
		//panel.add(top);
		//panel.add(middle);
		//panel.add(bottom);
		
		return panel;		
	}

	public JPanel getViewPanel() {
		return viewPanel;
	}

	public void setViewPanel(JPanel viewPanel) {
		this.viewPanel = viewPanel;
	}
	
	

}
