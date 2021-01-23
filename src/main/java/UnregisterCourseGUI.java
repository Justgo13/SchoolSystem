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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * Panel for adding courses to students
 * @author jgao2
 *
 */
public class UnregisterCourseGUI implements ActionListener{
	private String studentID;
	private JFrame frame;
	private Container container;
	private JPanel unregisterPanel;
	private JButton unregisterButton;
	private JButton cancelButton;
	private GridBagConstraints constraints;
	private JComboBox<?> courseComboBox;
	private ArrayList<String> courseNames;
	private String query;
	private ArrayList<String> queryParams;
	private ResultSet queryResult;
	private SQLQuery SQLInstance;
	public UnregisterCourseGUI(String studentID) {
		this.studentID = studentID;
		query = "";
		queryParams = new ArrayList<>();
		queryResult = null;
		SQLInstance = null;
		initUnregisterCourse();
	}
	private void initUnregisterCourse() {
		frame = new JFrame();
        container = frame.getContentPane();

        unregisterPanel = new JPanel(new GridBagLayout());
        container.add(unregisterPanel);
        
        courseNames = new ArrayList<>();
        courseNames.add("Choose course");

		SQLInstance = new SQLQuery();
		query = "SELECT courses FROM student WHERE studentID = ?";
		queryParams.clear();
		queryParams.add(studentID);
		queryResult = SQLInstance.runQuery(query, queryParams);
        try {
			if (queryResult.next()) {
				String [] studentCourses = queryResult.getString("courses").split(",");
				List<String> studentCourseList = Arrays.asList(studentCourses);
				ArrayList<String> courses = new ArrayList<String>(studentCourseList);
				
				courses.forEach(course -> courseNames.add(course));
			}
			SQLInstance.closeSQLConnection();
		} catch (SQLException e) {
			e.getStackTrace();
		}
        
        courseComboBox = new JComboBox<>(courseNames.toArray());
        
		// create button
		unregisterButton = new JButton("UNREGISTER COURSE");
		cancelButton = new JButton("CANCEL");
		
		constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0,5,0,5);
		
		unregisterPanel.add(courseComboBox, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		unregisterPanel.add(unregisterButton, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		unregisterPanel.add(cancelButton, constraints);
		
		// adding actionListener
		unregisterButton.addActionListener(this);
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
		
		if (button.equals(unregisterButton)) {
			if (checkCourseCodeValid()) {
				removeCourse((String) courseComboBox.getSelectedItem());
				frame.dispose();
			} else {
				String errorList = "Valid course selection: " + checkCourseCodeValid();
				JOptionPane.showMessageDialog(unregisterPanel, errorList);
			}
		} else if (button.equals(cancelButton)) {
			frame.dispose();
		}	
	}
	/**
	 * Adds course to the list of student courses
	 */
	private void removeCourse(String courseCode) {
		ArrayList<String> courses = new ArrayList<>();
		ArrayList<String> grades = new ArrayList<>();
		SQLInstance = new SQLQuery();
		query = "SELECT courses FROM student WHERE studentID = ?";
		queryParams.clear();
		queryParams.add(studentID);
		queryResult = SQLInstance.runQuery(query, queryParams);
		try {
			if (queryResult.next()) {
				String [] studentCourses = queryResult.getString("courses").split(",");
				List<String> studentCourseList = Arrays.asList(studentCourses);
				courses = new ArrayList<String>(studentCourseList);
//				if (queryResult.getString("courses") == null) {
//					courses = new ArrayList<String>();
//				} else {
//					String [] studentCourses = MainRun.myRs.getString("courses").split(",");
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
				String [] studentGrades = queryResult.getString("grades").split(",");
				List<String> studentGradeList = Arrays.asList(studentGrades);
				grades = new ArrayList<>(studentGradeList);
//				if (gradeResultSet.getString("grades") == null) {
//					grades = new ArrayList<String>();
//				} else {
//					String [] studentGrades = gradeResultSet.getString("grades").split(",");
//					List<String> studentGradeList = Arrays.asList(studentGrades);
//					grades = new ArrayList<String>(studentGradeList);
//				}
			}
			int courseCodeIndex = courses.indexOf(courseCode);
			courses.remove(courseCode);
			grades.remove(courseCodeIndex);
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
				queryParams.add(String.valueOf(Integer.parseInt(queryResult.getString("tuitionFee"))-500));
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
	private boolean checkCourseCodeValid() {
		if (courseComboBox.getSelectedIndex() == 0) {
			courseComboBox.setBorder(BorderFactory.createLineBorder(Color.RED));
			return false;
		} else {
			courseComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		return true;
	}
}
