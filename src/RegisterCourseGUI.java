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
	public RegisterCourseGUI(String studentID) {
		this.studentID = studentID;
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
		try {
			MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT courses FROM student WHERE studentID = ?");
			MainRun.myStmt.setString(1, studentID);
			MainRun.myRs = MainRun.myStmt.executeQuery();
			
			if (MainRun.myRs.next()) {
				String [] studentCourses = MainRun.myRs.getString("courses").split(",");
				List<String> studentCourseList = Arrays.asList(studentCourses);
				ArrayList<String> courses = new ArrayList<String>(studentCourseList);
				
				if (courses.contains(courseCodeField.getText())) {
					courseCodeField.setBorder(BorderFactory.createLineBorder(Color.RED));
					return false;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * Adds course to the list of student courses
	 */
	private void addCourse(String courseCode) {
		ArrayList<String> courses = null;
		ArrayList<String> grades = null;
		try {
			// grabs course string
			MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT courses FROM student WHERE studentID = ?");
			MainRun.myStmt.setString(1, studentID);
			MainRun.myRs = MainRun.myStmt.executeQuery();
			
			if (MainRun.myRs.next()) {
				if (MainRun.myRs.getString("courses") == null) {
					courses = new ArrayList<String>();
				} else {
					String[] studentCourses = MainRun.myRs.getString("courses").split(",");
					List<String> studentCourseList = Arrays.asList(studentCourses);
					courses = new ArrayList<String>(studentCourseList);
				}
			}
			// grabs grades string
			MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT grades FROM student WHERE studentID = ?");
			MainRun.myStmt.setString(1, studentID);
			ResultSet gradeResultSet = MainRun.myStmt.executeQuery();
			
			if (gradeResultSet.next()) {
				if (gradeResultSet.getString("grades") == null) {
					grades = new ArrayList<String>();
				} else {
					String[] studentGrades = gradeResultSet.getString("grades").split(",");
					List<String> studentGradeList = Arrays.asList(studentGrades);
					grades = new ArrayList<String>(studentGradeList);
				}
			}
			
			courses.add(courseCode);
			grades.add("CUR");
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
			MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT tuitionFee FROM student WHERE studentID = ?");
			MainRun.myStmt.setString(1, studentID);
			ResultSet tuitionFee = MainRun.myStmt.executeQuery();
			
			if (tuitionFee.next()) {
				MainRun.myStmt = MainRun.myConn.prepareStatement("UPDATE student SET tuitionFee = ? WHERE studentID = ?");
				MainRun.myStmt.setInt(1, Integer.parseInt(tuitionFee.getString("tuitionFee"))+500);
				MainRun.myStmt.setString(2, studentID);
				MainRun.myStmt.executeUpdate();
			}
			
			MainRun.myStmt = MainRun.myConn.prepareStatement("UPDATE student SET courses = ?, grades = ? WHERE studentID = ?");
			MainRun.myStmt.setString(1, courseToPass);
			MainRun.myStmt.setString(2, gradeToPass);
			MainRun.myStmt.setString(3, studentID);
			MainRun.myStmt.executeUpdate();
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
