import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

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
public class UpdateGradeGUI implements dataStorage, ActionListener {
	private JFrame frame;
	private Container contentPane;
	private JPanel gradePanel;
	private JButton applyButton;
	private JButton cancelButton;
	private ArrayList<String> studentNames;
	private JComboBox<Object> studentComboBox;
	private JLabel gradeLabel;
	private JTextField gradeField;
	private GridBagConstraints constraints;
	private JComboBox<?> courseComboBox;
	private ArrayList<String> courseCode;
	private Professor professor;
	public UpdateGradeGUI(Professor professor) {
		this.professor = professor;
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
		
		studentNames = new ArrayList<String>();
		studentNames.add("Choose a student");
		// create JComboBox
		for (Student student : studentCollection) {
			studentNames.add(student.getName());
		}
		
		studentComboBox = new JComboBox<Object>(studentNames.toArray());
		
		// creating salary label and textfield
		gradeLabel = new JLabel("Enter grade");
		gradeField = new JTextField();
		
		// student registered courses to comboBox
		courseCode = new ArrayList<String>();
		courseCode.add("Choose a course code");
		courseCode.add(professor.getCourse());
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
				   		   		   "Grade is valid" + checkValidGrade() + "\n" +
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
		for (Student student : studentCollection) {
			if (student.getName().equals((String) studentComboBox.getSelectedItem())) {
				if (student.getCourse().containsKey(courseComboBox.getSelectedItem())) {
					courseComboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					return true;
				}
			}
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
		for (Student studentObj : studentCollection) {
			if (studentObj.getName().equals(studentName)) {
				studentObj.setGrade((String) courseComboBox.getSelectedItem(), gradeField.getText());
			}
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
		return false;
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
		return false;
	}
}
