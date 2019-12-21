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

        if (button == updateGrade) {
            JOptionPane.showMessageDialog(null, updateGradeField);
            for (Object studentGrade : collection) {
                Student student = (Student) studentGrade;
                if (student.getStudentID() == Integer.parseInt(studentID.getText())) {
                    student.setGrade(courseID.getText(), grade.getText());
                }
            }
        } else if (button == getTeacherInfo) {
            for (Object studentGrade : collection) {
                Student student = (Student) studentGrade;
                if (student.getStudentID() == Integer.parseInt(studentID.getText())) {
                    JOptionPane.showMessageDialog(null, student.toString());
                }
            }
        }
    }
}
