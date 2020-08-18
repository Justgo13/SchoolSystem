import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;

public class ProfGUI implements ActionListener, dataStorage {

    /**
     * JButton for teacher
     */
    private JButton updateGrade;
    
    private Professor professor;

	private JFrame frame;

	private Container container;

	private JPanel profPanel;

    /**
     * Create a prof GUI
     * @param professor, Student student 
     */
    public ProfGUI(Professor professor){
    	this.professor = professor;
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
        	new UpdateGradeGUI(professor);
        }
    } 
}
