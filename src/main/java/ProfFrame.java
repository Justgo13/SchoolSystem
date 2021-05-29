import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private DefaultListModel<String> courseModel;
    private final String profID;
    private final ProfModel profModel;
    private DefaultListModel courseTaughtListModel;
    private final MongoQuery mongoQuery;

    public ProfFrame(String profID) {
        profModel = new ProfModel();
        mongoQuery = new MongoQuery();
        this.profID = profID;
        initProfessorGUI();
    }

    public void initProfessorGUI() {
        ProfController profController = new ProfController(profModel);
        profModel.addProfView(this);

        courseTaughtListModel = new DefaultListModel();
        courseTaughtList = new JList(courseTaughtListModel);

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
        firstNamePanel = createGenericTextFieldPanel(firstNameField, ProfFrameConstants.FIRST_NAME_FIELD_LABEL.toString(), c);
        lastNamePanel = createGenericTextFieldPanel(lastNameField, ProfFrameConstants.LAST_NAME_FIELD_LABEL.toString(), c);
        tuitionFeePanel = createGenericTextFieldPanel(tuitionFeeField, ProfFrameConstants.TUITION_FEE_FIELD_LABEL.toString(), c);

        // student course list panel setup
        courseModel = new DefaultListModel<>();
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
        removeCourseToTeach.setEnabled(false);
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
        MongoCollection<Document> profCollection = mongoQuery.getCollection("Professor");
        Document profDocument = profCollection.find(new Document("username", profID)).first();
        List<String> courseTaught = profDocument.getList("coursesTaught", String.class);
        courseTaughtListModel.clear();
        for (String courseTaughtItem : courseTaught) {
            courseTaughtListModel.addElement(courseTaughtItem);
        }
    }

    private void populateStudentID() {
        MongoCollection<Document> studentCollection = mongoQuery.getCollection("Student");
        MongoCollection<Document> professorCollection = mongoQuery.getCollection("Professor");
        Document profDocument = professorCollection.find(new Document("username", profID)).first();
        FindIterable<Document> studentDocumentIterable = studentCollection.find();
        List<String> professorTaughtCourse = profDocument.getList("coursesTaught", String.class);

        // stores course names taught by professor in a arraylist
        ArrayList<String> professorCourses = new ArrayList<>();
        for (String profCourse : professorTaughtCourse) {
            professorCourses.add(profCourse);
        }

        Set<String> studentListData = new HashSet<>();
        for (Document student : studentDocumentIterable) {
            List<Document> studentTakenCourse = student.getList("courseGrades", Document.class);
            for (Document course : studentTakenCourse) {
                if (professorCourses.contains(course.getString("courseName"))) {
                    String student_username = student.getString("username");
                    // make sure student is being added for the first time
                    if (!studentListData.contains(student_username)) {
                        // this student takes the course taught by this professor
                        studentListData.add(student.getString("username"));
                    }
                }
            }
        }
        studentList = new JList(studentListData.toArray());
        studentList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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

    /**
     * Populate student information fields
     *
     * @param studentID
     */
    private void updateStudentInformationFields(Object studentID) {
        MongoCollection<Document> studentCollection = mongoQuery.getCollection("Student");
        MongoCollection<Document> profCollection = mongoQuery.getCollection("Professor");
        Document studentDocument = studentCollection.find(new Document("username", studentID)).first();
        Document profDocument = profCollection.find(new Document("username", profID)).first();
        String firstName = studentDocument.getString("first name");
        String lastName = studentDocument.getString("last name");
        Long tuitionFee = studentDocument.getLong("tuition fee");
        List<Document> courseGrades = studentDocument.getList("courseGrades", Document.class);
        List<String> courseTaught = profDocument.getList("coursesTaught", String.class);

        // stores course names taught by professor in a arraylist
        ArrayList<String> professorCourses = new ArrayList<>();
        for (String profCourse : courseTaught) {
            professorCourses.add(profCourse);
        }

        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        tuitionFeeField.setText(String.valueOf(tuitionFee));
        courseModel.clear();
        for (Document courseGrade : courseGrades) {
            String courseName = courseGrade.getString("courseName");
            Long grade = courseGrade.getLong("grade");
            if (professorCourses.contains(courseName)) {
                courseModel.addElement(courseName + " - " + grade);
            }
        }
    }

    @Override
    public void handleShowStudentInfo(Object selectedValue) {
        updateStudentInformationFields(selectedValue);
    }

    @Override
    public void handleUpdateCourseGrade() {
        String course = (String) courseList.getSelectedValue();
        String[] coursesBreakdown = course.split(" ");
        String grade = (String) JOptionPane.showInputDialog(contentPane, "Set student grade", "Update grade",
                JOptionPane.PLAIN_MESSAGE, null, null, coursesBreakdown[2]);

        MongoCollection<Document> studentCollection = mongoQuery.getCollection("Student");
        Document studentDocument = studentCollection.find(new Document("username", studentList.getSelectedValue())).first();

        BasicDBObject query = new BasicDBObject();
        query.put("_id", studentDocument.getObjectId("_id"));
        query.put("courseGrades.courseName", coursesBreakdown[0]);

        BasicDBObject data = new BasicDBObject();
        data.put("courseGrades.$.grade", Long.valueOf(grade));

        BasicDBObject command = new BasicDBObject();
        command.put("$set", data);

        studentCollection.updateOne(query, command);
        updateStudentInformationFields(studentList.getSelectedValue());
        disableEditButtons();
    }

    @Override
    public void handleEnableEditCourse() {
        editButton.setEnabled(true);
    }

    @Override
    public void handleAddCourseTaught() {
        String courseTaught = JOptionPane.showInputDialog(this, "Enter course code", "Add course taught", +JOptionPane.OK_OPTION);
        MongoCollection<Document> profCollection = mongoQuery.getCollection("Professor");
        Document profDocument = profCollection.find(new Document("username", profID)).first();

        BasicDBObject query = new BasicDBObject();
        query.put("_id", profDocument.getObjectId("_id"));

        BasicDBObject fields = new BasicDBObject();
        fields.put("coursesTaught", courseTaught);

        BasicDBObject command = new BasicDBObject();
        command.put("$push", fields);

        profCollection.updateOne(query, command);
        populateCourseTaught();
    }

    @Override
    public void handleRemoveCourseTaught() {
        String currentCourseSelected = (String) courseTaughtList.getSelectedValue();
        MongoCollection<Document> profCollection = mongoQuery.getCollection("Professor");
        Document profDocument = profCollection.find(new Document("username", profID)).first();

        BasicDBObject query = new BasicDBObject();
        query.put("_id", profDocument.getObjectId("_id"));

        BasicDBObject fields = new BasicDBObject();
        fields.put("coursesTaught", currentCourseSelected);

        BasicDBObject command = new BasicDBObject();
        command.put("$pull", fields);

        profCollection.updateOne(query, command);
        populateCourseTaught();
        disableEditButtons();
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
}
