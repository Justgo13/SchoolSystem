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
    private HashMap<Integer,Teacher> teachers;
    private HashMap<Integer,Principal> principals;
    private HashMap<Integer,Student> students;

    /**
     * JButton for creating new staff members and students
     */
    JButton newTeacher;
    JButton newPrincipal;
    JButton newStudent;

    /**
     * JButton for returning list of staff members and students
     */
    JButton getStudent;
    JButton getTeacher;
    JButton getPrincipal;

    /**
     * JDialog boxes for entering new staff or student information
     */
    JDialog teacherBox;
    JDialog principalBox;
    JDialog studentBox;
    JDialog teacherList;
    JDialog principalList;
    JDialog studentList;

    /**
     * JTextField for inputting information
     */
    JTextField name;
    JTextField courseTaught;
    JTextField studentID;
    JTextField schoolName;

    /**
     * JComboBox for inputting information
     */
    JComboBox<Integer> age;
    JComboBox<Integer> salary;
    JComboBox<Integer> employeeID;

    /**
     * JTextArea for outputing information
     */
    JTextArea teacherInfo;
    JTextArea principalInfo;
    JTextArea studentInfo;

    /**
     * Red line border to indicate missing fields
     */
    Border redLineBorder;
    Border blackLineBorder;
    /**
     * Constructor the skeleton for the GUI
     */
    public GUI()
    {
        JFrame frame = new JFrame();
        Container container = frame.getContentPane();

        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));

        redLineBorder = BorderFactory.createLineBorder(Color.RED);
        blackLineBorder = BorderFactory.createLineBorder(Color.BLACK);

        TitledBorder staff, teacher, principal, student;

        staff = BorderFactory.createTitledBorder("Staff");
        teacher = BorderFactory.createTitledBorder("Teacher");
        principal = BorderFactory.createTitledBorder("Principal");
        student = BorderFactory.createTitledBorder("Student");

        // Initialize all dictionaries
        teachers = new HashMap<Integer, Teacher>();
        principals = new HashMap<Integer,Principal>();
        students = new HashMap<Integer,Student>();

        JPanel teacherPanel = new JPanel(new GridLayout(2,1));
        teacherPanel.setBorder(teacher);
        newTeacher = new JButton("NEW TEACHER");
        getTeacher = new JButton("GET TEACHER INFO");
        newTeacher.addActionListener(this);
        getTeacher.addActionListener(this);
        teacherPanel.add(newTeacher);
        teacherPanel.add(getTeacher);

        JPanel principalPanel = new JPanel(new GridLayout(2,1));
        principalPanel.setBorder(principal);
        newPrincipal = new JButton("NEW PRINCIPAL");
        getPrincipal = new JButton("GET PRINCIPAL INFO");
        newPrincipal.addActionListener(this);
        getPrincipal.addActionListener(this);
        principalPanel.add(newPrincipal);
        principalPanel.add(getPrincipal);

        JPanel staffPanel = new JPanel(new GridLayout(1,2));
        staffPanel.setBorder(staff);
        staffPanel.add(teacherPanel);
        staffPanel.add(principalPanel);

        JPanel studentPanel = new JPanel(new GridLayout(2,1));
        studentPanel.setBorder(student);
        newStudent = new JButton("NEW STUDENT");
        getStudent = new JButton("GET STUDENT INFO");
        newStudent.addActionListener(this);
        getStudent.addActionListener(this);
        studentPanel.add(newStudent);
        studentPanel.add(getStudent);

        // Creates JDialog boxes
        teacherBox = new JDialog();
        teacherBox.setSize(200,100);
        principalBox = new JDialog();
        principalBox.setSize(200,100);
        studentBox = new JDialog();
        studentBox.setSize(200,100);

        // JDialog box for outputing staff member / student info
        teacherList = new JDialog();
        principalList = new JDialog();
        studentList = new JDialog();

        // Creates data for JComboBox
        Integer[] ageData = new Integer[51];
        for (Integer i = 0; i < 51; i++){
            ageData[i] = i + 30;
        }

        Integer[] salaryData = new Integer[17];
        int value = 0;
        for (Integer i = 0; i < 17; i++){
            salaryData[i] = 40000 + value;
            value += 10000;
        }

        Integer[] employeeIDData = new Integer[100];
        for (Integer i = 0; i < 100; i++){
            employeeIDData[i] = i;
        }
        // Creates JTextField and JCheckBox in the JDialog box
        name = new JTextField();
        age = new JComboBox<Integer>(ageData);
        salary = new JComboBox<Integer>(salaryData);
        employeeID = new JComboBox<Integer>(employeeIDData);
        courseTaught = new JTextField();
        studentID = new JTextField();
        schoolName = new JTextField();

        // Creates JTextArea for outputing information
        teacherInfo = new JTextArea();
        principalInfo = new JTextArea();
        studentInfo = new JTextArea();

        teacherInfo.setEditable(false);
        principalInfo.setEditable(false);
        studentInfo.setEditable(false);

        // Adds JTextField to teacherBox
        teacherBox.add(name);
        teacherBox.add(age);
        teacherBox.add(salary);
        teacherBox.add(courseTaught);

        // Adds JTextField to principalBox
        principalBox.add(name);
        principalBox.add(age);
        principalBox.add(salary);
        principalBox.add(schoolName);

        // Adds JTextField to studentBox
        studentBox.add(name);
        studentBox.add(age);
        studentBox.add(studentID);

        // Adds JTextArea to JDialog for outputing info
        teacherList.add(teacherInfo);
        principalList.add(principalInfo);
        studentList.add(studentInfo);

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
        for (Map.Entry<Integer, Teacher> entry : teachers.entrySet()) {
            int k = entry.getKey();
            Teacher v = entry.getValue();
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
        for (Map.Entry<Integer, Principal> entry : principals.entrySet()) {
            int k = entry.getKey();
            Principal v = entry.getValue();
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
        for (Map.Entry<Integer, Student> entry : students.entrySet()) {
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
     * Clears all JTextFields
     */
    public void clear(){
        name.setText(null);
        age.setSelectedIndex(0);
        salary.setSelectedIndex(0);
        employeeID.setSelectedIndex(0);
        schoolName.setText(null);
        courseTaught.setText(null);
        studentID.setText(null);
    }

    /**
     * Adds default border color
     */
    public void defaultBorder(){
        name.setBorder(blackLineBorder);
        employeeID.setBorder(blackLineBorder);
        courseTaught.setBorder(blackLineBorder);
        schoolName.setBorder(blackLineBorder);
        studentID.setBorder(blackLineBorder);
    }

    /**
     * Makes JTextField have red border if missing field
     */
    public void addBorder(){
        if (name.getText().equals("")){
            name.setBorder(redLineBorder);
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

        // Object array for storing JTextFields
        Object[] teacherFields = {"Name", name, "Age", age, "Salary", salary, "Employee ID", employeeID, "Course taught", courseTaught};
        Object[] principalFields = {"Name", name, "Age", age, "Salary", salary, "Employee ID", employeeID, "School Name", schoolName};
        Object[] studentFields = {"Name", name, "Age", age, "Student ID", studentID};
        if (o == newTeacher){
            clear();
            boolean notFilled1 = true;
            while (notFilled1) {
                try {
                    JOptionPane.showMessageDialog(null, teacherFields);
                    teachers.put(Integer.parseInt(employeeID.getSelectedItem().toString()), new Teacher(Integer.parseInt(age.getSelectedItem().toString()), name.getText(), Integer.parseInt(salary.getSelectedItem().toString()), Integer.parseInt(employeeID.getSelectedItem().toString()), courseTaught.getText()));
                    notFilled1 = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    addBorder();
                }
            }
            defaultBorder();
        } else if (o == newPrincipal){
            clear();
            boolean notFilled2 = true;
            while (notFilled2) {
                try {
                    JOptionPane.showMessageDialog(null, principalFields);
                    principals.put(Integer.parseInt(employeeID.getSelectedItem().toString()),new Principal(Integer.parseInt(age.getSelectedItem().toString()), name.getText(), Integer.parseInt(salary.getSelectedItem().toString()), Integer.parseInt(employeeID.getSelectedItem().toString()), schoolName.getText()));
                    notFilled2 = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    addBorder();
                }
            }
            defaultBorder();
        } else if (o == newStudent){
            clear();
            boolean notFilled3 = true;
            while (notFilled3) {
                try {
                    JOptionPane.showMessageDialog(null, studentFields);
                    students.put(Integer.parseInt(studentID.getText()),new Student(Integer.parseInt(age.getSelectedItem().toString()), name.getText(), Integer.parseInt(studentID.getText())));
                    notFilled3 = false;
                }
                catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    addBorder();
                }
            }
            defaultBorder();
        } else if (o == getTeacher){
            addToListTeacher();
            JOptionPane.showMessageDialog(null, teacherInfo);
        } else if (o == getPrincipal){
            addToListPrincipal();
            JOptionPane.showMessageDialog(null, principalInfo);
        } else if (o == getStudent){
            addToListStudent();
            JOptionPane.showMessageDialog(null, studentInfo);
        }
    }
}
