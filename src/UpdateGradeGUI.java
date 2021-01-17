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
import java.util.Arrays;
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
 * A panel for updating student grades
 * @author jgao2
 *
 */
public class UpdateGradeGUI implements ActionListener {
	private JFrame frame;
	private Container contentPane;
	private JPanel gradePanel;
	private JButton applyButton;
	private JButton cancelButton;
	private ArrayList<String> studentIDs;
	private JComboBox<Object> studentComboBox;
	private JLabel gradeLabel;
	private JTextField gradeField;
	private GridBagConstraints constraints;
	private JComboBox<?> courseComboBox;
	private ArrayList<String> courseCode;
	private String profID;
	private String query;
	private ArrayList<String> queryParams;
	private ResultSet queryResult;
	private SQLQuery SQLInstance;
	public UpdateGradeGUI(String profID) {
		this.profID = profID;
		query = "";
		queryParams = new ArrayList<>();
		queryResult = null;
		SQLInstance = null;
		initUpdateGradeGUI();
	}
	private void initUpdateGradeGUI() {
		frame = new JFrame("Update Grade");
		contentPane = frame.getContentPane();
		gradePanel = new JPanel(new GridBagLayout());
		contentPane.add(gradePanel);
		
		// apply and cancel button
		applyButton = new JButton("Apply");
		cancelButton = new JButton("Cancel");
		applyButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		// professor courses to comboBox
		courseCode = new ArrayList<>();
		courseCode.add("Choose a course code");

		populateProfessorCourses();

		studentIDs = new ArrayList<>();
		studentIDs.add("Choose a student");

		populateStudents();

		studentComboBox = new JComboBox<>(studentIDs.toArray());
		
		// creating salary label and textField
		gradeLabel = new JLabel("Enter grade");
		gradeField = new JTextField();
		
		courseComboBox = new JComboBox<>(courseCode.toArray());
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0,5,0,5);
		
