//import java.awt.Color;
//import java.awt.Container;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JTextField;
//import javax.swing.WindowConstants;
//
///**
// * GUI panel for adding a course to teach for professors
// * @author jgao2
// *
// */
//public class CourseToTeach extends JFrame implements ActionListener{
//	private Container contentPane;
//	private JPanel panel;
//	private JButton applyButton;
//	private JButton cancelButton;
//	private JLabel courseCodeLabel;
//	private JTextField courseCodeField;
//	private String profID;
//	private String query;
//	private ArrayList<String> queryParams;
//	private ResultSet queryResult;
//	private SQLQuery SQLInstance;
//
//	public CourseToTeach(String profID) {
//		this.profID = profID;
//		query = "";
//		queryParams = new ArrayList<>();
//		queryResult = null;
//		SQLInstance = null;
//		initCourseToTeach();
//	}
//
//	public void initCourseToTeach() {
//		contentPane = this.getContentPane();
//		panel = new JPanel(new GridBagLayout());
//		contentPane.add(panel);
//
//		// apply and cancel button
//		applyButton = new JButton("Apply");
//		cancelButton = new JButton("Cancel");
//		applyButton.addActionListener(this);
//		cancelButton.addActionListener(this);
//
//		courseCodeLabel = new JLabel("Course Taught Code:");
//		courseCodeField = new JTextField();
//
//		GridBagConstraints constraints = new GridBagConstraints();
//		constraints.fill = GridBagConstraints.HORIZONTAL;
//		constraints.gridx = 0;
//		constraints.gridy = 0;
//		constraints.weightx = 0.0;
//		constraints.weighty = 1.0;
//		constraints.anchor = GridBagConstraints.EAST;
//		constraints.insets = new Insets(0,5,0,5);
//
//		panel.add(courseCodeLabel, constraints);
//
//		constraints.anchor = GridBagConstraints.WEST;
//		constraints.weightx = 1.0;
//		constraints.gridx = 1;
//		constraints.gridy = 0;
//		panel.add(courseCodeField, constraints);
//
//		constraints.gridx = 0;
//		constraints.gridy = 1;
//		constraints.gridwidth = 2;
//
//		panel.add(applyButton, constraints);
//
//		constraints.gridy = 2;
//		panel.add(cancelButton, constraints);
//
//		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        pack();
//        setSize(800,600);
//        setResizable(true);
//        setVisible(true);
//        setLocationRelativeTo(null);
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		Object o = e.getSource();
//		JButton button = (JButton) o;
//
//		if (button.equals(applyButton)) {
//			if (!checkAlreadyAdded()) {
//				addCourseTaught();
//				dispose();
//			} else {
//				String errorList = "Course already added: " + checkAlreadyAdded();
//				JOptionPane.showMessageDialog(contentPane, errorList);
//			}
//		} else if (button.equals(cancelButton)) {
//			dispose();
//		}
//	}
//
//	/**
//	 * Check if course taught is already added
//	 * @return
//	 */
//	private boolean checkAlreadyAdded() {
//		ArrayList<String> courseTaught;
//		SQLInstance = new SQLQuery();
//		query = "SELECT courseTaught FROM professor WHERE professorID = ?";
//		queryParams.clear();
//		queryParams.add(profID);
//		queryResult = SQLInstance.runQuery(query, queryParams);
//		try {
//			if (queryResult.next()) {
//				String [] professorCourses = queryResult.getString("courseTaught").split(",");
//				List<String> professorCourseList = Arrays.asList(professorCourses);
//				courseTaught = new ArrayList<>(professorCourseList);
//
//				for (String course : courseTaught) {
//					if (course.equals(courseCodeField.getText())) {
//						courseCodeField.setBorder(BorderFactory.createLineBorder(Color.RED));
//						return true;
//					}
//				}
////				if (queryResult.getString("courseTaught") == null) {
////					courseTaught = new ArrayList<String>();
////				} else {
////					String [] professorCourses = MainRun.myRs.getString("courseTaught").split(",");
////					List<String> professorCourseList = Arrays.asList(professorCourses);
////					courseTaught = new ArrayList<String>(professorCourseList);
////
////					for (String course : courseTaught) {
////						if (course.equals(courseCodeField.getText())) {
////							courseCodeField.setBorder(BorderFactory.createLineBorder(Color.RED));
////							return true;
////						}
////					}
////				}
//				SQLInstance.getMyConn().close();
//				System.out.println("Connection terminated");
//			}
//		} catch (SQLException e) {
//			e.getStackTrace();
//		}
//		return false;
//	}
//
//
//	/**
//	 * Add course taught
//	 */
//	private void addCourseTaught() {
//		ArrayList<String> courseTaught;
//		SQLInstance = new SQLQuery();
//		query = "SELECT courseTaught FROM professor WHERE professorID = ?";
//		queryParams.clear();
//		queryParams.add(profID);
//		queryResult = SQLInstance.runQuery(query, queryParams);
//		try {
//			if (queryResult.next()) {
//				String [] course = queryResult.getString("courseTaught").split(",");
//				List<String> professorCourseTaughtList = Arrays.asList(course);
//				courseTaught = new ArrayList<>(professorCourseTaughtList);
////				if (MainRun.myRs.getString("courseTaught") == null) {
////					courseTaught = new ArrayList<String>();
////				} else {
////					String [] course = queryResult.getString("courseTaught").split(",");
////					List<String> professorCourseTaughtList = Arrays.asList(course);
////					courseTaught = new ArrayList<>(professorCourseTaughtList);
////				}
//				courseTaught.add(courseCodeField.getText());
//				String courseToPass = "";
//				for (String courseToUpdate : courseTaught) {
//					if (courseToUpdate.isEmpty()) {
//						continue;
//					}
//					courseToPass += courseToUpdate + ",";
//				}
//
//				query = "SELECT courseTaught FROM professor WHERE professorID = ?";
//				queryParams.clear();
//				queryParams.add(courseToPass);
//				queryParams.add(profID);
//				SQLInstance.runUpdate(query, queryParams);
//				SQLInstance.getMyConn().close();
//				System.out.println("Connection terminated");
////				queryResult = MainRun.myConn.prepareStatement("UPDATE professor SET courseTaught = ? WHERE professorID = ?");
////				queryResult.setString(1, courseToPass);
////				queryResult.setString(2, profID);
////				queryResult.executeUpdate();
//				}
//			} catch (SQLException e1) {
//				e1.getStackTrace();
//			}
//		}
//	}
