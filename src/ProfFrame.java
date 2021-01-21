import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class ProfFrame extends JFrame implements ProfView {
    private Container contentPane;
    private JPanel infoPanel;
    private JPanel firstNamePanel;
    private JPanel lastNamePanel;
    private JPanel tuitionFeePanel;
    private JPanel coursesPanel;
    private JPanel profCommandsPanel;
    private JPanel studentIDPanel;
    private JList studentList;
    private JList courseTaughtList;
    private JList courseList;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField tuitionFeeField;
    private JScrollPane courseScroll;
    private JScrollPane courseTaughtScroll;
    private JButton editButton;
    private JButton addCourseToTeach;
    private JButton removeCourseToTeach;
    private DefaultListModel courseModel;
    private String query;
    private ArrayList<String> queryParams;
    private ResultSet queryResult;
    private SQLQuery SQLInstance;
    private String profID;
    private ProfModel profModel;

    public ProfFrame(String profID) {
        profModel = new ProfModel();
        queryParams = new ArrayList<>();
        SQLInstance = new SQLQuery();
        queryResult = null;
        this.profID = profID;
        initProfessorGUI();
    }

    public void initProfessorGUI() {
        ProfController profController = new ProfController(profModel);
        profModel.addProfView(this);

        // creating main panels
        contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), ProfFrameConstants.STUDENT_INFORMATION_BORDER_LABEL.toString()));
        studentIDPanel = new JPanel(new GridBagLayout());
        studentIDPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), ProfFrameConstants.STUDENT_ID_BORDER_LABEL.toString()));
        profCommandsPanel = new JPanel(new GridBagLayout());
        profCommandsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), ProfFrameConstants.COURSE_TAUGHT_BORDER_LABEL.toString()));

        populateStudentID(); // populate the student ID list from database

        populateCourseTaught(); // populate courses taken by student from database

        GridBagConstraints c = new GridBagConstraints();

        // text fields
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        tuitionFeeField = new JTextField();
        firstNamePanel = createGenericTextFieldPanel(firstNameField,ProfFrameConstants.FIRST_NAME_FIELD_LABEL.toString(),c);
        lastNamePanel = createGenericTextFieldPanel(lastNameField,ProfFrameConstants.LAST_NAME_FIELD_LABEL.toString(), c);
        tuitionFeePanel = createGenericTextFieldPanel(tuitionFeeField,ProfFrameConstants.TUITION_FEE_FIELD_LABEL.toString(), c );

        // student course list panel setup
        courseModel = new DefaultListModel();
        courseList = new JList(courseModel);
        courseScroll = new JScrollPane(courseList);
        courseList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        coursesPanel = new JPanel(new GridBagLayout());
        coursesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), ProfFrameConstants.COURSES_BORDER_LABEL.toString()));
        coursesPanel.add(courseScroll, c);

        editButton = new JButton(ProfFrameConstants.EDIT_BUTTON_LABEL.toString());
        editButton.setEnabled(false);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        studentIDPanel.add(studentList, c);

        c.weightx = 0.2;
        contentPane.add(studentIDPanel, c);

        c.weightx = 0.80;
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        infoPanel = createStudentInfoPanel();
        contentPane.add(infoPanel, c);

        // prof commands panel
        addCourseToTeach = new JButton(ProfFrameConstants.ADD_COURSE_LABEL.toString());
        removeCourseToTeach = new JButton(ProfFrameConstants.REMOVE_COURSE_LABEL.toString());
        profCommandsPanel = createCourseTaughtPanel();

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        contentPane.add(profCommandsPanel, c);

        setupActionListener(profController);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setSize(800, 600);
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private JPanel createCourseTaughtPanel() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel profCommandsPanel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        profCommandsPanel.add(addCourseToTeach, c);

        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        profCommandsPanel.add(removeCourseToTeach, c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        courseTaughtScroll = new JScrollPane(courseTaughtList);
        profCommandsPanel.add(courseTaughtScroll, c);
        return profCommandsPanel;
    }

    private JPanel createStudentInfoPanel() {
        GridBagConstraints c = new GridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        panel.add(firstNamePanel, c);
        c.gridy = 1;
        panel.add(lastNamePanel, c);
        c.gridy = 2;
        panel.add(tuitionFeePanel, c);
        c.gridy = 3;
        panel.add(coursesPanel, c);
        c.gridx = 0;
        c.gridy = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(editButton, c);
        return panel;
    }

    private JPanel createGenericTextFieldPanel(JTextField textField, String textBoxTitle, GridBagConstraints c) {
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), textBoxTitle));
        textField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        textPanel.add(textField, c);
        return textPanel;
    }

    private void populateCourseTaught() {
        ArrayList<String> courseTaughtData;
        query = "SELECT courseTaught FROM professor WHERE professorID = ?";
        queryParams.clear();
        queryParams.add(profID);
        queryResult = SQLInstance.runQuery(query, queryParams);
        try {
            if (queryResult.next()) {
                String[] courseTaught = queryResult.getString("courseTaught").split(",");
                List<String> courseList = Arrays.asList(courseTaught);
                courseTaughtData = new ArrayList<>(courseList);
                profModel.setCourseTaughtData(courseTaughtData);
                courseTaughtList = new JList(profModel.getCourseTaughtData().toArray());
                courseTaughtList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
            SQLInstance.getMyConn().close();
            System.out.println("Connection terminated");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    private void populateStudentID() {
        ArrayList<String> studentListData = new ArrayList<>();
        SQLInstance = new SQLQuery();
        query = "SELECT studentID FROM student";
        queryResult = SQLInstance.runQuery(query, queryParams);
        try {
            while (queryResult.next()) {
                studentListData.add(queryResult.getString("studentID"));
                profModel.setStudentListData(studentListData);
                studentList = new JList(profModel.getStudentListData().toArray());
                studentList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
            SQLInstance.getMyConn().close();
            System.out.println("Connection terminated");
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    private void setupActionListener(ProfController profController) {
        courseList.addListSelectionListener(profController);
        courseList.setName(ProfFrameConstants.COURSE_LIST_NAME.toString());
        studentList.addListSelectionListener(profController);
        studentList.setName(ProfFrameConstants.STUDENT_LIST_NAME.toString());
        editButton.addActionListener(profController);
        editButton.setActionCommand(ProfFrameConstants.EDIT_BUTTON_COMMAND.toString());
        courseTaughtList.addListSelectionListener(profController);
        courseTaughtList.setName(ProfFrameConstants.COURSE_TAUGHT_LIST_NAME.toString());
    }

    /**
     * Populate student information fields
     *
     * @param studentID
     */
    private void updateFields(Object studentID) {
        String ID = (String) studentID;
        ArrayList<String> courses = new ArrayList<>();
        ArrayList<String> grades = new ArrayList<>();
        SQLInstance = new SQLQuery();
        query = "SELECT * FROM student WHERE studentID = ?";
        queryParams.clear();
        queryParams.add(ID);
        queryResult = SQLInstance.runQuery(query, queryParams);
        try {
            if (queryResult.next()) {
                firstNameField.setText(queryResult.getString("firstName"));
                lastNameField.setText(queryResult.getString("lastName"));
                tuitionFeeField.setText(queryResult.getString("tuitionFee"));
            }

            // grabs course string
            query = "SELECT courses FROM student WHERE studentID = ?";
            queryParams.clear();
            queryParams.add(ID);
            queryResult = SQLInstance.runQuery(query, queryParams);

            if (queryResult.next()) {
                String[] studentCourses = queryResult.getString("courses").split(",");
                List<String> studentCourseList = Arrays.asList(studentCourses);
                courses = new ArrayList<>(studentCourseList);
            }

            // grabs grades string
            query = "SELECT grades FROM student WHERE studentID = ?";
            queryParams.clear();
            queryParams.add(ID);
            queryResult = SQLInstance.runQuery(query, queryParams);

            if (queryResult.next()) {
                String[] studentGrades = queryResult.getString("grades").split(",");
                List<String> studentGradeList = Arrays.asList(studentGrades);
                grades = new ArrayList<>(studentGradeList);
            }

            for (String course : courses) {
                courses.set(courses.indexOf(course), course + " - " + grades.get(courses.indexOf(course)));
            }
            SQLInstance.getMyConn().close();
            System.out.println("Connection terminated");
            courseModel.clear();
            courseModel.addAll(0, courses);
        } catch (SQLException e) {
            e.getStackTrace();
        }

    }

    @Override
    public void handleShowStudentInfo(Object selectedValue) {
        updateFields(selectedValue);
    }

    @Override
    public void handleUpdateCourseGrade() {
        String course = (String) courseList.getSelectedValue();
        String[] coursesBreakdown = course.split(" ");
        String grade = (String) JOptionPane.showInputDialog(contentPane, "Set student grade", "Update grade",
                JOptionPane.PLAIN_MESSAGE, null, null, coursesBreakdown[2]);

        ArrayList<String> courses = null;
        ArrayList<String> grades = null;
        SQLInstance = new SQLQuery();
        query = "SELECT courses FROM student WHERE studentID = ?";
        queryParams.clear();
        queryParams.add((String) studentList.getSelectedValue());
        queryResult = SQLInstance.runQuery(query, queryParams);
        try {
            if (queryResult.next()) {
                String[] studentCourses = queryResult.getString("courses").split(",");
                List<String> studentCourseList = Arrays.asList(studentCourses);
                courses = new ArrayList<>(studentCourseList);
            }

            // grabs grades string
            query = "SELECT grades FROM student WHERE studentID = ?";
            queryParams.clear();
            queryParams.add((String) studentList.getSelectedValue());
            queryResult = SQLInstance.runQuery(query, queryParams);

            if (queryResult.next()) {
                String[] studentGrades = queryResult.getString("grades").split(",");
                List<String> studentGradeList = Arrays.asList(studentGrades);
                grades = new ArrayList<>(studentGradeList);
            }

            // finds course to change and changes corresponding course grade
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).equals(coursesBreakdown[0])) {
                    grades.set(i, grade);
                }
            }

            String courseToPass = "";
            String gradeToPass = "";
            for (String courseToUpdate : courses) {
                if (courseToUpdate.isEmpty()) {
                    continue;
                }
                courseToPass += courseToUpdate + ",";
            }
            for (String gradeToUpdate : grades) {
                if (gradeToUpdate.isEmpty()) {
                    continue;
                }
                gradeToPass += gradeToUpdate + ",";
            }

            query = "UPDATE student SET courses = ?, grades = ? WHERE studentID = ?";
            queryParams.clear();
            queryParams.add(courseToPass);
            queryParams.add(gradeToPass);
            queryParams.add((String) studentList.getSelectedValue());
            SQLInstance.runUpdate(query, queryParams);

            // upon clicking OK, update fields
            if (grade != null) {
                updateFields(studentList.getSelectedValue());
            }
        } catch (SQLException e1) {
            e1.getStackTrace();
        }
    }

    @Override
    public void handleEnableEditCourse() {
        editButton.setEnabled(true);
    }
}
