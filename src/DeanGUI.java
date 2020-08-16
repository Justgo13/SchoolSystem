import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import javax.swing.*;

/**
 * dean GUI
 */
public class DeanGUI implements ActionListener {

    /**
     * button to set salary
     */
    private JButton setSalary;

	private JFrame frame;

	private Container container;

	private JPanel deanPanel;

	private JButton logout;
    /**
     * Create deanGUI
     */
    public DeanGUI(){
        super();
        frame = new JFrame();
        container = frame.getContentPane();

        deanPanel = new JPanel(new GridLayout(0,1));
        container.add(deanPanel);

        setSalary = new JButton("SET PROFESSOR SALARY");
        setSalary.setFont(new Font("Arial", Font.BOLD, 40));
        logout = new JButton("CANCEL");
        logout.setFont(new Font("Arial", Font.BOLD, 40));
        
        deanPanel.add(setSalary);
        deanPanel.add(logout);

        setSalary.addActionListener(this);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
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

        if (button.equals(setSalary)) {
        	new SalaryGUI();
        } else if (button.equals(logout)) {
        	frame.dispose();
        }
    }
}

