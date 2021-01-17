import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * dean GUI
 */
public class DeanGUI extends JFrame implements ActionListener {

    /**
     * button to set salary
     */
    private JButton setSalary;
	private Container container;
	private JPanel deanPanel;
	private JButton logout;
    /**
     * Create deanGUI
     */
    public DeanGUI(){
        container = getContentPane();

        deanPanel = new JPanel(new GridLayout(0,1));
        container.add(deanPanel);

        setSalary = new JButton("SET PROFESSOR SALARY");
        setSalary.setFont(new Font("Arial", Font.BOLD, 40));
        logout = new JButton("CANCEL");
        logout.setFont(new Font("Arial", Font.BOLD, 40));
        
        deanPanel.add(setSalary);
        deanPanel.add(logout);

        setSalary.addActionListener(this);
        logout.addActionListener(this);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setSize(800,600);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
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
        	dispose();
        }
    }
}

