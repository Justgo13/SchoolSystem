import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.LineBorder;

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
     * red border for if input is null
     */
    protected static LineBorder redBorder;
    /**
     * login field
     */
    protected static JTextField usernameField;
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
    private static HashMap<Integer, String> deanUserPass;
    private static HashMap<Integer, String> profUserPass;
    private static HashMap<Integer, String> studentUserPass;
    protected static HashMap<HashMap<Integer, String>, Dean> deanHashMap;
    protected static HashMap<HashMap<Integer,String>, Professor> professorHashMap;
    protected static HashMap<HashMap<Integer,String>, Student> studentHashMap;

    /**
     * Failsafe for program
     */
    public static final HashMap<Integer, String> failSafe = new HashMap<>();

    /**
     * Checks if user already registered
     */
    private static boolean notAlreadyRegistered;
    private static boolean notFilled;
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

        redBorder = new LineBorder(Color.RED);

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

        // init userPass
        deanUserPass = new HashMap<>();
        profUserPass = new HashMap<>();
        studentUserPass = new HashMap<>();

        // init textfield
        name = new JTextField();
        employeeID = new JTextField();
        schoolName = new JTextField();
        studentID = new JTextField();
        major = new JTextField();

        /**
         * Failsafe for program
         */
        failSafe.put(0,"fail");
        deanHashMap.put(failSafe, new Dean(0, "fail", 0, 0, "fail"));
        professorHashMap.put(failSafe, new Professor(0,"fail",0,0));
        studentHashMap.put(failSafe, new Student(0, "fail", 0, "fail", 0));

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(1500,200);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    /**
     * Clears all JTextFields
     */
    public void clear(){
        name.setText(null);
        name.setBorder(null);
        usernameField.setText(null);
        usernameField.setBorder(null);
        passwordField.setText(null);
        passwordField.setBorder(null);
        employeeID.setText(null);
        employeeID.setBorder(null);
        studentID.setText(null);
        studentID.setBorder(null);
        major.setText(null);
        major.setBorder(null);
    }

    /**
     * Listens to ActionEvents
     * @param e listen to actionEvent
     */
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        JButton button = (JButton) o;

        Object[] loginField = {"Username", usernameField, "Password", passwordField};
        Object[] registerChoiceField = {"Account type", registerChoice};
        Object[] deanRegisterField = {"Name", name, "Age", employeeAge, "Salary", salary, "Username (Employee ID)", employeeID, "Password", passwordField, "School Managed", schoolName};
        Object[] profRegisterField = {"Name", name, "Age", employeeAge, "Salary", salary, "Username (Employee ID)", employeeID, "Password", passwordField};
        Object[] studentRegisterField = {"Name", name, "Age", studentAge, "Username (Student ID)", studentID, "Password", passwordField};

        if (button == deanLogin) {
            notFilled = true;
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, loginField);
                    if (deanUserPass.containsKey(Integer.parseInt(usernameField.getText())) && deanUserPass.get(Integer.parseInt(usernameField.getText())).equals(passwordField.getText())) {
                        notFilled = false;
                        deanGUI dgui = new deanGUI();
                    } else {
                        JOptionPane.showMessageDialog(null, "No matching username and password found");
                        notFilled = false;
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    usernameField.setBorder(redBorder);
                    passwordField.setBorder(redBorder);
                }
            }
        } else if (button == professorLogin) {
            notFilled = true;
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, loginField);
                    if (profUserPass == null) {
                        JOptionPane.showMessageDialog(null, "There are currently no professors");
                        notFilled = false;
                    } else if (profUserPass.containsKey(Integer.parseInt(usernameField.getText())) && profUserPass.get(Integer.parseInt(usernameField.getText())).equals(passwordField.getText())) {
                        notFilled = false;
                        profGUI prof = new profGUI();
                    } else {
                        JOptionPane.showMessageDialog(null, "No matching username and password found");
                        notFilled = false;
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    usernameField.setBorder(redBorder);
                    passwordField.setBorder(redBorder);
                }
            }
        } else if (button == studentLogin) {
            notFilled = true;
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, loginField);
                    if (studentUserPass == null) {
                        JOptionPane.showMessageDialog(null, "There are currently no students");
                        notFilled = false;
                    } else if (studentUserPass.containsKey(Integer.parseInt(usernameField.getText())) && studentUserPass.get(Integer.parseInt(usernameField.getText())).equals(passwordField.getText())) {
                        notFilled = false;
                        studentGUI student = new studentGUI();
                    } else {
                        JOptionPane.showMessageDialog(null, "No matching username and password found");
                        notFilled = false;
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    usernameField.setBorder(redBorder);
                    passwordField.setBorder(redBorder);
                }
            }
        } else if (button == registerButton) {
            notFilled = true;
            notAlreadyRegistered = true;
            clear();
            JOptionPane.showMessageDialog(null, registerChoiceField);
            while(notFilled) {
                try {
                    if (registerChoice.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(null, deanRegisterField);
                        while (notAlreadyRegistered) {
                            if (deanUserPass.containsKey(Integer.parseInt(employeeID.getText()))) {
                                JOptionPane.showMessageDialog(null, "This employee ID belongs to somebody else please contact your supervisor for a new one");
                                employeeID.setText(null);
                                employeeID.setBorder(redBorder);
                                JOptionPane.showMessageDialog(null, deanRegisterField);
                            } else {
                                deanUserPass.put(Integer.parseInt(employeeID.getText()), passwordField.getText());
                                deanHashMap.put(deanUserPass, new Dean((Integer) employeeAge.getSelectedItem(), name.getText(), (Integer) salary.getSelectedItem(), Integer.parseInt(employeeID.getText()), schoolName.getText()));
                                notFilled = false;
                                notAlreadyRegistered = false;
                            }
                        }
                    } else if (registerChoice.getSelectedIndex() == 1) {
                        JOptionPane.showMessageDialog(null, profRegisterField);
                        while (notAlreadyRegistered) {
                            if (profUserPass.containsKey(Integer.parseInt(employeeID.getText()))) {
                                JOptionPane.showMessageDialog(null, "This employee ID belongs to somebody else please contact your supervisor for a new one");
                                employeeID.setText(null);
                                employeeID.setBorder(redBorder);
                                JOptionPane.showMessageDialog(null, profRegisterField);
                            } else {
                                profUserPass.put(Integer.parseInt(employeeID.getText()), passwordField.getText());
                                professorHashMap.put(profUserPass, new Professor((Integer) employeeAge.getSelectedItem(), name.getText(), (Integer) salary.getSelectedItem(), Integer.parseInt(employeeID.getText())));
                                notFilled = false;
                                notAlreadyRegistered = false;
                            }
                        }
                    } else if (registerChoice.getSelectedIndex() == 2){
                        JOptionPane.showMessageDialog(null, studentRegisterField);
                        while (notAlreadyRegistered) {
                            if (studentUserPass.containsKey(Integer.parseInt(studentID.getText()))) {
                                JOptionPane.showMessageDialog(null, "This student ID belongs to someone else please ask your Student Office for a new ID");
                                studentID.setText(null);
                                studentID.setBorder(redBorder);
                                JOptionPane.showMessageDialog(null, studentRegisterField);
                            } else {
                                studentUserPass.put(Integer.parseInt(studentID.getText()), passwordField.getText());
                                studentHashMap.put(studentUserPass, new Student((Integer)employeeAge.getSelectedItem(), name.getText(), Integer.parseInt(studentID.getText()), major.getText(), (Integer)yearStanding.getSelectedItem()));
                                notFilled = false;
                                notAlreadyRegistered = false;
                            }
                        }
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    name.setBorder(redBorder);
                    employeeID.setBorder(redBorder);
                    studentID.setBorder(redBorder);
                    schoolName.setBorder(redBorder);
                    major.setBorder(redBorder);
                    yearStanding.setBorder(redBorder);
                    passwordField.setBorder(redBorder);
                }
            }
        }
    }
}
