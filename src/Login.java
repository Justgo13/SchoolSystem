import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.*;
/**
 * Login screen GUI
 */
public class Login implements ActionListener{
    /**
     * Create main screen button
     */
    JButton deanLogin;
    JButton professorLogin;
    JButton studentLogin;
    JButton registerButton;

    /**
     * login field
     */
    private JTextField usernameField;
    private JTextField passwordField;

    /**
     * Register choice
     */
    JComboBox<String> registerChoice;

    /**
     * Register fields
     */
    private JTextField name;
    private JComboBox<Integer> employeeAge;
    private JComboBox<Integer> studentAge;

    //// employee field
    private JTextField employeeID;
    private JComboBox<Integer> salary;

    // dean specific
    private JTextField schoolName;

    // student specific
    private JTextField studentID;
    private JTextField major;
    private JComboBox<Integer> yearStanding;

    /**
     * HashMap of all the Professor, Dean, Student
     */
    private HashMap<Integer, String> userPass;
    protected static HashMap<HashMap<Integer, String>, Dean> deanHashMap;
    protected static HashMap<HashMap<Integer,String>, Professor> professorHashMap;
    protected static HashMap<HashMap<Integer,String>, Student> studentHashMap;

    /**
     * default login
     */
    public Login(){

    }

    /**
     * Creates login GUI
     */
    public Login(boolean login) {
        JFrame frame = new JFrame();
        Container container = frame.getContentPane();

        JPanel loginPanel = new JPanel(new GridLayout(1,4));
        container.add(loginPanel);

        // login and register buttons
        deanLogin = new JButton("DEAN LOGIN");
        professorLogin = new JButton("PROFESSOR LOGIN");
        studentLogin = new JButton("STUDENT LOGIN");
        registerButton = new JButton("REGISTER");

        // removes box around text
        deanLogin.setFocusPainted(false);
        professorLogin.setFocusPainted(false);
        studentLogin.setFocusPainted(false);
        registerButton.setFocusPainted(false);

        // new font for JButton text
        deanLogin.setFont(new Font("Arial", Font.BOLD, 24));
        professorLogin.setFont(new Font("Arial", Font.BOLD, 24));
        studentLogin.setFont(new Font("Arial", Font.BOLD, 24));
        registerButton.setFont(new Font("Arial", Font.BOLD, 24));

        // adding to panel
        loginPanel.add(deanLogin);
        loginPanel.add(professorLogin);
        loginPanel.add(studentLogin);
        loginPanel.add(registerButton);

        // adding actionListener
        deanLogin.addActionListener(this);
        professorLogin.addActionListener(this);
        studentLogin.addActionListener(this);
        registerButton.addActionListener(this);

        // initialize login fields
        usernameField = new JTextField();
        passwordField = new JTextField();

        // data for register choice comboBox
        String[] register = new String[3];
        register[0] = "Dean";
        register[1] = "Professor";
        register[2] = "Student";

        // create registerChoice CheckBox
        registerChoice = new JComboBox<>(register);

        // Data for age
        Integer[] staffAgeData = new Integer[51];
        for (Integer i = 0; i < 51; i++){
            staffAgeData[i] = i + 30;
        }

        Integer[] studentAgeData = new Integer[13];
        for (Integer i = 0; i < 13; i++){
            studentAgeData[i] = i + 18;
        }

        // create age CheckBox
        employeeAge = new JComboBox<>(staffAgeData);
        studentAge = new JComboBox<>(studentAgeData);

        // Data for salary
        Integer[] salaryData = new Integer[10];
        int value = 0;
        for (Integer i = 0; i < 10; i++){
            salaryData[i] = 100000 + value;
            value += 10000;
        }

        // Creat salary CheckBox
        salary = new JComboBox<>(salaryData);

        // Data for year standing
        Integer[] yearStandingData = new Integer[4];
        for (Integer i = 0; i < 4; i++){
            yearStandingData[i] = i + 1;
        }

        // year standing CheckBox
        yearStanding = new JComboBox<>(yearStandingData);

        // init HashMap
        deanHashMap = new HashMap<>();
        professorHashMap = new HashMap<>();
        studentHashMap = new HashMap<>();
        userPass = new HashMap<>();

        // init textfield
        name = new JTextField();
        employeeID = new JTextField();
        schoolName = new JTextField();
        studentID = new JTextField();
        major = new JTextField();

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(1500,200);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        JButton button = (JButton) o;

        Object[] loginField = {"Username", usernameField, "Password", passwordField};
        Object[] registerChoiceField = {"Account type", registerChoice};
        Object[] deanRegisterField = {"Name", name, "Age", employeeAge, "Salary", salary, "Username (Employee ID)", employeeID, "Password", passwordField, "School Managed", schoolName};
        Object[] profRegisterField = {"Name", name, "Age", employeeAge, "Salary", salary, "Username (Employee ID)", employeeID, "Password", passwordField};
        Object[] studentRegisterField = {"Name", name, "Age", studentAge, "Username (Student ID)", studentID, "Password", passwordField};

        if (button == deanLogin) {
            JOptionPane.showMessageDialog(null, loginField);
            if (userPass.containsKey(Integer.parseInt(usernameField.getText())) && userPass.get(Integer.parseInt(usernameField.getText())).equals(passwordField.getText())) {
                deanGUI dgui = new deanGUI();
            } else {
                JOptionPane.showMessageDialog(null, "No matching username and password found");
            }
        } else if (button == professorLogin) {
            JOptionPane.showMessageDialog(null, loginField);
            if (userPass.containsKey(Integer.parseInt(usernameField.getText())) && userPass.get(Integer.parseInt(usernameField.getText())).equals(passwordField.getText())) {
                profGUI prof = new profGUI();
            } else {
                JOptionPane.showMessageDialog(null, "No matching username and password found");
            }
        } else if (button == studentLogin) {
            JOptionPane.showMessageDialog(null, loginField);
            if (userPass.containsKey(Integer.parseInt(usernameField.getText())) && userPass.get(Integer.parseInt(usernameField.getText())).equals(passwordField.getText())) {
                studentGUI student = new studentGUI();
            } else {
                JOptionPane.showMessageDialog(null, "No matching username and password found");
            }
        } else if (button == registerButton) {
            JOptionPane.showMessageDialog(null, registerChoiceField);
            if (registerChoice.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(null, deanRegisterField);
                userPass.put(Integer.parseInt(employeeID.getText()), passwordField.getText());
                deanHashMap.put(userPass, new Dean((Integer)employeeAge.getSelectedItem(), name.getText(), (Integer)salary.getSelectedItem(), Integer.parseInt(employeeID.getText()), schoolName.getText()));
            } else if (registerChoice.getSelectedIndex() == 1) {
                JOptionPane.showMessageDialog(null, profRegisterField);
                userPass.put(Integer.parseInt(employeeID.getText()), passwordField.getText());
                professorHashMap.put(userPass, new Professor((Integer)employeeAge.getSelectedItem(), name.getText(), (Integer)salary.getSelectedItem(), Integer.parseInt(employeeID.getText())));
            } else {
                JOptionPane.showMessageDialog(null, studentRegisterField);
                userPass.put(Integer.parseInt(studentID.getText()), passwordField.getText());
                studentHashMap.put(userPass, new Student((Integer)employeeAge.getSelectedItem(), name.getText(), Integer.parseInt(studentID.getText()), major.getText(), (Integer)yearStanding.getSelectedItem()));
            }
        }
    }
}
