package StudentMVC;

import MongoQuery.MongoQueryInterface;
import org.bson.types.ObjectId;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class StudentFrame extends JFrame implements StudentView {
    private final ObjectId studentID;
    private JList<String> coursesTaken;
    private JButton addCourse;
    private JButton removeCourse;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField tuitionFeeField;
    private DefaultListModel<String> coursesTakenModel;

    public StudentFrame(ObjectId studentID) throws HeadlessException {
        this.studentID = studentID;
        createStudentGUI();
        updateStudentInformation();
    }

    private void createStudentGUI() {
        StudentModel studentModel = new StudentModel();
        StudentController studentController = new StudentController(studentModel);
        studentModel.addStudentView(this);

        coursesTakenModel = new DefaultListModel<>();
        coursesTaken = new JList<>(coursesTakenModel);

        // creating main panels
        GridBagConstraints c = createGenericGridBagConstraints();
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        JPanel studentInformationPanel = createStudentInformationPanel();
        JPanel studentCoursePanel = createStudentCoursePanel();

        contentPane.add(studentInformationPanel, c);

        c.gridy = 1;
        contentPane.add(studentCoursePanel, c);

        setupActionListeners(studentController);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setSize(800, 600);
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private JPanel createStudentInformationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = createGenericGridBagConstraints();
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Student Information"));
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        tuitionFeeField = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        JPanel firstNamePanel = createGenericTextFieldPanel(firstNameField, "First Name", c);
        JPanel lastNamePanel = createGenericTextFieldPanel(lastNameField, "Last Name", c);
        JPanel tuitionFeePanel = createGenericTextFieldPanel(tuitionFeeField, "Tuition Fee", c);
        panel.add(firstNamePanel, c);
        c.gridy = 1;
        panel.add(lastNamePanel, c);
        c.gridy = 2;
        panel.add(tuitionFeePanel, c);
        return panel;
    }

    private JPanel createStudentCoursePanel() {
        JPanel studentCoursePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = createGenericGridBagConstraints();
        studentCoursePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Current Courses"));

        JScrollPane courseTaughtScroll = new JScrollPane(coursesTaken);
        c.gridwidth = 2;
        studentCoursePanel.add(courseTaughtScroll, c);

        addCourse = new JButton("Add Course");
        removeCourse = new JButton("Remove course");

        c.gridwidth = 1;
        c.gridy = 1;
        studentCoursePanel.add(addCourse, c);

        c.gridx = 1;
        studentCoursePanel.add(removeCourse, c);

        return studentCoursePanel;
    }

    private JPanel createGenericTextFieldPanel(JTextField textField, String textBoxTitle, GridBagConstraints c) {
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), textBoxTitle));
        textField.setEditable(false);
        textPanel.add(textField, c);
        return textPanel;
    }

    private GridBagConstraints createGenericGridBagConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        return c;
    }

    private void setupActionListeners(StudentController studentController) {
        coursesTaken.addListSelectionListener(studentController);
        coursesTaken.setName(StudentEnum.COURSE_LIST_NAME.toString());
        addCourse.addActionListener(studentController);
        addCourse.setActionCommand(StudentEnum.ADD_COURSE.toString());
        removeCourse.addActionListener(studentController);
        removeCourse.setActionCommand(StudentEnum.REMOVE_COURSE.toString());
    }

    private void updateStudentInformation() {
        String firstName = MongoQueryInterface.getStudentFirstName(studentID);
        String lastName = MongoQueryInterface.getStudentLastName(studentID);
        Long tuitionFee = MongoQueryInterface.getStudentTuitionFee(studentID);

        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        tuitionFeeField.setText(String.valueOf(tuitionFee));
        coursesTakenModel.clear();

        Map<String, Long> studentCourses = MongoQueryInterface.getStudentCourses(studentID);
        // add courses for the first time or re-add them after an add course or remove course action
        Set<Map.Entry<String, Long>> entrySet = studentCourses.entrySet();
        ArrayList<Map.Entry<String, Long>> allStudentCourses = new ArrayList<>(entrySet);

        for (Map.Entry<String, Long> course : allStudentCourses)
        {
            coursesTakenModel.addElement(course.getKey() + " - " + course.getValue());
        }

    }

    @Override
    public void handleAddCourseTaken() {
        String courseToAdd = JOptionPane.showInputDialog(this, "Enter course code", "Add course taken", JOptionPane.PLAIN_MESSAGE);
        if (courseToAdd != null) {
            MongoQueryInterface.addStudentCourse(studentID, courseToAdd);
            updateStudentInformation();
        }
    }

    @Override
    public void handleRemoveCourseTaken() {
        String courseGradeString = coursesTaken.getSelectedValue();
        String[] courseGradeBreakdown = courseGradeString.split(" - ");
        String courseName = courseGradeBreakdown[0];
        Long grade = Long.parseLong(courseGradeBreakdown[1]);
        MongoQueryInterface.removeStudentCourse(studentID, courseName, grade);
        updateStudentInformation();
        coursesTaken.setSelectedValue(null, false);
        removeCourse.setEnabled(false);
    }
}
