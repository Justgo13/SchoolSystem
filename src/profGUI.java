import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class profGUI extends Login implements ActionListener {

    /**
     * JButton for teacher
     */
    private JButton updateGrade;
    private JButton getTeacherInfo;

    /**
     * TextField for updating grades
     */
    private JTextField studentID;
    private JTextField courseID;
    private JTextField grade;

    /**
     * prof info field
     */
    private JTextField profInfoField;

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

        Collection collection = studentHashMap.values();
        Object[] updateGradeField = {"Student ID", studentID, "Course ID", courseID, "Grade", grade};
        Object[] profField = {"Employee ID", profInfoField};

        boolean notFilled = true;

        if (button == updateGrade) {
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, updateGradeField);
                    for (Object studentGrade : collection) {
                        Student student = (Student) studentGrade;
                        if (student.getStudentID() == Integer.parseInt(studentID.getText())) {
                            student.setGrade(courseID.getText(), grade.getText());
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
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, profField);
                    for (Object profInfo : collection) {
                        Professor prof = (Professor) profInfo;
                        if (prof.getID() == Integer.parseInt(profInfoField.getText())) {
                            JOptionPane.showMessageDialog(null, prof.toString());
                            notFilled = false;
                        }
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    profInfoField.setBorder(redBorder);
                }
            }
        }
    }
}
