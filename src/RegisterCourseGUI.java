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
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Panel for adding courses to students
 * @author jgao2
 *
 */
public class RegisterCourseGUI implements ActionListener{
	private String studentID;
	private JFrame frame;
	private Container container;
	private JPanel registerPanel;
	private JLabel courseCodeLabel;
	private JTextField courseCodeField;
	private JButton registerButton;
	private JButton cancelButton;
	private GridBagConstraints constraints;
	private String query;
	private ArrayList<String> queryParams;
	private ResultSet queryResult;
	private SQLQuery SQLInstance;

	public RegisterCourseGUI(String studentID) {
		this.studentID = studentID;
		query = "";
		queryParams = new ArrayList<>();
		queryResult = null;
		SQLInstance = null;
		initRegisterCourse();
	}
	private void initRegisterCourse() {
		frame = new JFrame();
        container = frame.getContentPane();

        registerPanel = new JPanel(new GridBagLayout());
        container.add(registerPanel);
        
        courseCodeLabel = new JLabel("Course Code");
        courseCodeField = new JTextField();
        
		// create button
		registerButton = new JButton("REGISTER COURSE");
		cancelButton = new JButton("CANCEL");
		
		constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0,5,0,5);
		
		registerPanel.add(courseCodeLabel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		registerPanel.add(courseCodeField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		registerPanel.add(registerButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 3;
		registerPanel.add(cancelButton, constraints);
		
		// adding actionListener
		registerButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
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
		
		if (button.equals(registerButton)) {
			if (checkCourseCodeFilled() && checkAlreadyRegistered()) {
				addCourse(courseCodeField.getText());
				frame.dispose();
			} else {
				String errorList = "Course code not empty: " + checkCourseCodeFilled() + "\n" +
				   		   		   "Course unique: " + checkAlreadyRegistered();
				JOptionPane.showMessageDialog(registerPanel, errorList);
			}
		} else if (button.equals(cancelButton)) {
			frame.dispose();
		}	
	}
	
	/**
	 * Checks if the course has already been registered
	 * @return true if course is not registerd, false otherwise
	 */
	private boolean checkAlreadyRegistered() {
		SQLInstance = new SQLQuery();
		query = "SELECT courses FROM student WHERE studentID = ?";
		queryParams.clear();
		queryParams.add(studentID);
		queryResult = SQLInstance.runQuery(query, queryParams);
		try {
			if (queryResult.next()) {
				String [] studentCourses = queryResult.getString("courses").split(",");
				List<String> studentCourseList = Arrays.asList(studentCourses);
				ArrayList<String> courses = new ArrayList<>(studentCourseList);
				
				if (courses.contains(courseCodeField.getText())) {
					courseCodeField.setBorder(BorderFactory.createLineBorder(Color.RED));
					return false;
				}
			}
			SQLInstance.closeSQLConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Adds course to the list of student courses
	 */
	private void addCourse(String courseCode) {
		ArrayList<String> courses = new ArrayList<>();
		ArrayList<String> grades = new ArrayList<>();
		SQLInstance = new SQLQuery();
		query = "SELECT courses FROM student WHERE studentID = ?";
		queryParams.clear();
		queryParams.add(studentID);
		queryResult = SQLInstance.runQuery(query, queryParams);
		try {
			if (queryResult.next()) {
				String[] studentCourses = queryResult.getString("courses").split(",");
				List<String> studentCourseList = Arrays.asList(studentCourses);
				courses = new ArrayList<String>(studentCourseList);
//				if (queryResult.getString("courses") == null) {
//					courses = new ArrayList<String>();
//				} else {
//					String[] studentCourses = MainRun.myRs.getString("courses").split(",");
//					List<String> studentCourseList = Arrays.asList(studentCourses);
//					courses = new ArrayList<String>(studentCourseList);
//				}
			}
			// grabs grades string
			query = "SELECT grades FROM student WHERE studentID = ?";
			queryParams.clear();
			queryParams.add(studentID);
			queryResult = SQLInstance.runQuery(query, queryParams);
			if (queryResult.next()) {
				String[] studentGrades = queryResult.getString("grades").split(",");
				List<String> studentGradeList = Arrays.asList(studentGrades);
				grades = new ArrayList<>(studentGradeList);
//				if (queryResult.getString("grades") == null) {
//					grades = new ArrayList<String>();
//				} else {
//					String[] studentGrades = gradeResultSet.getString("grades").split(",");
//					List<String> studentGradeList = Arrays.asList(studentGrades);
//					grades = new ArrayList<String>(studentGradeList);
//				}
			}
			
			courses.add(courseCode);
			grades.add("EMPTY");
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
			
			// gets current tuitionFee
			query = "SELECT tuitionFee FROM student WHERE studentID = ?";
			queryParams.clear();
			queryParams.add(studentID);
			queryResult = SQLInstance.runQuery(query, queryParams);

			if (queryResult.next()) {
				query = "UPDATE student SET tuitionFee = ? WHERE studentID = ?";
				queryParams.clear();
				queryParams.add(String.valueOf(Integer.parseInt(queryResult.getString("tuitionFee"))+500));
				queryParams.add(studentID);
				SQLInstance.runUpdate(query, queryParams);
			}
			query = "UPDATE student SET courses = ?, grades = ? WHERE studentID = ?";
			queryParams.clear();
			queryParams.add(courseToPass);
			queryParams.add(gradeToPass);
			queryParams.add(studentID);
			SQLInstance.runUpdate(query, queryParams);
			SQLInstance.closeSQLConnection();
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * Checks if course code is empty
	 * @return true if course code is fill, false otherwise
	 */
	private boolean checkCourseCodeFilled() {
		if (courseCodeField.getText().isEmpty()) {
			courseCodeField.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		}
		return true;
	}
}
