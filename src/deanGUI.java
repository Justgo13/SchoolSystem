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
    private JTextField professorID;
    private JTextField salaryIncreaseAmount;

    /**
     * TextField for getting dean info
     */
    private JTextField deanID;

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
        salaryIncreaseAmount = new JTextField();
        deanID = new JTextField();

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
        Object[] deanField = {"Dean ID", deanID};

        Collection collection = professorHashMap.values();
        boolean notFilled = true;

        if (button == increaseSalary) {
            clear();
            while(notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, salaryField);
                    for (Object professor : collection) {
                        Professor prof = (Professor) professor;
                        if (prof.getID() == Integer.parseInt(professorID.getText())) {
                            prof.setSalary(Integer.parseInt(salaryIncreaseAmount.getText()));
                            JOptionPane.showMessageDialog(null, "You have changed " + prof.getName() + "'s salary to " + prof.getSalary());
                            notFilled = false;
                        }
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    professorID.setBorder(redBorder);
                    salaryIncreaseAmount.setBorder(redBorder);
                }
            }
        } else if (button == getDeanInfo) {
            clear();
            while (notFilled) {
                try {
                    JOptionPane.showMessageDialog(null, deanField);
                    for (Object deanInfo : collection) {
                        Dean dean = (Dean) deanInfo;
                        if (dean.getID() == Integer.parseInt(deanID.getText())) {
                            JOptionPane.showMessageDialog(null, dean.toString());
                            notFilled = false;
                        }
                    }
                } catch (NumberFormatException err) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    deanID.setBorder(redBorder);
                }
            }
        }
    }
}

