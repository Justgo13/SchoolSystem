import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 * Creates the frame of the School system GUI
 *
 * @author Jason Gao
 * @version v1.00 Dec 18, 2019
 */
public class GUI implements ActionListener
{
    /**
     * Dictionaries for storing Teacher, Principal, and Students
     */
    private HashMap<Integer,Professor> professorHashMap;
    private HashMap<Integer, Dean> deanHashMap;
    private HashMap<Integer,Student> studentHashMap;

    /**
     * JButton for creating new staff members and students
     */
    private JButton newTeacher;
    private JButton newPrincipal;
    private JButton newStudent;

    /**
     * JButton for returning list of staff members and students
     */
    private JButton getStudent;
    private JButton getProfessor;
    private JButton getDean;

    /**
     * JButton for Student
     */
    private JButton registerCourse;
    private JButton deregisterCourse;
    private JButton courseFee;
    private JButton fees;

    /**
     * JTextField for inputting information
     */
    private JTextField name;
    private JTextField courseTaught;
    private JTextField studentID;
    private JTextField schoolName;
    private JTextField employeeID;
    private JTextField course;
    private JTextField major;

    /**
     * JComboBox for inputting information
     */
    private JComboBox<Integer> age;
    private JComboBox<Integer> studentAge;
    private JComboBox<Integer> salary;
    private JComboBox<Integer> yearStanding;

    /**
     * JTextArea for outputing information
     */
    private JTextArea teacherInfo;
    private JTextArea principalInfo;
    private JTextArea studentInfo;

