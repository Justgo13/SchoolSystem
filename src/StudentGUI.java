import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class StudentGUI implements ActionListener {

    /**
     * JButtons
     */
    private JButton registerCourse;
    private JButton unregister;
    private JButton showCourses;
    private JButton showFees;
    private JButton getStudentInfo;
	private JFrame frame;
	private Container container;
	private JPanel studentPanel;
    
    private String studentID;

    /**
     * Creates student GUI
     */
    public StudentGUI (String studentID) {
    	initStudentGUI();
    	this.studentID = studentID;
    }
    
    public void initStudentGUI() {
        frame = new JFrame();
        container = frame.getContentPane();

        studentPanel = new JPanel(new GridLayout(4,1));
        container.add(studentPanel);

        registerCourse = new JButton("REGISTER COURSE");
        unregister = new JButton("UNREGISTER COURSE");
        showCourses = new JButton("SHOW COURSES");
        showFees = new JButton("SHOW FEES");
        getStudentInfo = new JButton("GET STUDENT INFO");

        registerCourse.setFocusPainted(false);
        unregister.setFocusPainted(false);
        showFees.setFocusPainted(false);
        showCourses.setFocusPainted(false);
        getStudentInfo.setFocusPainted(false);

        registerCourse.setFont(new Font("Arial", Font.BOLD, 40));
        unregister.setFont(new Font("Arial", Font.BOLD, 40));
        showFees.setFont(new Font("Arial", Font.BOLD, 40));
        showCourses.setFont(new Font("Arial", Font.BOLD, 40));
        getStudentInfo.setFont(new Font("Arial", Font.BOLD, 40));

        studentPanel.add(registerCourse);
        studentPanel.add(unregister);
        studentPanel.add(showCourses);
        studentPanel.add(showFees);
        
        registerCourse.addActionListener(this);
        unregister.addActionListener(this);
        showCourses.addActionListener(this);
        showFees.addActionListener(this);

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

        if (button.equals(registerCourse)) {
        	new RegisterCourseGUI(studentID);
        } else if (button.equals(unregister)) {
        	new UnregisterCourseGUI(studentID);
        } else if (button.equals(showCourses)) {
        	String[] studentCourses = null;
        	String[] studentGrades = null;
        	try {
	        	String courseInfoString = "";
	            // grabs course string
	 			MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT courses FROM student WHERE studentID = ?");
	 			MainRun.myStmt.setString(1, studentID);
	 			MainRun.myRs = MainRun.myStmt.executeQuery();
	 			
	 			if (MainRun.myRs.next()) {
	 				studentCourses = MainRun.myRs.getString("courses").split(",");
	 			}
	 			
	 			// grabs grades string
	 			MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT grades FROM student WHERE studentID = ?");
	 			MainRun.myStmt.setString(1, studentID);
	 			MainRun.myRs = MainRun.myStmt.executeQuery();
	 			
	 			if (MainRun.myRs.next()) {
	 				studentGrades = MainRun.myRs.getString("grades").split(",");
	 			}
	 			
	 			for (int i = 0; i < studentCourses.length; i++) {
	 				if (studentCourses[i].isEmpty()) {
	 					continue;
	 				}
	 				courseInfoString += studentCourses[i] + ": " + studentGrades[i] + "\n";
	 			}
	 			
	            if (courseInfoString.isEmpty()) {
	            	JOptionPane.showMessageDialog(studentPanel, "NO COURSES REGISTERED");
	            } else {
	            	JOptionPane.showMessageDialog(studentPanel, courseInfoString);
	            }
        	} catch (SQLException err) {
        		err.getStackTrace();
        	}
        } else if (button.equals(showFees)) {
        	try {
	        	MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT tuitionFee FROM student WHERE studentID = ?");
	        	MainRun.myStmt.setString(1, studentID);
	        	MainRun.myRs = MainRun.myStmt.executeQuery();
	        	
	        	if (MainRun.myRs.next()) {
	        		JOptionPane.showMessageDialog(studentPanel, "Your fee is $" + MainRun.myRs.getString("tuitionFee"));
	        	}
        	} catch (SQLException error) {
        		error.getStackTrace();
        	}
        }
    }
}
