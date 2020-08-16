import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * This is the register account screen
 * @author jgao2
 *
 */
public class Register implements ActionListener, dataStorage{
	private JFrame frame;
	private Container contentPane;
	private JPanel panel;
	private JLabel firstName;
	private JLabel lastName;
	private JLabel userID;
	private JLabel password;
	private JLabel confirmPassword;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField userIDField;
	private JTextField passwordField;
	private JTextField confirmPasswordField;
	private GridBagConstraints constraints;
	private JButton registerButton;
	private JButton cancelButton;
	private JComboBox<?> accountTypeComboBox;
	private ArrayList<String> accountTypes;
	
	public Register() {
		initRegister();
	}

	public void initRegister() {
		frame = new JFrame("Register");
		contentPane = frame.getContentPane();
		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new GridBagLayout());
		
		// create JComboBox
		accountTypes = new ArrayList<String> (List.of("Choose account type", "Dean", "Professor", "Student"));
		accountTypeComboBox = new JComboBox<Object>(accountTypes.toArray());
				
		// creates JLabels
		firstName = new JLabel("First Name");
		lastName = new JLabel("Last Name");
		userID = new JLabel("User ID");
		password = new JLabel("Password");
		confirmPassword = new JLabel ("Confirm Password");
		
		// creating JTextFields
		firstNameField = new JTextField();
		lastNameField = new JTextField();
		userIDField = new JTextField();
		passwordField = new JTextField();
		confirmPasswordField = new JTextField();
		
		// register and cancel button
		registerButton = new JButton("Register");
		cancelButton = new JButton("Cancel");
		registerButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0,5,0,5);
		
		panel.add(firstName, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(lastName, constraints);
				
		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(userID, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		panel.add(password, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		panel.add(confirmPassword, constraints);
		
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 1.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		panel.add(firstNameField, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		panel.add(lastNameField, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		panel.add(userIDField, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 3;
		panel.add(passwordField, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 4;
		panel.add(confirmPasswordField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		panel.add(accountTypeComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 6;
		panel.add(registerButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 7;
		panel.add(cancelButton, constraints);
		
		frame.setPreferredSize(new Dimension(800,600));
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		JButton button = (JButton) o;
		if (button.equals(registerButton)) {
			// register success when no fields missing, password match, username not in database, and account type valid
			if (checkIfFieldFilled() 
					&& passwordErrorCheck(passwordField.getText(), confirmPasswordField.getText())
					&& userNameErrorCheck(userIDField.getText())
					&& accountTypeErrorCheck()) {
				storeFields(userIDField.getText(), passwordField.getText());
				frame.dispose();
			} else {
				String errorList = "Fields filled: " + checkIfFieldFilled() + "\n" + 
								   "Password match: " + passwordErrorCheck(passwordField.getText(), confirmPasswordField.getText()) + "\n" +
								   "Username unique: " + userNameErrorCheck(userIDField.getText()) + "\n" +
								   "Account type valid: " + accountTypeErrorCheck();
				JOptionPane.showMessageDialog(panel, errorList);
			}
		} else if (button.equals(cancelButton)) {
			frame.dispose();
		}
	}

	/**
	 * Checks if password and confirm password are the same
	 */
	private boolean passwordErrorCheck(String password, String confirmPassword) {
		if (!password.equals(confirmPassword) || passwordField.getText().isEmpty()) {
			passwordField.setBorder(BorderFactory.createLineBorder(Color.RED));
			confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		}
		return true;
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
	 * Checks if username already exists
	 */
	private boolean userNameErrorCheck(String userID) {
		if (userPass.contains(userID) || userIDField.getText().isEmpty()) {
			userIDField.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		}
		return true;
	}

	/**
	 * Stores inputted fields into hashmap
	 */
	private void storeFields(String username, String password) {
		if (!userPass.contains(username)) {
			userPass.add(username);
			switch ((String) accountTypeComboBox.getSelectedItem()) {
				case "Dean":
					deanUserPass.put(Map.of(username, password), new Dean(firstNameField.getText(), lastNameField.getText(), 0, userIDField.getText()));
					break;
				case "Professor":
					professorUserPass.put(Map.of(username, password), new Professor(firstNameField.getText(), lastNameField.getText(), 0, userIDField.getText()));
					break;
				case "Student":
					studentUserPass.put(Map.of(username, password), new Student(firstNameField.getText(), lastNameField.getText(), userIDField.getText()));
			}
		}
	}

	/**
	 * Checks if any fields are missing and put red outline on the missing fields
	 * @return boolean false if missing fields, true otherwise
	 */
	private boolean checkIfFieldFilled() {
		boolean filled = true;
		for (Component component : panel.getComponents()) {
			if (component instanceof JTextField) {
				JTextField textField = (JTextField) component;
				if (textField.getText().isEmpty()) {
					textField.setBorder(BorderFactory.createLineBorder(Color.RED));
					filled = false;
				} else {
					textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				}
			}
		}
		return filled;
	}
}