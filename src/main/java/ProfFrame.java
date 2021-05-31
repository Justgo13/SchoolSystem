import org.bson.types.ObjectId;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ProfFrame extends JFrame implements ProfView {
    private Container contentPane;
    private JPanel infoPanel;
    private JPanel firstNamePanel;
    private JPanel lastNamePanel;
    private JPanel tuitionFeePanel;
    private JPanel coursesPanel;
    private JPanel profCommandsPanel;
    private JPanel studentIDPanel;
    private JList<Object> studentList;
    private JList<String> courseTaughtList;
    private JList<String> courseList;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField tuitionFeeField;
    private JScrollPane courseScroll;
    private JScrollPane courseTaughtScroll;
    private JButton editButton;
    private JButton addCourseToTeach;
    private JButton removeCourseToTeach;
    private DefaultListModel<String> courseModel;
    private final ObjectId profID;
    private final ProfModel profModel;
    private DefaultListModel<String> courseTaughtListModel;

    public ProfFrame(ObjectId profID) {
        profModel = new ProfModel();
        this.profID = profID;
        initProfessorGUI();
    }

    public void initProfessorGUI() {
        ProfController profController = new ProfController(profModel);
        profModel.addProfView(this);

        courseTaughtListModel = new DefaultListModel<>();
        courseTaughtList = new JList<>(courseTaughtListModel);

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

        updateProfessorCourseTaught(); // populate courses taken by student from database

        GridBagConstraints c = createGenericGridBagConstraints();

        // create text fields
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        tuitionFeeField = new JTextField();
        firstNamePanel = createGenericTextFieldPanel(firstNameField, ProfFrameConstants.FIRST_NAME_FIELD_LABEL.toString(), c);
        lastNamePanel = createGenericTextFieldPanel(lastNameField, ProfFrameConstants.LAST_NAME_FIELD_LABEL.toString(), c);
        tuitionFeePanel = createGenericTextFieldPanel(tuitionFeeField, ProfFrameConstants.TUITION_FEE_FIELD_LABEL.toString(), c);

        // professor course taught list panel setup
        courseModel = new DefaultListModel<>();
        courseList = new JList<>(courseModel);
        courseScroll = new JScrollPane(courseList);
        courseList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        coursesPanel = new JPanel(new GridBagLayout());
        coursesPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), ProfFrameConstants.COURSES_BORDER_LABEL.toString()));
        coursesPanel.add(courseScroll, c);

        editButton = new JButton(ProfFrameConstants.EDIT_BUTTON_LABEL.toString());
        editButton.setEnabled(false);

        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        studentIDPanel.add(studentList, c);

        c.weightx = 0.2;
        contentPane.add(studentIDPanel, c);

        c.weightx = 0.8;
        c.gridx = 1;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        infoPanel = createStudentInfoPanel();
        contentPane.add(infoPanel, c);

        // prof commands panel
        addCourseToTeach = new JButton(ProfFrameConstants.ADD_COURSE_LABEL.toString());
        removeCourseToTeach = new JButton(ProfFrameConstants.REMOVE_COURSE_LABEL.toString());
        removeCourseToTeach.setEnabled(false);
        profCommandsPanel = createCourseTaughtPanel();

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
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
        GridBagConstraints c = createGenericGridBagConstraints();
        JPanel profCommandsPanel = new JPanel(new GridBagLayout());
        c.gridwidth = 2;
        courseTaughtScroll = new JScrollPane(courseTaughtList);
        profCommandsPanel.add(courseTaughtScroll, c);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        profCommandsPanel.add(addCourseToTeach, c);

        c.gridx = 1;
        c.gridy = 1;
        profCommandsPanel.add(removeCourseToTeach, c);
        return profCommandsPanel;
    }

    private JPanel createStudentInfoPanel() {
        GridBagConstraints c = createGenericGridBagConstraints();
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(firstNamePanel, c);
        c.gridy = 1;
        panel.add(lastNamePanel, c);
        c.gridy = 2;
        panel.add(tuitionFeePanel, c);
        c.gridy = 3;
        panel.add(coursesPanel, c);
        c.gridy = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(editButton, c);
        return panel;
    }

    private JPanel createGenericTextFieldPanel(JTextField textField, String textBoxTitle, GridBagConstraints c) {
        JPanel textPanel = new JPanel(new GridBagLayout());
        textPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), textBoxTitle));
        textField.setEditable(false);
        textPanel.add(textField, c);
        return textPanel;
    }

    private void updateProfessorCourseTaught() {
        List<String> courseTaught = MongoQueryInterface.getProfessorCourses(profID);
        courseTaughtListModel.clear();
        for (String courseTaughtItem : courseTaught) {
            courseTaughtListModel.addElement(courseTaughtItem);
        }
    }

    private void populateStudentID() {
        Map<ObjectId, List<String>> studentCoursesTaughtByProfessor = getStudentCoursesTaughtByProfessor();
        List<ObjectId> studentId = List.copyOf(studentCoursesTaughtByProfessor.keySet());
        studentList = new JList<>(studentId.toArray());
        studentList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Populate student information fields
     */
    private void updateStudentInformationFields(ObjectId studentID) {
        String firstName = MongoQueryInterface.getStudentFirstName(studentID);
        String lastName = MongoQueryInterface.getStudentLastName(studentID);
        Long tuitionFee = MongoQueryInterface.getStudentTuitionFee(studentID);

        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        tuitionFeeField.setText(String.valueOf(tuitionFee));
        courseModel.clear();

        Map<ObjectId, List<String>> studentCoursesTaughtByProfessor = getStudentCoursesTaughtByProfessor();
        List<String> studentCourses = studentCoursesTaughtByProfessor.get(studentID);

        for (String studentCourseName : studentCourses) {
            Long courseGrade = MongoQueryInterface.getStudentCourseGrade(studentID, studentCourseName);
            courseModel.addElement(studentCourseName + " - " + courseGrade);
        }
    }

    public Map<ObjectId, List<String>> getStudentCoursesTaughtByProfessor() {
        List<String> professorCourses = MongoQueryInterface.getProfessorCourses(profID);
        Map<ObjectId, List<String>> studentInformation = MongoQueryInterface.getAllStudentCourses();
        Map<ObjectId, List<String>> studentCoursesTaughtByProfessor = new HashMap<>();

        for (String professorCourse : professorCourses) {
            List<String> studentProfessorCourseMatches = new ArrayList<>();
            Set<ObjectId> studentIds = studentInformation.keySet();
            for (ObjectId studentId : studentIds) {
                studentProfessorCourseMatches.clear();
                List<String> currentStudentCourses = studentInformation.get(studentId);
                if (currentStudentCourses.contains(professorCourse)) {
                    // Found a course that the student takes that the professor also teaches
                    if (studentCoursesTaughtByProfessor.containsKey(studentId)) {
                        // get existing list
                        List<String> existingStudentCourses = studentCoursesTaughtByProfessor.get(studentId);
                        existingStudentCourses.add(professorCourse);
                        studentCoursesTaughtByProfessor.put(studentId, existingStudentCourses);
                    } else {
                        studentProfessorCourseMatches.add(professorCourse);
                        studentCoursesTaughtByProfessor.put(studentId, studentProfessorCourseMatches);
                    }
                }
            }
        }
        return studentCoursesTaughtByProfessor;
    }

    @Override
    public void handleShowStudentInfo(Object selectedValue) {
        updateStudentInformationFields((ObjectId) selectedValue);
    }

    @Override
    public void handleUpdateCourseGrade() {
        String course = courseList.getSelectedValue();
        ObjectId studentID = (ObjectId) studentList.getSelectedValue();
        String[] coursesBreakdown = course.split(" ");
        String courseName = coursesBreakdown[0];
        Long grade = Long.parseLong((String) JOptionPane.showInputDialog(contentPane, "Set student grade", "Update grade",
                JOptionPane.PLAIN_MESSAGE, null, null, coursesBreakdown[2]));
        MongoQueryInterface.updateStudentGrade(studentID, courseName, grade);
        updateStudentInformationFields(studentID);
        courseList.setSelectedValue(null, false);
        disableEditButtons();
    }

    @Override
    public void handleAddCourseTaught() {
        String courseToAdd = JOptionPane.showInputDialog(this, "Enter course code", "Add course taught", JOptionPane.OK_OPTION);
        MongoQueryInterface.addProfessorCourse(profID, courseToAdd);
        updateProfessorCourseTaught();
    }

    @Override
    public void handleRemoveCourseTaught() {
        String courseToRemove = courseTaughtList.getSelectedValue();
        MongoQueryInterface.removeProfessorCourse(profID, courseToRemove);
        updateProfessorCourseTaught();
        courseTaughtList.setSelectedValue(null, false);
        disableEditButtons();
    }

    @Override
    public void handleEnableEditCourse() {
        editButton.setEnabled(true);
    }

    @Override
    public void handleEnableRemoveCourseTaught() {
        removeCourseToTeach.setEnabled(true);
    }

    public void disableEditButtons() {
        if (studentList.isSelectionEmpty()) {
            editButton.setEnabled(false);
        }
        if (courseTaughtList.isSelectionEmpty()) {
            editButton.setEnabled(false);
        }
    }

    public GridBagConstraints createGenericGridBagConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.BOTH;
        return c;
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
        addCourseToTeach.addActionListener(profController);
        addCourseToTeach.setActionCommand(ProfFrameConstants.ADD_COURSE_BUTTON_COMMAND.toString());
        removeCourseToTeach.addActionListener(profController);
        removeCourseToTeach.setActionCommand(ProfFrameConstants.REMOVE_COURSE_BUTTON_COMMAND.toString());
    }
}
