import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

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

    /**
     * JTextField for registering course
     */
    private static JTextField courseCode;
    
    private Student student;

    /**
     * Creates student GUI
     */
    public StudentGUI (Student student) {
    	initStudentGUI();
    	this.student = student;
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

        // init TextField
        courseCode = new JTextField();

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
        	new RegisterCourseGUI(student);
        } else if (button.equals(unregister)) {
        	new UnregisterCourseGUI(student);
        } else if (button.equals(showCourses)) {
            String courseInfoString = student.getGrade();
            if (courseInfoString.isEmpty()) {
            	JOptionPane.showMessageDialog(studentPanel, "NO COURSES REGISTERED");
            } else {
            	JOptionPane.showMessageDialog(studentPanel, courseInfoString);
            }
        } else if (button.equals(showFees)) {
            JOptionPane.showMessageDialog(studentPanel, "Your fee is $" + student.getFees());
        }
    }
}
