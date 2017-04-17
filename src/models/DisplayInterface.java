package models;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	private JFrame frame;
	private String title = "Family Tree";

	
	public DisplayInterface(JPanel panel){
		fileReader = new FileReader();
		viewPanel = makePanel(panel);
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
		
		topPanel.add(instructions);
		middlePanel.add(name);
		bottomPanel.add(confirm);
		
		return panel;
	}
	
	/**
	 * When a name has been selected and the button to "View" their ancestry has been pressed, 
	 * open the view to show them their heritage 
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		String findName = name.getText();
		viewPerson = fileReader.getPeopleMap().get(findName);
					
		frame = new JFrame(title);

		JPanel contentPane = (JPanel)frame.getContentPane();
		contentPane.setLayout(new BorderLayout(10,10));
		//modifyPanel = makeNextPanel();

		JPanel displayPane = makeNextPanel(contentPane);
		contentPane = displayPane;
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public JPanel makeNextPanel(JPanel panel){
		panel.setLayout(new BorderLayout()); 

		instructions = new JLabel("Your Ancestors: ");
		panel.add(instructions);
		
		return panel;		
	}

	public JPanel getViewPanel() {
		return viewPanel;
	}

	public void setViewPanel(JPanel viewPanel) {
		this.viewPanel = viewPanel;
	}
	
	

}