    /**
     * Red line border to indicate missing fields
     */
    private Border redLineBorder;
    /**
     * Constructor the skeleton for the GUI
     */
    public GUI()
    {
        JFrame frame = new JFrame();
        Container container = frame.getContentPane();

        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        redLineBorder = BorderFactory.createLineBorder(Color.RED);

        TitledBorder staff, teacher, principal, student;

        staff = BorderFactory.createTitledBorder("Staff");
        teacher = BorderFactory.createTitledBorder("Teacher");
        principal = BorderFactory.createTitledBorder("Principal");
        student = BorderFactory.createTitledBorder("Student");

        // Initialize all dictionaries
        professorHashMap = new HashMap<>();
        deanHashMap = new HashMap<>();
        studentHashMap = new HashMap<>();

        JPanel teacherPanel = new JPanel(new GridLayout(2,1));
        teacherPanel.setBorder(teacher);
        newTeacher = new JButton("NEW TEACHER");
        getProfessor = new JButton("GET TEACHER INFO");
        newTeacher.addActionListener(this);
        getProfessor.addActionListener(this);
        teacherPanel.add(newTeacher);
        teacherPanel.add(getProfessor);

        JPanel principalPanel = new JPanel(new GridLayout(2,1));
        principalPanel.setBorder(principal);
        newPrincipal = new JButton("NEW PRINCIPAL");
        getDean = new JButton("GET PRINCIPAL INFO");
        newPrincipal.addActionListener(this);
        getDean.addActionListener(this);
        principalPanel.add(newPrincipal);
        principalPanel.add(getDean);

        JPanel staffPanel = new JPanel(new GridLayout(1,2));
        staffPanel.setBorder(staff);
        staffPanel.add(teacherPanel);
        staffPanel.add(principalPanel);

        JPanel studentPanel = new JPanel(new GridLayout(3,3));
        studentPanel.setBorder(student);
        newStudent = new JButton("NEW STUDENT");
        getStudent = new JButton("GET STUDENT INFO");
        registerCourse = new JButton("REGISTER COURSE");
        deregisterCourse = new JButton("UNREGISTER COURSE");
        courseFee = new JButton("GET COURSES");
        fees = new JButton("GET FEES");
        newStudent.addActionListener(this);
        getStudent.addActionListener(this);
        registerCourse.addActionListener(this);
        deregisterCourse.addActionListener(this);
        courseFee.addActionListener(this);
        fees.addActionListener(this);
        studentPanel.add(newStudent);
        studentPanel.add(getStudent);
        studentPanel.add(registerCourse);
        studentPanel.add(deregisterCourse);
        studentPanel.add(courseFee);
        studentPanel.add(fees);

        // Creates data for JComboBox
        Integer[] teacherAgeData = new Integer[51];
        for (Integer i = 0; i < 51; i++){
            teacherAgeData[i] = i + 30;
        }

        Integer[] studentAgeData = new Integer[13];
        for (Integer i = 0; i < 13; i++){
            studentAgeData[i] = i + 18;
        }

        Integer[] salaryData = new Integer[17];
        int value = 0;
        for (Integer i = 0; i < 17; i++){
            salaryData[i] = 40000 + value;
            value += 10000;
        }

        Integer[] yearStandingData = new Integer[4];
        for (Integer i = 0; i < 4; i++){
            yearStandingData[i] = i + 1;
        }

        // Creates JTextField and JCheckBox in the JDialog box
        name = new JTextField();
        age = new JComboBox<>(teacherAgeData);
        studentAge = new JComboBox<>(studentAgeData);
        major = new JTextField();
        yearStanding = new JComboBox<>(yearStandingData);
        salary = new JComboBox<>(salaryData);
        employeeID = new JTextField();
        courseTaught = new JTextField();
        studentID = new JTextField();
        schoolName = new JTextField();
        course = new JTextField();

        // Creates JTextArea for outputing information
        teacherInfo = new JTextArea();
        principalInfo = new JTextArea();
        studentInfo = new JTextArea();

        teacherInfo.setEditable(false);
        principalInfo.setEditable(false);
        studentInfo.setEditable(false);

        container.add(staffPanel);
        container.add(studentPanel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,200);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    /**
     * Adds the desired information to JTextArea of Teacher
     */
    public void addToListTeacher(){
        String teacherList = "";
        for (Map.Entry<Integer, Professor> entry : professorHashMap.entrySet()) {
            int k = entry.getKey();
            Professor v = entry.getValue();
            teacherList += k + ": " + v.toString() + "\n";
        }
        if (teacherList.isEmpty()){
            teacherInfo.setText("There are no teachers");
            return;
        }
        teacherInfo.setText(teacherList);
    }

    /**
     * Adds the desired information to JTextArea of Principal
     */
    public void addToListPrincipal() {
        String principalList = "";
        for (Map.Entry<Integer, Dean> entry : deanHashMap.entrySet()) {
            int k = entry.getKey();
            Dean v = entry.getValue();
            principalList += k + ": " + v.toString() + "\n";
        }
        if (principalList.isEmpty()){
            principalInfo.setText("There are no principals");
            return;
        }
        principalInfo.setText(principalList);
    }

    /**
     * Adds the desired information to JTextArea of Student
     */
    public void addToListStudent() {
        String studentList = "";
        for (Map.Entry<Integer, Student> entry : studentHashMap.entrySet()) {
            int k = entry.getKey();
            Student v = entry.getValue();
            studentList += k + ": " + v.toString() + "\n";
        }

        if (studentList.isEmpty()){
            studentInfo.setText("There are no students");
            return;
        }
        studentInfo.setText(studentList);
    }

    /**
     * Returns Student courses
     * @param studentID int student ID for identifying student
     * @return ArrayList<String> of courses Student registered for </String>
     */
    public ArrayList<String> getCourses(int studentID) {
        Student student = studentHashMap.get(studentID);
        return student.getCourse();
    }

    /**
     * Returns Student fees
     * @param studentID int student ID for identifying student
     * @return int Student fee
     */
    public int getFees(int studentID) {
        Student student = studentHashMap.get(studentID);
        return student.getFees();
    }

    /**
     * Clears all JTextFields
     */
    public void clear(){
        name.setText(null);
        age.setSelectedIndex(0);
        studentAge.setSelectedIndex(0);
        major.setText(null);
        yearStanding.setSelectedIndex(0);
        salary.setSelectedIndex(0);
        employeeID.setText(null);
        schoolName.setText(null);
        courseTaught.setText(null);
        studentID.setText(null);
    }

    /**
     * Adds default border color
     */
    public void defaultBorder(){
        name.setBorder(null);
        employeeID.setBorder(null);
        major.setBorder(null);
        yearStanding.setBorder(null);
        course.setBorder(null);
        courseTaught.setBorder(null);
        schoolName.setBorder(null);
        studentID.setBorder(null);
    }

    /**
     * Makes JTextField have red border if missing field
     */
    public void addBorder(){
        if (name.getText().equals("")){
            name.setBorder(redLineBorder);
        }
        if (course.getText().equals("")) {
            course.setBorder(redLineBorder);
        }
        if (major.getText().equals("")) {
            major.setBorder(redLineBorder);
        }
        if (employeeID.getText().equals("")) {
            employeeID.setBorder(redLineBorder);
        }
        if (courseTaught.getText().equals("")){
            courseTaught.setBorder(redLineBorder);
        }
        if (schoolName.getText().equals("")){
            schoolName.setBorder(redLineBorder);
        }
        if (studentID.getText().equals("")){
            studentID.setBorder(redLineBorder);
        }
    }

    /**
     * Method that listens to ActionEvents
     *
     * @param e An ActionEvent that is passed in from JButton click
     * @throws NumberFormatException If JTextField are submitted blank
     */
    public void actionPerformed(ActionEvent e) throws NumberFormatException {
        Object o = e.getSource();
        JButton button = (JButton) o;
        boolean notFilled = true;

        // Object array for storing JTextFields
        Object[] teacherFields = {"Name", name, "Age", age, "Salary", salary, "Employee ID", employeeID, "Course taught", courseTaught};
        Object[] principalFields = {"Name", name, "Age", age, "Salary", salary, "Employee ID", employeeID, "School Name", schoolName};
        Object[] studentFields = {"Name", name, "Age", studentAge, "Student ID", studentID, "Major", major, "Year Standing", yearStanding};
        Object[] addCourseField = {"Student ID", studentID, "Course Code", course};
        Object[] courses = {"Student ID", studentID};
        Object[] studentFee = {"Student ID", studentID};
        if (button == newTeacher){
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, teacherFields);
                    professorHashMap.put(Integer.parseInt(employeeID.getText()), new Professor(Integer.parseInt(age.getSelectedItem().toString()), name.getText(), Integer.parseInt(salary.getSelectedItem().toString()), Integer.parseInt(employeeID.getText()), courseTaught.getText()));
                    notFilled = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    addBorder();
                }
            }
            defaultBorder();
        } else if (button == newPrincipal){
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, principalFields);
                    deanHashMap.put(Integer.parseInt(employeeID.getText()), new Dean(Integer.parseInt(age.getSelectedItem().toString()), name.getText(), Integer.parseInt(salary.getSelectedItem().toString()), Integer.parseInt(employeeID.getText()), schoolName.getText()));
                    notFilled = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    addBorder();
                }
            }
            defaultBorder();
        } else if (button == newStudent){
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, studentFields);
                    studentHashMap.put(Integer.parseInt(studentID.getText()), new Student(Integer.parseInt(studentAge.getSelectedItem().toString()), name.getText(), Integer.parseInt(studentID.getText()), major.getText(), Integer.parseInt(String.valueOf(yearStanding.getSelectedItem()))));
                    notFilled = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    addBorder();
                }
            }
            defaultBorder();
        } else if (button == getProfessor){
            addToListTeacher();
            JOptionPane.showMessageDialog(null, teacherInfo);
        } else if (button == getDean){
            addToListPrincipal();
            JOptionPane.showMessageDialog(null, principalInfo);
        } else if (button == getStudent){
            addToListStudent();
            JOptionPane.showMessageDialog(null, studentInfo);
        } else if (button == registerCourse) {
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, addCourseField);
                    studentHashMap.get(Integer.parseInt(studentID.getText())).addCourse(course.getText());
                    notFilled = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill your course code");
                    addBorder();
                }
            }
        } else if (button == deregisterCourse) {
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, addCourseField);
                    studentHashMap.get(Integer.parseInt(studentID.getText())).removeCourse(course.getText());
                    notFilled = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill your course code");
                    addBorder();
                }
            }
        } else if (button == courseFee) {
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, courses);
                    if (getCourses(Integer.parseInt(studentID.getText())).isEmpty()) {
                        JOptionPane.showMessageDialog(null, "There is no courses for this student");
                        return;
                    }
                    JOptionPane.showMessageDialog(null, getCourses(Integer.parseInt(studentID.getText())));
                    notFilled = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in the student ID");
                    addBorder();
                }
            }
        }  else if (button == fees) {
            clear();
            while(notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, studentFee);
                    JOptionPane.showMessageDialog(null, getFees(Integer.parseInt(studentID.getText())));
                    notFilled = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in the student ID");
                    addBorder();
                }
            }
        }
    }
}
