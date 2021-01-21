import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ProfessorGUI extends JFrame implements ListSelectionListener, ActionListener {
    private ArrayList<String> studentListData;
    private ArrayList<String> courseTaughtData;
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

    public ProfessorGUI(String profID) {
        queryParams = new ArrayList<>();
        queryResult = null;
        SQLInstance = null;
        this.profID = profID;
        initProfessorGUI();
    }

    public void initProfessorGUI() {
        contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Student Information"));
        studentIDPanel = new JPanel(new GridBagLayout());
        studentIDPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Student ID"));
        profCommandsPanel = new JPanel(new GridBagLayout());
        profCommandsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Courses Taught"));

        studentListData = new ArrayList<>();
        courseTaughtData = new ArrayList<>();

        SQLInstance = new SQLQuery();
        query = "SELECT studentID FROM student";
        queryResult = SQLInstance.runQuery(query, queryParams);
        try {
            while (queryResult.next()) {
                studentListData.add(queryResult.getString("studentID"));
                studentList = new JList(studentListData.toArray());
                studentList.addListSelectionListener(this);
                studentList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
            SQLInstance.getMyConn().close();
            System.out.println("Connection terminated");
        } catch (SQLException e) {
            e.getStackTrace();
        }

        SQLInstance = new SQLQuery();
        query = "SELECT courseTaught FROM professor WHERE professorID = ?";
        queryParams.clear();
        queryParams.add(profID);
        queryResult = SQLInstance.runQuery(query, queryParams);
        try {
            if (queryResult.next()) {
                String[] courseTaught = queryResult.getString("courseTaught").split(",");
                List<String> courseList = Arrays.asList(courseTaught);
                courseTaughtData = new ArrayList<>(courseList);
                courseTaughtList = new JList(courseTaughtData.toArray());
                courseTaughtList.addListSelectionListener(this);
                courseTaughtList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
            SQLInstance.getMyConn().close();
            System.out.println("Connection terminated");
        } catch (SQLException e) {
            e.getStackTrace();
        }

        GridBagConstraints c = new GridBagConstraints();

        // fields
        firstNameField = new JTextField();
        firstNameField.setEditable(false);
        firstNamePanel = new JPanel(new GridBagLayout());
        firstNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "First Name"));
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        firstNamePanel.add(firstNameField, c);

        lastNameField = new JTextField();
        lastNameField.setEditable(false);
        lastNamePanel = new JPanel(new GridBagLayout());
        lastNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Last Name"));
        lastNamePanel.add(lastNameField, c);

        tuitionFeeField = new JTextField();
        tuitionFeeField.setEditable(false);
        tuitionFeePanel = new JPanel(new GridBagLayout());
        tuitionFeePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Tuition Fee"));
        tuitionFeePanel.add(tuitionFeeField, c);

        courseModel = new DefaultListModel();
        courseList = new JList(courseModel);
        courseList.addListSelectionListener(this);
        courseScroll = new JScrollPane(courseList);
        courseList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        coursesPanel = new JPanel(new GridBagLayout());
        coursesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Courses"));
        coursesPanel.add(courseScroll, c);

        editButton = new JButton("Edit Selected Course");
        editButton.setMinimumSize(new Dimension(100, 27));
        editButton.addActionListener(this);
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
        contentPane.add(infoPanel, c);

        // add component for panel field
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.BOTH;
        infoPanel.add(firstNamePanel, c);
        c.gridy = 1;
        infoPanel.add(lastNamePanel, c);
        c.gridy = 2;
        infoPanel.add(tuitionFeePanel, c);
        c.gridy = 3;
        infoPanel.add(coursesPanel, c);
        c.gridy = 4;
        c.fill = GridBagConstraints.NONE;
        infoPanel.add(editButton, c);

        // prof commands panel
        addCourseToTeach = new JButton("Add course");
        removeCourseToTeach = new JButton("Remove course");
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        profCommandsPanel.add(addCourseToTeach,c);

        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        profCommandsPanel.add(removeCourseToTeach,c);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        courseTaughtScroll = new JScrollPane(courseTaughtList);
        profCommandsPanel.add(courseTaughtScroll, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        contentPane.add(profCommandsPanel, c);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setSize(700, 500);
        setResizable(true);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        Object o = e.getSource();
        JList studentIDList = (JList) o;
        if (studentIDList.equals(studentList)) {
            updateFields(studentIDList.getSelectedValue());
            editButton.setEnabled(false);
        } else if (studentIDList.equals(courseList)) {
            editButton.setEnabled(true);
        }

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
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        JButton edit = (JButton) o;
        if (edit.equals(editButton)) {
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

    }
}
