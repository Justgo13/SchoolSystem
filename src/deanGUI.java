import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import javax.swing.*;

/**
 * dean GUI
 */
public class deanGUI extends Login implements ActionListener {

    /**
     * JButtons
     */
    private JButton increaseSalary;
    private JButton getDeanInfo;

    /**
     * JTextField for increasing salary
     */
    private static JTextField professorID;
    private static JComboBox<Integer> salaryIncreaseAmount;

    /**
     * Collections
     */
    private static Collection<Professor> profCollection;
    private static Collection<Dean> deanCollection;
    private static boolean notFilled;
    private Dean dean;
    private Professor prof;
    /**
     * Create deanGUI
     */
    public deanGUI(){
        super();
        JFrame frame = new JFrame();
        Container container = frame.getContentPane();

        JPanel deanPanel = new JPanel(new GridLayout(2,1));
        container.add(deanPanel);

        increaseSalary = new JButton("INCREASE PROFESSOR SALARY");
        getDeanInfo = new JButton("DEAN INFO");

        increaseSalary.setFocusPainted(false);
        getDeanInfo.setFocusPainted(false);

        increaseSalary.setFont(new Font("Arial", Font.BOLD, 24));
        getDeanInfo.setFont(new Font("Arial", Font.BOLD, 24));

        deanPanel.add(increaseSalary);
        deanPanel.add(getDeanInfo);

        increaseSalary.addActionListener(this);
        getDeanInfo.addActionListener(this);

        professorID = new JTextField();

        Integer[] salaryIncrease = new Integer[5];
        int value = 0;
        for (int i=0; i < 5; i++) {
            salaryIncrease[i] =  10000 + value;
            value += 10000;
        }

        salaryIncreaseAmount = new JComboBox<>(salaryIncrease);

        profCollection = professorHashMap.values();
        deanCollection = deanHashMap.values();

        // finds the correct Dean in Collection
        for (Dean dean : deanCollection) {
            if (dean.getID() == Integer.parseInt(usernameField.getText())) {
                this.dean = dean;
            }
        }

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,200);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    /**
     * ActionPerformed for listening to actionEvent
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        JButton button = (JButton) o;
        Object[] salaryField = {"Professor ID", professorID, "Salary increase by", salaryIncreaseAmount};

        if (button == increaseSalary) {
            notFilled  = true;
            professorID.setBorder(null);
            professorID.setText(null);

            while(notFilled) {
                JOptionPane.showMessageDialog(null, salaryField);
                if (professorID.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Fill the professor ID");
                    professorID.setBorder(redBorder);
                    professorID.setText(null);
                } else {

                    // finds the specific Professor
                    prof = null;
                    for (Professor professor : profCollection) {
                        if (professor.getID() == Integer.parseInt(professorID.getText())) {
                            prof = professor;
                        }
                    }

                    if (prof == null) {
                        JOptionPane.showMessageDialog(null, "ID does not match any professor in database");
                        notFilled = false;
                    } else {
                        prof.setSalary((Integer) salaryIncreaseAmount.getSelectedItem());
                        JOptionPane.showMessageDialog(null, "You have changed " + prof.getName() + "'s salary to " + prof.getSalary());
                        notFilled = false;
                    }
                }
            }
        } else if (button == getDeanInfo) {
            JOptionPane.showMessageDialog(null, dean.toString());
        }
    }
}

