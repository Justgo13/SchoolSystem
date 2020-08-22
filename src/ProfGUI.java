import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ProfGUI implements ActionListener {

    /**
     * JButton for teacher
     */
    private JButton updateGrade;
    
	private JFrame frame;

	private Container container;

	private JPanel profPanel;
	
	private String profID;

    /**
     * Create a prof GUI
     * @param professor, Student student 
     */
    public ProfGUI(String profID){
    	this.profID = profID;
    	initProfGUI();
    }
    
    public void initProfGUI() {
        frame = new JFrame();
        container = frame.getContentPane();

        profPanel = new JPanel(new GridLayout(0,1));
        container.add(profPanel);

        updateGrade = new JButton("UPDATE GRADE");

        updateGrade.setFocusPainted(false);

        updateGrade.setFont(new Font("Arial", Font.BOLD, 40));

        profPanel.add(updateGrade);

        updateGrade.addActionListener(this);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(800,600);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        JButton button = (JButton) o;
        
        if (button.equals(updateGrade)) {
        	new UpdateGradeGUI(profID);
        }
    } 
}
