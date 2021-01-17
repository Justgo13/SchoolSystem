//import java.awt.Color;
//import java.awt.Container;
//import java.awt.Dimension;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.swing.BorderFactory;
//import javax.swing.DefaultListModel;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JList;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextField;
//import javax.swing.WindowConstants;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//
//public class ProfessorGUI implements ListSelectionListener, ActionListener {
//    private JFrame frame;
//    private Container contentPane;
//    private JPanel infoPanel;
//    private JList studentList;
//    private ArrayList<String> studentListData;
//    private JPanel studentIDPanel;
//    private JTextField firstNameField;
//    private JTextField lastNameField;
//    private JTextField tuitionFeeField;
//    private JList courseList;
//    private JPanel firstNamePanel;
//    private JPanel lastNamePanel;
//    private JPanel tuitionFeePanel;
//    private JPanel coursesPanel;
//    private JScrollPane courseScroll;
//    private DefaultListModel courseModel;
//    private JButton editButton;
//
//    public ProfessorGUI() {
//        initProfessorGUI();
//    }
//
//    public void initProfessorGUI() {
//        frame = new JFrame();
//        contentPane = frame.getContentPane();
//        contentPane.setLayout(new GridBagLayout());
//        infoPanel = new JPanel(new GridBagLayout());
//        infoPanel
//                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Student Information"));
//        studentIDPanel = new JPanel(new GridBagLayout());
//        studentIDPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(1), "Student ID"));
//        studentListData = new ArrayList<String>();
//        try {
//            MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT studentID FROM student");
//            MainRun.myRs = MainRun.myStmt.executeQuery();
//            while (MainRun.myRs.next()) {
//                studentListData.add(MainRun.myRs.getString("studentID"));
//                studentList = new JList(studentListData.toArray());
//                studentList.addListSelectionListener(this);
//                studentList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            }
//
//        } catch (SQLException e) {
//            e.getStackTrace();
//        }
//
//        GridBagConstraints c = new GridBagConstraints();
//
//        // fields
//        firstNameField = new JTextField();
//        firstNameField.setEditable(false);
//        firstNamePanel = new JPanel(new GridBagLayout());
//        firstNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "First Name"));
//        c.fill = GridBagConstraints.BOTH;
//        c.weightx = 1.0;
//        c.weighty = 1.0;
//        firstNamePanel.add(firstNameField, c);
//
//        lastNameField = new JTextField();
//        lastNameField.setEditable(false);
//        lastNamePanel = new JPanel(new GridBagLayout());
//        lastNamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Last Name"));
//        lastNamePanel.add(lastNameField, c);
//
//        tuitionFeeField = new JTextField();
//        tuitionFeeField.setEditable(false);
//        tuitionFeePanel = new JPanel(new GridBagLayout());
//        tuitionFeePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Tuition Fee"));
//        tuitionFeePanel.add(tuitionFeeField, c);
//
//        courseModel = new DefaultListModel();
//        courseList = new JList(courseModel);
//        courseList.addListSelectionListener(this);
//        courseScroll = new JScrollPane(courseList);
//        courseList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        coursesPanel = new JPanel(new GridBagLayout());
//        coursesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Courses"));
//        coursesPanel.add(courseScroll, c);
//
//        editButton = new JButton("Edit Selected Course");
//        editButton.setMinimumSize(new Dimension(100, 27));
//        editButton.addActionListener(this);
//        editButton.setEnabled(false);
//
//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 1.0;
//        c.weighty = 1.0;
//        c.insets = new Insets(5, 5, 5, 5);
//        c.anchor = GridBagConstraints.FIRST_LINE_START;
//
//        studentIDPanel.add(studentList, c);
//
//        c.weightx = 0.2;
//        contentPane.add(studentIDPanel, c);
//
//        c.weightx = 0.80;
//        c.gridx = 1;
//        c.anchor = GridBagConstraints.FIRST_LINE_END;
//        contentPane.add(infoPanel, c);
//
//        // add component for panel field
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weighty = 1.0;
//        c.weightx = 1.0;
//        c.fill = GridBagConstraints.BOTH;
//        infoPanel.add(firstNamePanel, c);
//        c.gridy = 1;
//        infoPanel.add(lastNamePanel, c);
//        c.gridy = 2;
//        infoPanel.add(tuitionFeePanel, c);
//        c.gridy = 3;
//        infoPanel.add(coursesPanel, c);
//        c.gridy = 4;
//        c.fill = GridBagConstraints.NONE;
//        infoPanel.add(editButton, c);
//
//        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//        frame.pack();
//        frame.setSize(700, 500);
//        frame.setResizable(true);
//        frame.setVisible(true);
//        frame.setLocationRelativeTo(null);
//    }
//
//    @Override
//    public void valueChanged(ListSelectionEvent e) {
//        Object o = e.getSource();
//        JList studentIDList = (JList) o;
//        if (studentIDList.equals(studentList)) {
//            updateFields(studentIDList.getSelectedValue());
//            editButton.setEnabled(false);
//        } else if (studentIDList.equals(courseList)) {
//            editButton.setEnabled(true);
//        }
//
//    }
//
//    /**
//     * Populate student information fields
//     *
//     * @param studentID
//     */
//    private void updateFields(Object studentID) {
//        String ID = (String) studentID;
//        ArrayList<String> courses = null;
//        ArrayList<String> grades = null;
//        try {
//            MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT * FROM student WHERE studentID = ?");
//            MainRun.myStmt.setString(1, ID);
//            MainRun.myRs = MainRun.myStmt.executeQuery();
//
//            if (MainRun.myRs.next()) {
//                firstNameField.setText(MainRun.myRs.getString("firstName"));
//                lastNameField.setText(MainRun.myRs.getString("lastName"));
//                tuitionFeeField.setText(MainRun.myRs.getString("tuitionFee"));
//            }
//
//            // grabs course string
//            MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT courses FROM student WHERE studentID = ?");
//            MainRun.myStmt.setString(1, ID);
//            ResultSet courseResult = MainRun.myStmt.executeQuery();
//
//            if (courseResult.next()) {
//                if (courseResult.getString("courses") == null) {
//                    courses = new ArrayList<String>();
//                } else {
//                    String[] studentCourses = courseResult.getString("courses").split(",");
//                    List<String> studentCourseList = Arrays.asList(studentCourses);
//                    courses = new ArrayList<String>(studentCourseList);
//                }
//            }
//
//            // grabs grades string
//            MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT grades FROM student WHERE studentID = ?");
//            MainRun.myStmt.setString(1, ID);
//            ResultSet gradeResultSet = MainRun.myStmt.executeQuery();
//
//            if (gradeResultSet.next()) {
//                if (gradeResultSet.getString("grades") == null) {
//                    grades = new ArrayList<String>();
//                } else {
//                    String[] studentGrades = gradeResultSet.getString("grades").split(",");
//                    List<String> studentGradeList = Arrays.asList(studentGrades);
//                    grades = new ArrayList<String>(studentGradeList);
//                }
//            }
//
//            for (String course : courses) {
//                courses.set(courses.indexOf(course), course + " - " + grades.get(courses.indexOf(course)));
//            }
//            courseModel.clear();
//            courseModel.addAll(0, courses);
//        } catch (SQLException e) {
//            e.getStackTrace();
//        }
//
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        Object o = e.getSource();
//        JButton edit = (JButton) o;
//        if (edit.equals(editButton)) {
//            String course = (String) courseList.getSelectedValue();
//            String[] coursesBreakdown = course.split(" ");
//            String grade = (String) JOptionPane.showInputDialog(contentPane, "Set student grade", "Update grade",
//                    JOptionPane.PLAIN_MESSAGE, null, null, coursesBreakdown[2]);
//            ArrayList<String> courses = null;
//            ArrayList<String> grades = null;
//            try {
//                MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT courses FROM student WHERE studentID = ?");
//                MainRun.myStmt.setString(1, (String) studentList.getSelectedValue());
//                MainRun.myRs = MainRun.myStmt.executeQuery();
//
//                if (MainRun.myRs.next()) {
//                    String[] studentCourses = MainRun.myRs.getString("courses").split(",");
//                    List<String> studentCourseList = Arrays.asList(studentCourses);
//                    courses = new ArrayList<String>(studentCourseList);
//                }
//
//                // grabs grades string
//                MainRun.myStmt = MainRun.myConn.prepareStatement("SELECT grades FROM student WHERE studentID = ?");
//                MainRun.myStmt.setString(1, (String) studentList.getSelectedValue());
//                ResultSet gradeResultSet = MainRun.myStmt.executeQuery();
//
//                if (gradeResultSet.next()) {
//                    String[] studentGrades = gradeResultSet.getString("grades").split(",");
//                    List<String> studentGradeList = Arrays.asList(studentGrades);
//                    grades = new ArrayList<String>(studentGradeList);
//                }
//
//                // finds course to change and changes corresponding course grade
//                for (int i = 0; i < courses.size(); i++) {
//                    if (courses.get(i).equals(coursesBreakdown[0])) {
//                        grades.set(i, grade);
//                    }
//                }
//
//                String courseToPass = "";
//                String gradeToPass = "";
//                for (String courseToUpdate : courses) {
//                    if (courseToUpdate.isEmpty()) {
//                        continue;
//                    }
//                    courseToPass += courseToUpdate + ",";
//                }
//                for (String gradeToUpdate : grades) {
//                    if (gradeToUpdate.isEmpty()) {
//                        continue;
//                    }
//                    gradeToPass += gradeToUpdate + ",";
//                }
//
//                MainRun.myStmt = MainRun.myConn
//                        .prepareStatement("UPDATE student SET courses = ?, grades = ? WHERE studentID = ?");
//                MainRun.myStmt.setString(1, courseToPass);
//                MainRun.myStmt.setString(2, gradeToPass);
//                MainRun.myStmt.setString(3, (String) studentList.getSelectedValue());
//                MainRun.myStmt.executeUpdate();
//
//                // upon clicking OK, update fields
//                if (grade != null) {
//                    updateFields(studentList.getSelectedValue());
//                }
//            } catch (SQLException e1) {
//                e1.getStackTrace();
//            }
//        }
//
//    }
//}
