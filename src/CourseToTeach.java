import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * GUI panel for adding a course to teach for professors
 * @author jgao2
 *
 */
public class CourseToTeach implements ActionListener{
	private JFrame frame;
	private Container contentPane;
	private JPanel panel;
	private JButton applyButton;
	private JButton cancelButton;
	private JLabel courseCodeLabel;
	private JTextField courseCodeField;
	private String profID;

	public CourseToTeach(String profID) {
		this.profID = profID;
		initCourseToTeach();
	}
	
	public void initCourseToTeach() {
		frame = new JFrame();
		contentPane = frame.getContentPane();
		panel = new JPanel(new GridBagLayout());
		contentPane.add(panel);
		
		// apply and cancel button
		applyButton = new JButton("Apply");
		cancelButton = new JButton("Cancel");
		applyButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		courseCodeLabel = new JLabel("Course Taught Code:");
		courseCodeField = new JTextField();
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 0.0;
		constraints.weighty = 1.0;
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(0,5,0,5);

		panel.add(courseCodeLabel, constraints);
		
		constraints.anchor = GridBagConstraints.WEST;
		constraints.weightx = 1.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		panel.add(courseCodeField, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		
		panel.add(applyButton, constraints);
		
		constraints.gridy = 2;
		panel.add(cancelButton, constraints);
		
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		JButton button = (JButton) o;
		
		if (button.equals(applyButton)) {
			if (!checkAlreadyAdded()) {
				addCourseTaught();
				frame.dispose();
			} else {
				String errorList = "Course already added: " + checkAlreadyAdded();
				JOptionPane.showMessageDialog(contentPane, errorList);
			}
		} else if (button.equals(cancelButton)) {
			frame.dispose();
		}
		
	}
	
	/**
	 * Check if course taught is already added
	 * @return
	 */
	private boolean checkAlreadyAdded() {
		ArrayList<String> courseTaught = null;
		try {
			MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT courseTaught FROM professor WHERE professorID = ?");
			MainRun.myStmt.setString(1, profID);
			MainRun.myRs = MainRun.myStmt.executeQuery();
			
			if (MainRun.myRs.next()) {
				if (MainRun.myRs.getString("courseTaught") == null) {
					courseTaught = new ArrayList<String>();
				} else {
					String [] professorCourses = MainRun.myRs.getString("courseTaught").split(",");
					List<String> professorCourseList = Arrays.asList(professorCourses);
					courseTaught = new ArrayList<String>(professorCourseList);
					
					for (String course : courseTaught) {
						if (course.equals(courseCodeField.getText())) {
							courseCodeField.setBorder(BorderFactory.createLineBorder(Color.RED));
							return true;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return false;
	}
		

	/** 
	 * Add course taught
	 */
	private void addCourseTaught() {
		ArrayList<String> courseTaught = null;
		try {
			MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT courseTaught FROM professor WHERE professorID = ?");
			MainRun.myStmt.setString(1, profID);
			MainRun.myRs = MainRun.myStmt.executeQuery();
			
			if (MainRun.myRs.next()) {
				if (MainRun.myRs.getString("courseTaught") == null) {
					courseTaught = new ArrayList<String>();
				} else {
					String [] course = MainRun.myRs.getString("courseTaught").split(",");
					List<String> professorCourseTaughtList = Arrays.asList(course);
					courseTaught = new ArrayList<String>(professorCourseTaughtList);
				}
				courseTaught.add(courseCodeField.getText());
				
				String courseToPass = "";
				for (String courseToUpdate : courseTaught) {
					if (courseToUpdate.isEmpty()) {
						continue;
					}
					courseToPass += courseToUpdate + ",";
				}
				
				MainRun.myStmt = MainRun.myConn.prepareStatement("UPDATE professor SET courseTaught = ? WHERE professorID = ?");
				MainRun.myStmt.setString(1, courseToPass);
				MainRun.myStmt.setString(2, profID);
				MainRun.myStmt.executeUpdate();
				}
			} catch (SQLException e1) {
				e1.getStackTrace();
			}
		}
	}
