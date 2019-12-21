import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

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
    private JTextField studentID;
    private JTextField courseCode;

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
        studentID = new JTextField();
        courseCode = new JTextField();

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
        Object[] registerUnregister = {"Student ID", studentID, "Course code", courseCode};
        Collection collection = studentHashMap.values();
        if (button == register) {
            JOptionPane.showMessageDialog(null, registerUnregister);
            for (Object studentCourse : collection) {
                Student student = (Student) studentCourse;
                if (student.getStudentID() == Integer.parseInt(studentID.getText())) {
                    student.addCourse(courseCode.getText());
                }
            }
        } else if (button == unregister) {
            JOptionPane.showMessageDialog(null, registerUnregister);
            for (Object studentCourse : collection) {
                Student student = (Student) studentCourse;
                if (student.getStudentID() == Integer.parseInt(studentID.getText())) {
                    student.removeCourse(courseCode.getText());
                }
            }
        } else if (button == showCourses) {
            for (Object studentCourse : collection) {
                Student student = (Student) studentCourse;
                if (student.getStudentID() == Integer.parseInt(studentID.getText())) {
                    JOptionPane.showMessageDialog(null, student.getCourse());
                }
            }
        } else if (button == showFees) {
            for (Object studentCourse : collection) {
                Student student = (Student) studentCourse;
                if (student.getStudentID() == Integer.parseInt(studentID.getText())) {
                    JOptionPane.showMessageDialog(null, student.getFees());
                }
            }
        } else if (button == getStudentInfo) {
            for (Object studentCourse : collection) {
                Student student = (Student) studentCourse;
                if (student.getStudentID() == Integer.parseInt(studentID.getText())) {
                    JOptionPane.showMessageDialog(null, student.toString());
                }
            }
        }
    }
}
