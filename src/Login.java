import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
 * Login screen GUI
 */
public class Login extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JTextField passwordField;
	private Container contentPane;
	private JPanel loginPanel;
	private GridBagConstraints constraints;
	private ArrayList<String> accountTypes;
	private JComboBox<Object> accountTypeComboBox;
	private JLabel userID;
	private JLabel password;
	private JButton loginButton;
	private JButton cancelButton;
	private String accountSelection;
	private String query;
	private ArrayList<String> queryParams;
	private ResultSet queryResult;
	private SQLQuery SQLInstance;
    /**
     * Creates login GUI
     */
    public Login() {
		query = "";
		queryParams = new ArrayList<>();
		queryResult = null;
		SQLInstance = null;
    	initLogin();
    }
    
    public void initLogin() {
        contentPane = getContentPane();

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

        setPreferredSize(new Dimension(800,600));
		pack();
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
        JButton button = (JButton) o;

        if (button.equals(loginButton)) {
        	// login success when no fields missing, valid password, username exists, and account type valid
			if (checkAccountDetails((String) accountTypeComboBox.getSelectedItem())
					&& accountTypeErrorCheck()) {
				loginSuccess(accountSelection);
				dispose();
			} else {
				String errorList = "Account details: " + checkAccountDetails((String) accountTypeComboBox.getSelectedItem()) + "\n" +
						   		   "Account type valid: " + accountTypeErrorCheck();
				JOptionPane.showMessageDialog(loginPanel, errorList);
			}
        } else if (button.equals(cancelButton)) {
			dispose();
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
		SQLInstance = new SQLQuery();
		query = "SELECT * FROM userPass WHERE username = ? AND password = ?";
		queryParams.clear();
		queryParams.add(usernameField.getText());
		queryParams.add(passwordField.getText());
		queryResult = SQLInstance.runQuery(query, queryParams);
		try {
			if (queryResult.next()) {
				switch (accountType) {
					case "Dean":
						query = "SELECT deanID FROM dean WHERE deanID = ?";
						queryParams.clear();
						queryParams.add(usernameField.getText());
						queryResult = SQLInstance.runQuery(query, queryParams);
						if (queryResult.next()) {
							accountSelection = "Dean";
							return true;
						}
						break;
					case "Professor":
						query = "SELECT professorID FROM professor WHERE professorID = ?";
						queryParams.clear();
						queryParams.add(usernameField.getText());
						queryResult = SQLInstance.runQuery(query, queryParams);
						if (queryResult.next()) {
							accountSelection = "Professor";
							return true;
						}
						break;
					case "Student":
						query = "SELECT studentID FROM student WHERE studentID = ?";
						queryParams.clear();
						queryParams.add(usernameField.getText());
						queryResult = SQLInstance.runQuery(query, queryParams);
						if (queryResult.next()) {
							accountSelection = "Student";
							return true;
						}
						break;
				}
			}
			SQLInstance.getMyConn().close();
			System.out.println("Connection terminated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		usernameField.setBorder(BorderFactory.createLineBorder(Color.RED));
		passwordField.setBorder(BorderFactory.createLineBorder(Color.RED));
		return false;
	}
	
	/**
	 * Opens the correct gui panel based on login details
	 */
	private void loginSuccess(String accountSelection) {
    	switch (accountSelection) {
			case "Dean":
				new DeanGUI();
			break;
			case "Professor":
				new ProfGUI(usernameField.getText());
			break;
			case "Student":
				new StudentGUI(usernameField.getText());
			break;
    	}
	}
}
