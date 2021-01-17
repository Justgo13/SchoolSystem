import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Panel for setting professor salary
 * @author jgao2
 *
 */
public class SalaryGUI implements ActionListener{
	private JFrame frame;
	private Container contentPane;
	private JPanel salaryPanel;
	private JComboBox<Object> professorComboBox;
	private ArrayList<String> professorIDs;
	private JTextField salaryField;
	private GridBagConstraints constraints;
	private JLabel salaryLabel;
	private JButton applyButton;
	private JButton cancelButton;
	private String query;
	private ArrayList<String> queryParams;
	private ResultSet queryResult;
	private SQLQuery SQLInstance;

	public SalaryGUI() {
		query = "";
		queryParams = new ArrayList<>();
		queryResult = null;
		SQLInstance = null;
		initSalaryGUI();
	}
	
	public void initSalaryGUI() {
		frame = new JFrame("Set salary");
		contentPane = frame.getContentPane();
		salaryPanel = new JPanel(new GridBagLayout());
		contentPane.add(salaryPanel);
		
		// apply and cancel button
		applyButton = new JButton("Apply");
		cancelButton = new JButton("Cancel");
		applyButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		// creating and setup JComboBox
		professorIDs = new ArrayList<String>();
		professorIDs.add("Choose a professor");

		SQLInstance = new SQLQuery();
		query = "SELECT professorID FROM professor";
		queryResult = SQLInstance.runQuery(query, queryParams);
		try {
			if (!queryResult.next()) {
				System.out.println("Nothing was queried");
			} else {
				do {
					professorIDs.add(queryResult.getString("professorID"));
				} while (queryResult.next());
			}
			SQLInstance.getMyConn().close();
			System.out.println("Connection terminated");
		} catch (SQLException e) {
			e.getStackTrace();
		}
		
		professorComboBox = new JComboBox<Object>(professorIDs.toArray());
		
		// creating salary label and textfield
		salaryLabel = new JLabel("Enter salary");
		salaryField = new JTextField();
		
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0,5,0,5);
		
		salaryPanel.add(professorComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		salaryPanel.add(salaryLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.WEST;
		salaryPanel.add(salaryField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		salaryPanel.add(applyButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		salaryPanel.add(cancelButton, constraints);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		JButton button = (JButton) o;
		if (button.equals(applyButton)) {
			if (checkValidProfessor() && checkSalaryEmpty() && checkValidSalary()) {
				applySalary((String) professorComboBox.getSelectedItem());
				frame.dispose();
			} else {
				String errorList = "Valid professor: " + checkValidProfessor() + "\n" +
				   		   		   "Salary entered: " + checkSalaryEmpty() + "\n" +
				   		   		   "Salary is number: " + checkValidSalary();
				JOptionPane.showMessageDialog(salaryPanel, errorList);
			}
		} else if (button.equals(cancelButton)) {
			frame.dispose();
		}
		
	}
	/**
	 * Checks if salary is a number only
	 * @return
	 */
	private boolean checkValidSalary() {
		try {
			Integer.parseInt(salaryField.getText());
		} catch (NumberFormatException e) {
			salaryField.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		}
		return true;
	}

	/**
	 * Applys salary to the chosen professor
	 * @param professor
	 */
	private void applySalary(String professor) {
		SQLInstance = new SQLQuery();
		query = "UPDATE professor SET salary = ? WHERE professorID = ?";
		queryParams.clear();
		queryParams.add(salaryField.getText());
		queryParams.add((String) professorComboBox.getSelectedItem());
		SQLInstance.runUpdate(query, queryParams);

		try {
			SQLInstance.getMyConn().close();
			System.out.println("Connection terminated");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if salary field is empty
	 * @return false if empty, true otherwise
	 */
	private boolean checkSalaryEmpty() {
		if (salaryField.getText().isEmpty()) {
			salaryField.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		}
		return true;
	}

	private boolean checkValidProfessor() {
		if (professorComboBox.getSelectedIndex() == 0) {
			professorComboBox.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		} else {
			professorComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		return true;
	}
}
