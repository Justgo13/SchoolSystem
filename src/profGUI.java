import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;

public class profGUI extends Login implements ActionListener {

    /**
     * JButton for teacher
     */
    private JButton updateGrade;
    private JButton getTeacherInfo;

    /**
     * TextField for updating grades
     */
    private static JTextField studentID;
    private static JTextField courseID;
    private static JTextField grade;

    /**
     * prof info field
     */
    private static JTextField profInfoField;

    /**
     * Collections
     */
    private static boolean notFilled;
    private static Collection<Professor> professorCollection;
    private static Collection<Student> studentCollection;
    private Professor professor;
    private Student student;
    private HashMap<String, String> studentCourse;

    /**
     * Create a prof GUI
     */
    public profGUI(){
        super();
        JFrame frame = new JFrame();
        Container container = frame.getContentPane();

        JPanel profPanel = new JPanel(new GridLayout(4,1));
        container.add(profPanel);

        updateGrade = new JButton("UPDATE GRADE");
        getTeacherInfo = new JButton("GET TEACHER INFO");

        updateGrade.setFocusPainted(false);
        getTeacherInfo.setFocusPainted(false);

        updateGrade.setFont(new Font("Arial", Font.BOLD, 24));
        getTeacherInfo.setFont(new Font("Arial", Font.BOLD, 24));

        profPanel.add(updateGrade);
        profPanel.add(getTeacherInfo);

        updateGrade.addActionListener(this);
        getTeacherInfo.addActionListener(this);

        studentID =  new JTextField();
        courseID = new JTextField();
        grade = new JTextField();
        profInfoField = new JTextField();

        professorCollection = professorHashMap.values();

        // gets specific prof
        for (Professor professor : professorCollection) {
            if (professor.getID() == Integer.parseInt(usernameField.getText())) {
                this.professor = professor;
            }
        }
        studentCollection = studentHashMap.values();

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

        Object[] updateGradeField = {"Student ID", studentID, "Course ID", courseID, "Grade", grade};
        Object[] profField = {"Employee ID", profInfoField};

        if (button == updateGrade) {
            notFilled = true;
            studentID.setBorder(null);
            studentID.setText(null);
            courseID.setText(null);
            courseID.setBorder(null);
            grade.setBorder(null);
            grade.setText(null);
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, updateGradeField);

                    student = null;
                    for (Student student : studentCollection) {
                        if (student.getStudentID() == Integer.parseInt(studentID.getText())) {
                            this.student = student;
                        }
                    }

                    if (student == null) {
                        JOptionPane.showMessageDialog(null, "ID does not match any student in database");
                        notFilled = false;
                    } else {
                        studentCourse = student.getCourse();

                        if (studentCourse.containsKey(courseID.getText())) {
                            student.setGrade(courseID.getText(), grade.getText());
                            JOptionPane.showMessageDialog(null, "You have updated " + student.getName() + "'s grade for course: " + courseID.getText() + " to " + grade.getText());
                            notFilled = false;
                        } else {
                            JOptionPane.showMessageDialog(null, "This course has not been register by the student");
                            notFilled = false;
                        }

                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    studentID.setBorder(redBorder);
                    courseID.setBorder(redBorder);
                    grade.setBorder(redBorder);
                }
            }
        } else if (button == getTeacherInfo) {
            JOptionPane.showMessageDialog(null, professor.toString());
        }
    }
}
