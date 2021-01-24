import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
public class RegisterGUI extends JFrame implements ActionListener{
	private Container contentPane;
	private JPanel welcomePanel;
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
	private JComboBox<String> accountTypeComboBox;
	private String[] accountTypes;
	private MongoQuery mongoQuery;
	
	public RegisterGUI() {
		super("Register");
		mongoQuery = new MongoQuery();
		initRegister();
	}

	public void initRegister() {
		contentPane = getContentPane();
		welcomePanel = new JPanel();
		contentPane.add(welcomePanel);
		welcomePanel.setLayout(new GridBagLayout());
		
		// create JComboBox
		accountTypes = new String[] {
				SignInConstants.DEFAULT_ACCOUNT_TYPE.toString(),
				SignInConstants.DEAN_ACCOUNT_TYPE.toString(),
				SignInConstants.PROFESSOR_ACCOUNT_TYPE.toString(),
				SignInConstants.STUDENT_ACCOUNT_TYPE.toString()
		};
		accountTypeComboBox = new JComboBox<>(accountTypes);
				
		// creates JLabels
		firstName = new JLabel(SignInConstants.FN_LABEL.toString());
		lastName = new JLabel(SignInConstants.LN_LABEL.toString());
		userID = new JLabel(SignInConstants.USERNAME_LABEL.toString());
		password = new JLabel(SignInConstants.PASSWORD_LABEL.toString());
		confirmPassword = new JLabel (SignInConstants.CONF_PASSWORD_LABEL.toString());
		
		// creating JTextFields
		firstNameField = new JTextField();
		lastNameField = new JTextField();
		userIDField = new JTextField();
		passwordField = new JTextField();
		confirmPasswordField = new JTextField();
		
		// register and cancel button
		registerButton = new JButton(SignInConstants.REGISTER_BTN_LABEL.toString());
		cancelButton = new JButton(SignInConstants.CANCEL_BTN_LABEL.toString());
		setupButtons();

		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0,5,0,5);
		
		welcomePanel.add(firstName, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		welcomePanel.add(lastName, constraints);
				
		constraints.gridx = 0;
		constraints.gridy = 2;
		welcomePanel.add(userID, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		welcomePanel.add(password, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		welcomePanel.add(confirmPassword, constraints);
		
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 1.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		welcomePanel.add(firstNameField, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		welcomePanel.add(lastNameField, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		welcomePanel.add(userIDField, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 3;
		welcomePanel.add(passwordField, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 4;
		welcomePanel.add(confirmPasswordField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		welcomePanel.add(accountTypeComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 6;
		welcomePanel.add(registerButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 7;
		welcomePanel.add(cancelButton, constraints);
		
		setPreferredSize(new Dimension(800,600));
		pack();
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void setupButtons() {
		registerButton.addActionListener(this);
		registerButton.setActionCommand(SignInConstants.REGISTER_BTN_CMD.toString());
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand(SignInConstants.CANCEL_BTN_CMD.toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (SignInConstants.REGISTER_BTN_CMD.toString().equals(e.getActionCommand())) {
			// register success when no fields missing, password match, username not in database, and account type valid
			if (checkIfFieldFilled() 
					&& passwordErrorCheck(passwordField.getText(), confirmPasswordField.getText())
					&& userNameNotExist()
					&& accountTypeErrorCheck()) {
				storeFields(userIDField.getText(), passwordField.getText(), firstNameField.getText(), lastNameField.getText());
				dispose();
			} else {
				String errorList = "Fields filled: " + checkIfFieldFilled() + "\n" + 
								   "Password match: " + passwordErrorCheck(passwordField.getText(), confirmPasswordField.getText()) + "\n" +
								   "Username unique: " + userNameNotExist() + "\n" +
								   "Account type valid: " + accountTypeErrorCheck();
				JOptionPane.showMessageDialog(welcomePanel, errorList);
			}
		} else if (SignInConstants.CANCEL_BTN_CMD.toString().equals(e.getActionCommand())) {
			dispose();
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
	 * Checks if username already exists in database
	 * @throws SQLException 
	 */
	private boolean userNameNotExist() {
		MongoCollection<Document> userpassCollection = mongoQuery.getCollection("Userpass");
		if (userpassCollection.countDocuments(new Document("username", userIDField.getText())) > 0) {
			return false;
		}
		return true;
	}

	/**
	 * Stores inputted fields into SQL database
	 * @throws SQLException 
	 */
	private void storeFields(String username, String password, String firstName, String lastName) {
		MongoCollection<Document> userPassCollection = mongoQuery.getCollection("Userpass");
		ObjectId uniqueUserID = new ObjectId();
		Document userPassPair = new Document("_id", uniqueUserID);
		userPassPair.append("username", username);
		userPassPair.append("password", password);
		userPassCollection.insertOne(userPassPair);

		switch ((String) accountTypeComboBox.getSelectedItem()) {
			case "Dean":
				MongoCollection<Document> deanCollection = mongoQuery.getCollection("Dean");
				createUserDocument(uniqueUserID, username, firstName, lastName, deanCollection);
				break;
			case "Professor":
				MongoCollection<Document> professorCollection = mongoQuery.getCollection("Professor");
				createUserDocument(uniqueUserID, username, firstName, lastName, professorCollection);
				break;
			case "Student":
				MongoCollection<Document> studentCollection = mongoQuery.getCollection("Student");
				createUserDocument(uniqueUserID, username, firstName, lastName, studentCollection);
				break;
		}
		mongoQuery.closeConnection();
	}

	private void createUserDocument(ObjectId uniqueUserId, String username, String firstName, String lastName, MongoCollection<Document> userCollection) {
		Document userDocument = new Document("_id", uniqueUserId);
		userDocument.append("username", username);
		userDocument.append("first name", firstName);
		userDocument.append("last name", lastName);
		userCollection.insertOne(userDocument);
	}

	/**
	 * Checks if any fields are missing and put red outline on the missing fields
	 * @return boolean false if missing fields, true otherwise
	 */
	private boolean checkIfFieldFilled() {
		boolean filled = true;
		for (Component component : welcomePanel.getComponents()) {
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
