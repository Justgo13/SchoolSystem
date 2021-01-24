import com.mongodb.client.FindIterable;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;

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
	private JLabel userID;
	private JLabel password;
	private JButton loginButton;
	private JButton cancelButton;
	private MongoQuery mongoQuery;
	private ObjectId objectId;
    /**
     * Creates login GUI
     */
    public Login() {
		mongoQuery = new MongoQuery();
		objectId = new ObjectId();
    	initLogin();
    }
    
    public void initLogin() {
        contentPane = getContentPane();

        loginPanel = new JPanel(new GridBagLayout());
        contentPane.add(loginPanel);
 		
        // initialize login fields
        usernameField = new JTextField();
        passwordField = new JTextField();
 		
 		// creates JLabels
 		userID = new JLabel(SignInConstants.USERNAME_LABEL.toString());
		password = new JLabel(SignInConstants.PASSWORD_LABEL.toString());
		
		// create button
		loginButton = new JButton(SignInConstants.LOGIN_BTN_LABEL.toString());
		cancelButton = new JButton(SignInConstants.CANCEL_BTN_LABEL.toString());

		// adding actionListener
		setupButtons();

		constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.EAST;
		//constraints.insets = new Insets(0,5,0,5);
		
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
		loginPanel.add(passwordField, constraints);

		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.FIRST_LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 2;
		loginPanel.add(loginButton, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		loginPanel.add(cancelButton, constraints);

        setPreferredSize(new Dimension(800,600));
		pack();
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
    }

	private void setupButtons() {
		loginButton.addActionListener(this);
		loginButton.setActionCommand(SignInConstants.LOGIN_BTN_CMD.toString());
		cancelButton.addActionListener(this);
		cancelButton.setActionCommand(SignInConstants.CANCEL_BTN_CMD.toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
        if (SignInConstants.LOGIN_BTN_CMD.toString().equals(e.getActionCommand())) {
        	// login success when no fields missing, valid password, username exists, and account type valid
			if (checkAccountDetails()) {
				loginSuccess(objectId);
				dispose();
			} else {
				String errorList = "Account details: " + checkAccountDetails();
				JOptionPane.showMessageDialog(loginPanel, errorList);
			}
        } else if (SignInConstants.CANCEL_BTN_CMD.toString().equals(e.getActionCommand())) {
			dispose();
		}
	}
	
	/**
	 * Checks if account login is correct
	 * @return boolean true if it matches database, false otherwise
	 */
	private boolean checkAccountDetails() {
		MongoCollection<Document> userCollection = mongoQuery.getCollection("Userpass");
		FindIterable<Document> userPassPair = userCollection.find();
		while (userPassPair.cursor().hasNext()) {
			Document userpass = userPassPair.cursor().next();
			String username = userpass.getString("username");
			String password = userpass.getString("password");
			if (username.equals(usernameField.getText()) && password.equals(passwordField.getText())) { // correct user found
				objectId = userpass.getObjectId("_id");
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
	private void loginSuccess(ObjectId objectID) {
		String collectionName = "";
		/* Figured out that a user exists and need to find the type of user that logged in */
		ArrayList<Document> userAccountDocuments = mongoQuery.getUserAccountDocuments();
		for (Document doc : userAccountDocuments) {
			if (doc.get("_id").equals(objectID)) {
				collectionName = mongoQuery.getDocumentCollectionName(doc);
				break;
			}
		}
    	switch (collectionName) {
			case "Dean":
				new DeanGUI();
			break;
			case "Professor":
				System.out.println("Logged in as professor" );
				new ProfFrame(usernameField.getText());
			break;
			case "Student":
				new StudentGUI(usernameField.getText());
			break;
    	}
	}
}
