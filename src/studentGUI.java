import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class studentGUI extends Login implements ActionListener {

    /**
     * JButtons
     */
    private JButton register;
    private JButton unregister;
    private JButton showCourses;
    private JButton showFees;
    private JButton getStudentInfo;

    /**
     * JTextField for registering course
     */
    private static JTextField courseCode;

    /**
     * Collections
     */
    private static boolean notFilled;
    private static Collection<Student> studentCollection;
    private static HashMap<String, String> courses;
    private static Student student;

    /**
     * Creates student GUI
     */
    public studentGUI () {
        super();
        JFrame frame = new JFrame();
        Container container = frame.getContentPane();

        JPanel studentPanel = new JPanel(new GridLayout(4,1));
        container.add(studentPanel);

        register = new JButton("REGISTER COURSE");
        unregister = new JButton("UNREGISTER COURSE");
        showCourses = new JButton("SHOW COURSES");
        showFees = new JButton("SHOW FEES");
        getStudentInfo = new JButton("GET STUDENT INFO");

        register.setFocusPainted(false);
        unregister.setFocusPainted(false);
        showFees.setFocusPainted(false);
        showCourses.setFocusPainted(false);
        getStudentInfo.setFocusPainted(false);

        register.setFont(new Font("Arial", Font.BOLD, 24));
        unregister.setFont(new Font("Arial", Font.BOLD, 24));
        showFees.setFont(new Font("Arial", Font.BOLD, 24));
        showCourses.setFont(new Font("Arial", Font.BOLD, 24));
        getStudentInfo.setFont(new Font("Arial", Font.BOLD, 24));

        studentPanel.add(register);
        studentPanel.add(unregister);
        studentPanel.add(showCourses);
        studentPanel.add(showFees);
        studentPanel.add(getStudentInfo);

        register.addActionListener(this);
        unregister.addActionListener(this);
        showCourses.addActionListener(this);
        showFees.addActionListener(this);
        getStudentInfo.addActionListener(this);

        // init TextField
        courseCode = new JTextField();

        studentCollection = studentHashMap.values();

        // finds the correct student in Collection
        for (Student student : studentCollection) {
            if (student.getStudentID() == (Integer) registeredStudentID.getSelectedItem()) {
                this.student = student;
            }
        }

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,200);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        JButton button = (JButton) o;
        Object[] registerUnregister = {"Course code", courseCode};

        if (button == register) {
            notFilled = true;
            courseCode.setBorder(null);
            courseCode.setText(null);

            while(notFilled) {
                JOptionPane.showMessageDialog(null, registerUnregister);
                if (courseCode.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Fill the course field");
                    courseCode.setBorder(redBorder);
                    courseCode.setText(null);
                } else {
                    courses = student.getCourse();
                    if (courses.containsKey(courseCode.getText())) {
                        JOptionPane.showMessageDialog(null, "You have already registered for the course: " + courseCode.getText());
                        notFilled = false;
                    } else {
                        student.addCourse(courseCode.getText());
                        JOptionPane.showMessageDialog(null, "You have registered for " + courseCode.getText());
                        notFilled = false;
                    }
                }
            }
        } else if (button == unregister) {
            notFilled = true;
            courseCode.setBorder(null);
            courseCode.setText(null);
            while(notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, registerUnregister);
                    courses = student.getCourse();
                    if (!courses.containsKey(courseCode.getText())) {
                        JOptionPane.showMessageDialog(null, "Course: " + courseCode.getText() + " is not a registered course");
                        notFilled = false;
                    } else {
                        student.removeCourse(courseCode.getText());
                        JOptionPane.showMessageDialog(null, "You have unregistered from " + courseCode.getText());
                        notFilled = false;
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    courseCode.setBorder(redBorder);
                }
            }
        } else if (button == showCourses) {
            courses = student.getCourse();
            String courseInfoString = "";
            for (Map.Entry<String, String> entry : courses.entrySet()) {
                courseInfoString += entry.getKey() + ": " + entry.getValue() + "\n";
            }
            JOptionPane.showMessageDialog(null, courseInfoString);
        } else if (button == showFees) {
            JOptionPane.showMessageDialog(null, "Your fee is $" + student.getFees());
        } else if (button == getStudentInfo) {
            JOptionPane.showMessageDialog(null, student.toString());
        }
    }
}
