import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
/**
 * Login screen GUI
 */
public class Login implements ActionListener, dataStorage {
    private JTextField usernameField;
    private JTextField passwordField;
	private JFrame frame;
	private Container contentPane;
	private JPanel loginPanel;
	private GridBagConstraints constraints;
	private ArrayList<String> accountTypes;
	private JComboBox<Object> accountTypeComboBox;
	private JLabel userID;
	private JLabel password;
	private JButton loginButton;
	private JButton cancelButton;

    /**
     * Creates login GUI
     */
    public Login() {
    	initLogin();
    }
    
    public void initLogin() {
        frame = new JFrame();
        contentPane = frame.getContentPane();

        loginPanel = new JPanel(new GridBagLayout());
        contentPane.add(loginPanel);
        
        // create JComboBox
 		accountTypes = new ArrayList<String> (List.of("Choose account type", "Dean", "Professor", "Student"));
 		accountTypeComboBox = new JComboBox<Object>(accountTypes.toArray());
 		
        // initialize login fields
        usernameField = new JTextField();
        passwordField = new JTextField();
 		
 		// creates JLabels
 		userID = new JLabel("User ID");
		password = new JLabel("Password");
		
		// create button
		loginButton = new JButton("Login");
		cancelButton = new JButton("Cancel");

		// adding actionListener
		loginButton.addActionListener(this);
		cancelButton.addActionListener(this);
    
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0,5,0,5);
		
		loginPanel.add(userID, constraints); 
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		loginPanel.add(password, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.WEST;
		loginPanel.add(usernameField, constraints); 
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		loginPanel.add(passwordField, constraints);
		
		constraints.gridwidth = 2;
		constraints.gridx = 0;
		constraints.gridy = 2;
		loginPanel.add(accountTypeComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		loginPanel.add(loginButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		loginPanel.add(cancelButton, constraints);

        frame.setPreferredSize(new Dimension(800,600));
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
        JButton button = (JButton) o;

        if (button.equals(loginButton)) {

        	// login success when no fields missing, valid password, username exists, and account type valid
			if (checkAccountDetails((String) accountTypeComboBox.getSelectedItem())
					&& accountTypeErrorCheck()) {
				loginSuccess();
				frame.dispose();
			} else {
				String errorList = "Account details: " + checkAccountDetails((String) accountTypeComboBox.getSelectedItem()) + "\n" +
						   		   "Account type valid: " + accountTypeErrorCheck();
				JOptionPane.showMessageDialog(loginPanel, errorList);
			}
        } else if (button.equals(cancelButton)) {
			frame.dispose();
		}
	}
	
	/**
	 * Checks if an account type was chosen
	 */
	
	private boolean accountTypeErrorCheck() {
		if (accountTypeComboBox.getSelectedIndex() == 0) {
			accountTypeComboBox.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		} else {
			accountTypeComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		return true;
	}
	
	/**
	 * Checks if account login is correct
	 * @return boolean true if it matches database, false otherwise
	 */
	private boolean checkAccountDetails(String accountType) {
		switch (accountType) {
			case "Dean":
				if (deanUserPass.containsKey(Map.of(usernameField.getText(), passwordField.getText()))) {
					return true;
				}
			case "Professor":
				if (professorUserPass.containsKey(Map.of(usernameField.getText(), passwordField.getText()))) {
					return true;
				}
			case "Student":
				if (studentUserPass.containsKey(Map.of(usernameField.getText(), passwordField.getText()))) {
					return true;
				}
		}
		usernameField.setBorder(BorderFactory.createLineBorder(Color.RED));
		passwordField.setBorder(BorderFactory.createLineBorder(Color.RED));
		return false;
	}
	
	/**
	 * Opens the correct gui panel based on login details
	 */
	private void loginSuccess() {
    	switch ((String) accountTypeComboBox.getSelectedItem()) {
			case "Dean":
				new DeanGUI();
			case "Professor":
				
			case "Student":
		
    	}
	}
}