		gradePanel.add(studentComboBox, constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		gradePanel.add(courseComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		gradePanel.add(gradeLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.weightx = 1.0;
		constraints.anchor = GridBagConstraints.WEST;
		gradePanel.add(gradeField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		gradePanel.add(applyButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 4;
		gradePanel.add(cancelButton, constraints);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
	}

	private void populateStudents() {
		SQLInstance = new SQLQuery();
		query = "SELECT studentID FROM student";
		queryParams.clear();
		queryResult = SQLInstance.runQuery(query, queryParams);
		try {
			ResultSet studentQuery;
			while (queryResult.next()) {
				query = "SELECT courses FROM student WHERE studentID = ?";
				queryParams.clear();
				queryParams.add(queryResult.getString("studentID"));
				studentQuery = SQLInstance.runQuery(query, queryParams);
				if (studentQuery.next()) {
					String [] course = studentQuery.getString("courses").split(",");
					List<String> studentCourseList = Arrays.asList(course);
					ArrayList<String> courses = new ArrayList<String>(studentCourseList);
					for (String c : courses) {
						if (courseCode.contains(c)) {
							studentIDs.add(queryResult.getString("studentID"));
						}
					}
				}
			}
			SQLInstance.getMyConn().close();
			System.out.println("Connection terminated");
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}

	private void populateProfessorCourses() {
		SQLInstance = new SQLQuery();
		query = "SELECT courseTaught FROM professor WHERE professorID = ?";
		queryParams.clear();
		queryParams.add(profID);
		queryResult = SQLInstance.runQuery(query, queryParams);
		try {
			if (queryResult.next()) {
				String [] courseTaught = queryResult.getString("courseTaught").split(",");
				List<String> professorCourseList = Arrays.asList(courseTaught);
				ArrayList<String> courses = new ArrayList<String>(professorCourseList);
				courses.forEach(c -> courseCode.add(c));
			}
			SQLInstance.getMyConn().close();
			System.out.println("Connection terminated");
		} catch (SQLException e){
			e.getStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		JButton button = (JButton) o;
		if (button.equals(applyButton)) {
			if (checkValidStudent() && checkGradeEmpty() && checkValidGrade() && checkSelectedCourseCode() && courseCodeExistForStudent()) {
				applyGrade((String) studentComboBox.getSelectedItem());
				frame.dispose();
			} else {
				String errorList = "Valid student: " + checkValidStudent() + "\n" +
				   		   		   "Grade inputted: " + checkGradeEmpty() + "\n" +
				   		   		   "Grade is valid: " + checkValidGrade() + "\n" +
				   		   		   "Course code chosen: " + checkSelectedCourseCode() + "\n" +
				   		   		   "Course code exist for student: " + courseCodeExistForStudent();
				JOptionPane.showMessageDialog(gradePanel, errorList);
			}
		} else if (button.equals(cancelButton)) {
			frame.dispose();
		}
	
	/**
	 * Checks if course code chosen is being taken by student
	 */
	}
	private boolean courseCodeExistForStudent() {
		SQLInstance = new SQLQuery();
		query = "SELECT courses FROM student WHERE studentID = ?";
		queryParams.clear();
		queryParams.add((String) studentComboBox.getSelectedItem());
		queryResult = SQLInstance.runQuery(query, queryParams);
		try {
			if (queryResult.next()) {
				String [] studentCourses = queryResult.getString("courses").split(",");
				List<String> studentCourseList = Arrays.asList(studentCourses);
				ArrayList<String> courses = new ArrayList<>(studentCourseList);
				
				for (String course : courses) {
					if (course.equals(courseComboBox.getSelectedItem())) {
						return true;
					}
				}
			}
			SQLInstance.getMyConn().close();
			System.out.println("Connection terminated");
		} catch (SQLException e) {
			e.getStackTrace();
		}
		courseComboBox.setBorder(BorderFactory.createLineBorder(Color.RED));
		return false;
	}
	/**
	 * Checks if grade is valid number
	 * @return
	 */
	private boolean checkValidGrade() {
		try {
			Integer.parseInt(gradeField.getText());
		} catch (NumberFormatException e) {
			gradeField.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		}
		return true;
	}

	/**
	 * Applys grade to the chosen professor
	 * @param studentName name of the Student getting a grade change
	 */
	private void applyGrade(String studentName) {
		ArrayList<String> courses = null;
		ArrayList<String> grades = null;
		SQLInstance = new SQLQuery();
		query = "SELECT courses FROM student WHERE studentID = ?";
		queryParams.clear();
		queryParams.add((String) studentComboBox.getSelectedItem());
		queryResult = SQLInstance.runQuery(query, queryParams);
		try {
			if (queryResult.next()) {
				String [] studentCourses = queryResult.getString("courses").split(",");
				List<String> studentCourseList = Arrays.asList(studentCourses);
				courses = new ArrayList<String>(studentCourseList);
//				if (MainRun.myRs.getString("courses") == null) {
//					courses = new ArrayList<String>();
//				} else {
//					String [] studentCourses = MainRun.myRs.getString("courses").split(",");
//					List<String> studentCourseList = Arrays.asList(studentCourses);
//					courses = new ArrayList<String>(studentCourseList);
//				}
			}

			query = "SELECT grades FROM student WHERE studentID = ?";
			queryParams.clear();
			queryParams.add((String) studentComboBox.getSelectedItem());
			queryResult = SQLInstance.runQuery(query, queryParams);
			
			if (queryResult.next()) {
				String [] studentGrades = queryResult.getString("grades").split(",");
				List<String> studentGradeList = Arrays.asList(studentGrades);
				grades = new ArrayList<String>(studentGradeList);
//				if (queryResult.getString("grades") == null) {
//					grades = new ArrayList<String>();
//				} else {
//					String [] studentGrades = gradeResultSet.getString("grades").split(",");
//					List<String> studentGradeList = Arrays.asList(studentGrades);
//					grades = new ArrayList<String>(studentGradeList);
//				}
			}

			// finds course to change and changes corresponding course grade
			for (int i = 0; i < courses.size(); i++) {
				if (courses.get(i).equals(courseComboBox.getSelectedItem())) {
					grades.set(i, gradeField.getText());
				}
			}
			
			String courseToPass = "";
			String gradeToPass = "";
			for (String courseToUpdate : courses) {
				if (courseToUpdate.isEmpty()) {
					continue;
				}
				courseToPass += courseToUpdate + ",";
			}
			for (String gradeToUpdate : grades) {
				if (gradeToUpdate.isEmpty()) {
					continue;
				}
				gradeToPass += gradeToUpdate + ",";
			}

			query = "UPDATE student SET courses = ?, grades = ? WHERE studentID = ?";
			queryParams.clear();
			queryParams.add(courseToPass);
			queryParams.add(gradeToPass);
			queryParams.add((String) studentComboBox.getSelectedItem());
			SQLInstance.runUpdate(query, queryParams);
			SQLInstance.getMyConn().close();
			System.out.println("Connection terminated");
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Checks if grade field is empty
	 * @return false if empty, true otherwise
	 */
	private boolean checkGradeEmpty() {
		if (gradeField.getText().isEmpty()) {
			gradeField.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		}
		return true;
	}

	/**
	 * Check if selected student is valid
	 * @return
	 */
	private boolean checkValidStudent() {
		if (studentComboBox.getSelectedIndex() == 0) {
			studentComboBox.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		} else {
			studentComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		return true;
	}
	
	/**
	 * Check if selected course code is selected
	 * @return
	 */
	private boolean checkSelectedCourseCode() {
		if (courseComboBox.getSelectedIndex() == 0) {
			courseComboBox.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		} else {
			courseComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		return true;
	}
}
