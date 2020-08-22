import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class Login implements ActionListener {
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
	private String accountSelection;
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
				loginSuccess(accountSelection);
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
		try {
			MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT * FROM userPass WHERE username = ? AND password = ?");
			MainRun.myStmt.setString(1, usernameField.getText());
			MainRun.myStmt.setString(2, passwordField.getText());
			MainRun.myRs = MainRun.myStmt.executeQuery();
			if (MainRun.myRs.next()) {
				switch (accountType) {
					case "Dean":
						MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT deanID FROM dean WHERE deanID = ?");
						MainRun.myStmt.setString(1, usernameField.getText());
						MainRun.myRs = MainRun.myStmt.executeQuery();
						if (MainRun.myRs.next()) {
							accountSelection = "Dean";
							return true;
						}
						break;
					case "Professor":
						MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT professorID FROM professor WHERE professorID = ?");
						MainRun.myStmt.setString(1, usernameField.getText());
						MainRun.myRs = MainRun.myStmt.executeQuery();
						if (MainRun.myRs.next()) {
							accountSelection = "Professor";
							return true;
						}
						break;
					case "Student":
						MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT studentID FROM student WHERE studentID = ?");
						MainRun.myStmt.setString(1, usernameField.getText());
						MainRun.myRs = MainRun.myStmt.executeQuery();
						if (MainRun.myRs.next()) {
							accountSelection = "Student";
							return true;
						}
						break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
